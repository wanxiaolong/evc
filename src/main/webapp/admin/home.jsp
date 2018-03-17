<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<!DOCTYPE html>
<html>
<head>
	<title>中国英语村-管理员主页</title>
	<common:import/>
</head>
<body>
	<common:page-header/>
	<div class="evc-content">
		<h1>管理员首页</h1>
		<div class="evc-p-container">
			
		</div>
	</div>
	<common:page-footer/>
</body>
</html>