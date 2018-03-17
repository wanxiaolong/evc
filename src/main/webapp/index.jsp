<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="i18n" uri="http://www.springframework.org/tags" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<!DOCTYPE html>
<html>
<head>
	<title><i18n:message code="welcome"/></title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="<%=basePath%>/vendor/bootstrap-3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" href="<%=basePath%>/vendor/font-awesome-4.7.0/css/font-awesome.min.css">
	<script src="<%=basePath%>/vendor/jquery-3.2.1/jquery-3.2.1.min.js"></script>
	<script src="<%=basePath%>/vendor/bootstrap-3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
	<h1><i18n:message code="hello"/></h1>
	<h1><i18n:message code="china"/></h1>
	<a href="/evc/server/lang?lang=zh">中文</a>
	<a href="/evc/server/lang?lang=en">English</a>
</body>
</html>