<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<!DOCTYPE html>
<html>
<head>
	<title>Bootstrap页面模板</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="<%=basePath%>/vendor/bootstrap-3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" href="<%=basePath%>/vendor/font-awesome-4.7.0/css/font-awesome.min.css">
	<script src="<%=basePath%>/vendor/jquery-3.2.1/jquery-3.2.1.min.js"></script>
	<script src="<%=basePath%>/vendor/bootstrap-3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
	<h1>Hello World!</h1>
</body>
</html>