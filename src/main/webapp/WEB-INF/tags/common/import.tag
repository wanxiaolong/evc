<%@ tag language="java" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="<%=basePath%>/vendor/bootstrap-3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=basePath%>/vendor/font-awesome-4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="styles/evc.css">
<script src="<%=basePath%>/vendor/jquery-3.2.1/jquery-3.2.1.min.js"></script>
<script src="<%=basePath%>/vendor/bootstrap-3.3.7/js/bootstrap.min.js"></script>