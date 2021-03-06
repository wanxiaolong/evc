/**
 * 本文件为score_upload.jsp使用的初始化脚本。
 */
var subjectMap = new Map();//保存所有的科目信息
var examMap = new Map();//保存一学期的所有考试，key=Exam.id, value=Exam以避免重复查询
var webroot = getWebRoot();

//由于有按考试上传和批量上传，所以这里定义了这两种上传方式的公共配置
var uploadConfig = {
	language: 'zh', //设置语言
	uploadAsync: true, //默认异步上传
	showUpload: true, //是否显示上传按钮
	showRemove: true, //显示移除按钮
	showPreview: true, //是否显示预览
	showCaption: true,//是否显示标题
	browseClass: "btn btn-primary", //按钮样式
	dropZoneEnabled: false,//是否显示拖拽区域
	minFileSize: 1,
	maxFileSize: 10240,//单位为kb，如果为0表示不限制文件大小
	minFileCount: 1,
	maxFileCount: 1,//最大文件数，如果是成绩批量上传，则文件为一个.zip文件，所以限制最大上传文件数
	enctype:'multipart/form-data',
	validateInitialCount:true,
	previewFileIcon: "<i class='glyphicon glyphicon-file'></i>",
	msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
};

$(document).ready(function(){
	//初始化学期下拉菜单
	queryAllSemesters();
	//获取所有的科目。用于当考试信息变化时，需要显示该考试的所有考试科目名字。
	queryAllSubjects();
	
	//如果学期下拉菜单变化，则查询该学期下的所有考试信息。成绩查询将根据这个选中的考试信息进行。
	$('#semesterSelect').change(function(){
		//获取selected的值
		var selectedSemester = $("#semesterSelect").children('option:selected').val();
		//如果没有选择有效的，则无需查询该学期下面的考试，直接返回
		if (selectedSemester == 'none') {
			reInitSelectOption("#examSelect");
			return;
		}
		var url = '/exam/findBySemester?semester_id=' + selectedSemester;
		ajax('GET', url, null, null, addExamOption);
	});
	
	//初始化考试下拉菜单
	$("#examSelect").select2({
		language: "zh-CN"
	});
	
	//考试下拉菜单变化的事件。考试下拉菜单变化会导致考试科目变化
	$("#examSelect").change(function(){
		handleExamChange();
	});
	
	//在“批量上传”页面，直接初始化这个input
	uploadBatchConfig();
});

//判断一个select是否有指定value的option
function containsSelectOption(selectId, value) {
	var optionValues = [];
	var examOptions = $("#" + selectId).children();
	if (examOptions == null || examOptions.length == 0) {
		return false;
	}
	for (var i = 0; i < examOptions.length; i++) {
		var optionValue = examOptions[i].value;
		optionValues.push(optionValue);
	}
	return optionValues.includes(value);
}

//初始化批量上传的配置
function uploadBatchConfig() {
	uploadConfig.allowedFileExtensions = ['zip'];//接收的文件后缀，这里只支持.zip格式的压缩文件
	uploadConfig.uploadUrl = webroot + "/score/uploadbatch"; //上传的地址
	uploadConfig.uploadExtraData = {}; //附加信息
	uploadConfig.maxFileCount = 10; //表示允许同时上传的最大文件个数;
	$("#upload-batch").fileinput(uploadConfig).on("fileuploaded", function (event, data, previewId, index){
		//上传成功后的回调
		if (data.jqXHR.responseJSON.status == 0) {
			var msg = "";
			data.filenames.forEach(function(fileName){
				msg += fileName + ", ";
			});
			toastr.success("批量上传成功！文件名：" + msg);
		} else {
			var msg = "上传失败的文件：" + data.jqXHR.responseJSON.response;
			toastr.error(msg);
		}
	});
}

//初始化单文件上传的配置
function initUploadConfig(examId) {
	//当前选择的是有效的考试，所以显示上传控件(upload-div)
	$(".upload-div").show();
	
	//对“按考试上传”进行特殊的配置
	uploadConfig.allowedFileExtensions = ['xls', 'xlsx'];//接收的文件后缀
	uploadConfig.uploadUrl = webroot + "/score/upload"; //上传的地址
	uploadConfig.uploadExtraData = {"exam_id": examId}; //附加信息
	uploadConfig.maxFileCount = 1; //表示允许同时上传的最大文件个数;
	
	$("#upload-file").fileinput(uploadConfig).on("fileuploaded", function (event, data, previewId, index){
		var responseJson = data.jqXHR.responseJSON;
		//上传成功后的回调
		if (responseJson.status == 0) {
			var fileName = data.filenames[0];
			toastr.success("文件" + fileName + "上传成功！");
		} else {
			var msg = "上传失败，原因：" + responseJson.errorMessage;
			toastr.error(msg);
		}
	});
}


function handleExamChange() {
	//获取selected的值
	var examId = $("#examSelect option:selected").val();
	//如果选择的是“--请选择--”，则隐藏科目信息，并隐藏上传div
	if (examId == "none") {
		$(".subject-msg-div").hide();
		$(".upload-div").hide();
		return;
	}
	
	//根据选择的考试，显示该考试的科目信息
	displaySubjectInfo(examId);
	
	//重新初始化上传控件。因为下拉菜单变化会引起examId变化，
	//而这个在上传文件的时候需要作为附加参数（uploadExtraData属性）上传的值需要在初始化控件的时候获取
	initUploadConfig(examId);
}

//在上传之前显示该考试的科目信息。
function displaySubjectInfo(examId) {
	//从examMap中可以直接获取到Exam的详细信息，就不用再次查询数据库了
	var exam = examMap.get(parseInt(examId));
	//把所有的考试ID都转换成考试名字
	var info = '';
	var ids = exam.subjectIds.split(',');
	for(var j in ids) {
		info += subjectMap.get(parseInt(ids[j])).name + '，';
	}
	//将消息显示到页面上
	$("#subjectmsg").html(info);
	$(".subject-msg-div").show();
}

//查询所有的学期信息（用于初始化下拉列表）
function queryAllSubjects() {
	ajax('GET', '/subject/all', null, null, querySuccessCallback);
}

//将查询到的学期信息动态增加到下拉菜单中
function addExamOption(array) {
	//当获取到一个学期的所有考试信息的时候，重新初始化examMap。当然也可以重新去请求一次，这是为了减少网络请求。
	examMap.clear();
	for(var index in array) {
		var exam = array[index];
		examMap.set(exam.id, exam);
	}

	//该方法在common.js中
	clearSelectOption("#examSelect");
	//再依次添加
	for(var index in array) {
		var exam = array[index];
		//这里只添加没有上传成绩的考试，已上传的直接过滤。如需重新为考试上传成绩，可以到“考试管理”里面删掉成绩
		if (exam.isScoreUploaded) {
			continue;
		}
		var option = "<option value='" + exam.id+"'>" + exam.name + "</option>";
		$("#examSelect").append(option);
	}

	//如果考试下拉选项里有考试隐藏域的值(从考试管理页面跳转到本页面的时候会将学期和考试作为参数放在本页面的隐藏域中)
	//则设置考试下拉菜单的选中项
	var examId = $("input[name='examId']").val();
	if (containsSelectOption('examSelect', examId)) {
		//这会触发examSelect上的change()事件
		setSelect2SelectedOption("examSelect", examId);
		//考试下拉只默认选中一次。当再次选中对应的学期后，考试下来菜单不默认选中任何值。这里只需要把隐藏域置空即可
		$("input[name='examId']").val("");
	}

	//用中文渲染select2
	$("#examSelect").select2({
		language: "zh-CN"
	});
}

function querySuccessCallback(subjects) {
	//把科目放到Map中，key为id
	for (var i in subjects) {
		subjectMap.set(subjects[i].id, subjects[i]);
	}
}