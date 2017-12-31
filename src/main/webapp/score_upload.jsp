<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<!DOCTYPE html>
<html>
<head>
	<title>中国英语村-成绩上传</title>
	<common:import/>
	<link rel="stylesheet" href="<%=basePath%>/vendor/bootstrap-fileinput-4.4.7/css/fileinput.min.css">
	<script src="<%=basePath%>/vendor/bootstrap-fileinput-4.4.7/js/fileinput.min.js"></script>
	<!-- FileInput插件的本地化设置，使用时需提前引入\locales文件夹下对应的语言文件，中文zh，引入语言文件必须放在fileinput.js之后 -->
	<script src="<%=basePath%>/vendor/bootstrap-fileinput-4.4.7/js/locales/zh.js"></script>
</head>
<body>
	<common:page-header/>
	<div class="evc-content">
		<div class="">
			<p>仅支持一次上传一个文件，支持预览：</p>
		</div>
		<div class="">
			<div class="form-group">
				<label>考试名称：</label>
				<select id="exam" class="form-control">
					<option value="3">半期考试</option>
					<option value="1">第一次月考</option>
					<option value="4">期末考试</option>
				</select>
			</div>
		</div>
		<div class="">
			<input id="uploadfile" name="input-b1" type="file" class="file">
		</div>
	</div>
	<common:page-footer/>
<!-- FileInput插件的初始化脚本 -->
<script type="text/javascript">
$("#uploadfile").fileinput({
	language: 'zh', //设置语言
	uploadUrl: "<%=basePath%>/rest/score/upload", //上传的地址
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
</script>
</body>
</html>