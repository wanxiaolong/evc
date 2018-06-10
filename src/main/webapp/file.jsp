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
	<title>中国英语村-资料下载</title>
	<common:import />
	<script type="text/javascript" src="http://cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js"></script>
	<link type="text/css" rel="stylesheet" href="http://cdn.datatables.net/1.10.15/css/jquery.dataTables.min.css"></link>
</head>
<body>
	<common:page-header />
	<div class="evc-content">
		<div class="evc-file">
			<table id="fileTable" class="table table-striped column1-left">
				<thead>
					<tr>
						<th>ID</th>
						<th>文件名</th>
						<th>下载次数</th>
						<th>创建日期</th>
					</tr>
				</thead>
				<tbody></tbody>
			</table>
		</div>
	</div>
	<common:page-footer />
</body>
<script type="text/javascript" src="<%=basePath%>/scripts/file.js"></script>
</html>