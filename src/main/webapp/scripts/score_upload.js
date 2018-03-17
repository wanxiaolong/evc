/**
 * 本文件为score_upload.jsp使用的初始化脚本。
 * 脚本中需要用到common.js的getWebRoot()函数，所以需要同时导入common.js文件。
 */
var webroot = getWebRoot();
$(document).ready(function(){
	//如果学期下拉菜单变化，则查询该学期下的所有考试信息。成绩查询将根据这个选中的考试信息进行。
	$('#semesterSelect').change(function(){
		var selectedSemester = $(this).children('option:selected').val();//获取selected的值
		$.ajax({
			type: 'GET',
			url: webroot + '/exam/findBySemester?semester=' + selectedSemester,
			success: function (data) {
				//移除examSelect所有带有value的option
				$("#examSelect option[value]").remove();
				
				var examArray = data.response;
				if (examArray.length > 0) {
					for(var index in examArray) {
						var exam = examArray[index];
						//动态创建并添加select的option
						var option = new Option(exam.name, exam.id);
						$("#examSelect").append(option);
					}
				}
			},
			error: function () {
				console.log("调用查询考试信息接口失败！");
			}
		});
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
});
