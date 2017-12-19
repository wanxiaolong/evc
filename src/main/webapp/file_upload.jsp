<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<!DOCTYPE html>
<html>
<head>
	<title>中国英语村-资料上传</title>
	<common:import/>
	<link rel="stylesheet" href="<%=basePath%>/vendor/bootstrap-fileinput-4.4.7/css/fileinput.min.css">
	<script src="<%=basePath%>/vendor/bootstrap-fileinput-4.4.7/js/fileinput.min.js"></script>
	<!-- FileInput插件的本地化设置，使用时需提前引入\locales文件夹下对应的语言文件，中文zh，引入语言文件必须放在fileinput.js之后 -->
	<script src="<%=basePath%>/vendor/bootstrap-fileinput-4.4.7/js/locales/zh.js"></script>
</head>
<body>
	<common:page-header/>
	<div class="evc-content">
		<div class="row">
			<p>支持一次上传多个文件，支持预览：</p>
		</div>
		<div class="row">
			<input id="uploadfile" name="input-b1" type="file" class="file" multiple>
		</div>
	</div>
	<common:page-footer/>
<!-- FileInput插件的初始化脚本 -->
<script type="text/javascript">
$("#uploadfile").fileinput({
	language: 'zh', //设置语言
	uploadUrl: "http://localhost:8080/evc/rest/file/upload", //上传的地址
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
	console.log("上传成功！event=" + event + ", data=" + data + ", previewId=" + previewId + ", index=" + index);
});
</script>
</body>
</html>