/**
 * 本文件为score_upload.jsp使用的初始化脚本。
 * 脚本中需要用到common.js的getWebRoot()和initExamSelect()函数，所以需要同时导入common.js文件。
 */
var subjectMap = new Map();//保存所有的科目信息
var examMap = new Map();//保存一学期的所有考试，key=id, value=Exam以避免重复查询
var webroot = getWebRoot();
$(document).ready(function(){
	//初始化学期下拉菜单
	queryAllSemesters();
	//获取所有的科目。用于当考试信息变化时，需要显示该考试的所有考试科目名字。
	queryAllSubjects();
	
	//如果学期下拉菜单变化，则查询该学期下的所有考试信息。成绩查询将根据这个选中的考试信息进行。
	$('#semesterSelect').change(function(){
		//在common.js中定义
		initExamSelect(getExamCallback);
	});
	
	$("#uploadfile").fileinput({
		language: 'zh', //设置语言
		uploadUrl: "<%=basePath%>/score/upload", //上传的地址
		allowedFileExtensions: ['xls', 'xlsx'],//接收的文件后缀
		uploadExtraData:{"exam_id": $("#exam option:selected").val()},
		uploadAsync: true, //默认异步上传
		showUpload: true, //是否显示上传按钮
		showRemove: true, //显示移除按钮
		showPreview: true, //是否显示预览
		showCaption: true,//是否显示标题
		browseClass: "btn btn-primary", //按钮样式
		dropZoneEnabled: false,//是否显示拖拽区域
		//minImageWidth: 50, //图片的最小宽度
		//minImageHeight: 50,//图片的最小高度
		//maxImageWidth: 1000,//图片的最大宽度
		//maxImageHeight: 1000,//图片的最大高度
		minFileSize: 1,
		maxFileSize: 10240,//单位为kb，如果为0表示不限制文件大小
		//minFileCount: 1,
		maxFileCount: 1, //表示允许同时上传的最大文件个数
		enctype:'multipart/form-data',
		validateInitialCount:true,
		previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
		msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
	}).on("fileuploaded", function (event, data, previewId, index){
		//上传成功后的回调
		console.log("上传成功！event=" + event + ", data=" + data + ", previewId=" + previewId + ", index=" + index);
	});
	
	//初始化考试下拉菜单
	$("#examSelect").select2({
		language: "zh-CN"
	});
	$("#examSelect").change(function(){
		displayUploadInfo();
	});
});

//回调函数，当获取到一个学期的所有考试信息的时候，重新初始化examMap
function getExamCallback(exams) {
	examMap.clear();
	for(var index in exams) {
		var exam = exams[index];
		examMap.set(exam.id, exam);
	}
}

function displayUploadInfo() {
	var examId = $("#examSelect").children('option:selected').val();//获取selected的值
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
