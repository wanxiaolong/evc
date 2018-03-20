<%@ tag language="java" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<link href="<%=basePath%>/styles/admin/home.css" rel="stylesheet">
<script src="<%=basePath%>/scripts/admin/home.js"></script>