<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<!DOCTYPE html>
<html>
<head>
<title>中国英语村-留言栏</title>
	<common:import />
	<script type="text/javascript" src="http://cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js"></script>
	<link type="text/css" rel="stylesheet" href="http://cdn.datatables.net/1.10.15/css/jquery.dataTables.min.css"></link>
</head>
<body>
	<common:page-header />
	<div class="evc-content">
		<div class="evc-message">
			<div class="right">
				<button class="btn btn-primary btn-success" onclick="javascript:location.href='/evc/message_add.jsp'">
					<i class="fa fa-plus-circle"></i>我要留言
				</button>
			</div>
			<table id="messageTable" class="table table-striped column1-left">
				<thead>
					<tr>
						<th>ID</th>
						<th>标题</th>
						<th>类型</th>
						<th>发布者</th>
						<th>联系方式</th>
						<th>创建日期</th>
					</tr>
				</thead>
				<tbody></tbody>
			</table>
		</div>
	</div>
	<common:page-footer />
</body>
<script type="text/javascript" src="<%=basePath%>/scripts/message.js"></script>
</html>