/**
 * 本文件为file_upload.jsp使用的初始化脚本。
 */
var webroot = getWebRoot();
$(document).ready(function(){
	$("#uploadfile").fileinput({
		language: 'zh', //设置语言
		uploadUrl: "http://localhost:8080/evc/file/upload", //上传的地址
		//allowedFileExtensions: ['jpg', 'gif', 'png'],//接收的文件后缀
		//uploadExtraData:{"id": 1, "fileName":'123.mp3'},
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
		maxFileSize: 102400,//单位为kb，如果为0表示不限制文件大小
		//minFileCount: 1,
		maxFileCount: 10, //表示允许同时上传的最大文件个数
		enctype:'multipart/form-data',
		validateInitialCount:true,
		previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
		msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
	}).on("fileuploaded", function (event, data, previewId, index){
		//上传成功后的回调
		toastr.success("上传成功！data=" + data);
	});
});
