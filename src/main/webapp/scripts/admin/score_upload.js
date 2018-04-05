/**
 * 本文件为score_upload.jsp使用的初始化脚本。
 * 脚本中需要用到common.js的getWebRoot()和initExamSelect()函数，所以需要同时导入common.js文件。
 */
var subjectMap = new Map();//保存所有的科目信息
var examMap = new Map();//保存一学期的所有考试，key=id, value=Exam以避免重复查询
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
	dropZoneEnabled: true,//是否显示拖拽区域
	minFileSize: 1,
	maxFileSize: 10240,//单位为kb，如果为0表示不限制文件大小
	minFileCount: 1,
	maxFileCount: 1,//最大文件数，如果是成绩批量上传，则文件为一个.zip文件，所以限制最大上传文件数
	enctype:'multipart/form-data',
	validateInitialCount:true,
	previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
	msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
};

$(document).ready(function(){
	//初始化学期下拉菜单
	queryAllSemesters();
	//获取所有的科目。用于当考试信息变化时，需要显示该考试的所有考试科目名字。
	queryAllSubjects();
	
	//如果学期下拉菜单变化，则查询该学期下的所有考试信息。成绩查询将根据这个选中的考试信息进行。
	$('#semesterSelect').change(function(){
		//在common.js中定义
		initExamSelect(getExamCallback);
		
		//在common.js中定义。设置考试下拉菜单的选中项为“--请选择--”
		setSelect2SelectedOption("examSelect", "none");
		//学期变化会带来考试变化，考试变化又有相应的函数
		handleExamChange();
	});
	
	//初始化考试下拉菜单
	$("#examSelect").select2({
		language: "zh-CN"
	});
	
	//考试下拉菜单变化的事件
	$("#examSelect").change(function(){
		handleExamChange();
	});
	
	//在“批量上传”页面，直接初始化这个input
	uploadBatchConfig();
});

//初始化批量上传的配置
function uploadBatchConfig() {
	uploadConfig.allowedFileExtensions = ['zip'];//接收的文件后缀，这里只支持.zip格式的压缩文件
	uploadConfig.uploadUrl = webroot + "/score/uploadbatch"; //上传的地址
	uploadConfig.uploadExtraData = {}; //附加信息
	uploadConfig.maxFileCount = 20; //表示允许同时上传的最大文件个数;
	$("#upload-batch").fileinput(uploadConfig).on("fileuploaded", function (event, data, previewId, index){
		//上传成功后的回调
		if (data.jqXHR.responseJSON.status == 0) {
			toastr.success("批量上传成功！");
		} else {
			var msg = "上传失败的文件：" + data.jqXHR.responseJSON.response;
			toastr.success(msg);
			console.log(msg);
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
		//上传成功后的回调
		console.log("单文件上传成功！event=" + event + ", data=" + data + ", previewId=" + previewId + ", index=" + index);
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

//回调函数，当获取到一个学期的所有考试信息的时候，重新初始化examMap
function getExamCallback(exams) {
	examMap.clear();
	for(var index in exams) {
		var exam = exams[index];
		examMap.set(exam.id, exam);
	}
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
	$.ajax({
		type: 'GET',
		url: webroot + '/subject/all',
		success: function (data) {
			var subjects = data.response;
			//把科目放到Map中，key为id
			for (var i in subjects) {
				subjectMap.set(subjects[i].id, subjects[i]);
			}
		},
		error: function () {
			alert("调用学生信息查询接口失败！");
			console.log("调用查询接口失败！");
		}
	});
}
