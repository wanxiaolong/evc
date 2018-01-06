<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common"%>
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
		<div class="filter-row">
			<div class="filter">
				<label>学期：</label>
				<select class="form-control" id="semesterSelect" name="semester" required>
					<option>--请选择--</option>
					<c:forEach items="${semesters}" var="semester">
						<option value="${semester.number}">${semester.name}</option>
					</c:forEach>
				</select>
			</div>
			<div class="filter">
				<label>考试：</label>
				<select class="form-control" id="examSelect" required>
					<option>--请选择--</option>
				</select>
			</div>
		</div>
		<div class="">
			<input id="uploadfile" name="input-b1" type="file" class="file">
		</div>
	</div>
	<common:page-footer/>
<!-- FileInput插件的初始化脚本 -->
<script type="text/javascript" src="<%=basePath%>/scripts/common.js"></script>
<script type="text/javascript" src="<%=basePath%>/scripts/score_upload.js"></script>
</body>
</html>