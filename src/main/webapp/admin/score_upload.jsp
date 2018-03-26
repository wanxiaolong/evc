<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common"%>
<%@ taglib prefix="admin" tagdir="/WEB-INF/tags/admin"%>
<%@ page isELIgnored="false" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<!DOCTYPE html>
<html>
<head>
	<title>中国英语村-成绩上传</title>
	<common:import/>
	<admin:import/>
	
	<!-- For fileinput -->
	<link rel="stylesheet" href="<%=basePath%>/vendor/bootstrap-fileinput-4.4.7/css/fileinput.min.css">
	<script src="<%=basePath%>/vendor/bootstrap-fileinput-4.4.7/js/fileinput.min.js"></script>
	<!-- FileInput插件的本地化设置，使用时需提前引入\locales文件夹下对应的语言文件，中文zh，引入语言文件必须放在fileinput.js之后 -->
	<script src="<%=basePath%>/vendor/bootstrap-fileinput-4.4.7/js/locales/zh.js"></script>
	
	<!-- For select2 -->
	<link href="<%=basePath%>/vendor/select2-4.0.5/css/select2.min.css" rel="stylesheet"/>
	<script src="<%=basePath%>/vendor/select2-4.0.5/js/select2.min.js"></script>
	<!-- Select2 language resource file -->
	<script src="<%=basePath%>/vendor/select2-4.0.5/js/i18n/zh-CN.js"></script>
</head>
<body>
	<common:page-header/>
	<div class="admin evc-content">
		<admin:menu/>
		<div class="content">
			<div class="form-inline filter-block">
				<div class="title">请先选择你要为哪次考试上传成绩：</div>
				<div class="filter-row">
					<div class="filter">
						<label>学期：</label>
						<select class="form-control" id="semesterSelect" name="semester" required>
							<option>--请选择--</option>
						</select>
					</div>
					<div class="filter">
						<label>考试：</label>
						<select class="form-control" id="examSelect" required>
							<option>--请选择--</option>
						</select>
					</div>
				</div>
				<div class="subject-msg-div">
					请确保文件中<b>科目</b>和<b>顺序</b>按照如下顺序：<div id="subjectmsg"></div>
				</div>
				<div class="upload-div">
					<input id="uploadfile" name="input-b1" type="file" class="file">
				</div>
			</div>
		</div>
	</div>
	<common:page-footer/>
<!-- FileInput插件的初始化脚本 -->
<script type="text/javascript" src="<%=basePath%>/scripts/admin/score_upload.js"></script>
</body>
</html>