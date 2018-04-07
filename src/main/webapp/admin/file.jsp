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
	<title>中国英语村-文件管理</title>
	<common:import/>
	<admin:import/>
	
	<!-- For datatable -->
	<script src="http://cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js"></script>
	<link href="http://cdn.datatables.net/1.10.15/css/jquery.dataTables.min.css" rel="stylesheet"/>
</head>
<body>
	<common:page-header/>
	<div class="admin evc-content">
		<admin:menu/>
		<div class="content">
			<div class="form-inline filter-block">
				<div class="title">系统中的所有文件信息</div>
				<div class="right">
					<button class="btn btn-primary btn-success" id="addBtn">
						<i class="fa fa-plus-circle"></i>文件上传
					</button>
				</div>
			</div>
			
			<table id="fileTable" class="table table-striped table-bordered column1-left">
				<thead>
					<tr>
						<th>ID</th>
						<th>文件名</th>
						<th>路径</th>
						<th>下载次数</th>
						<th>创建日期</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody></tbody>
			</table>
		</div>
	</div>
	<common:page-footer/>
</body>
<script type="text/javascript" src="<%=basePath%>/scripts/admin/file.js"></script>
</html>