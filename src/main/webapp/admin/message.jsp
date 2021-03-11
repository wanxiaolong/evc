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
	<title>中国英语村-留言管理</title>
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
				<div class="title">系统中的所有留言信息</div>
				<div class="right">
				</div>
			</div>
			
			<table id="messageTable" class="table table-striped table-bordered">
				<thead>
					<tr>
						<th>ID</th>
						<th>类型</th>
						<th>标题</th>
						<th>内容</th>
						<th>昵称</th>
						<th>联系方式</th>
						<th>发布时间</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody></tbody>
			</table>
		</div>
	</div>
	<common:page-footer/>
</body>
<script type="text/javascript" src="<%=basePath%>/scripts/admin/message.js"></script>

<!-- 弹出层，用于修改数据 -->
<admin:modal-dialog action="提交更改" title="修改留言信息">
	<form id="edit-form">
		<input type="hidden" name="id">
		<div class="field">
			<div class="name">类型：</div>
			<div class="value">
				<input type="text" name="type">
			</div>
		</div>
		<div class="field">
			<div class="name">标题：</div>
			<div class="value">
				<input type="text" name="title">
			</div>
		</div>
		<div class="field">
			<div class="name">内容：</div>
			<div class="value">
				<input type="text" name="content">
			</div>
		</div>
		<div class="field">
			<div class="name">昵称：</div>
			<div class="value">
				<input type="text" name="nick">
			</div>
		</div>
		<div class="field">
			<div class="name">联系方式：</div>
			<div class="value">
				<input type="text" name="contact">
			</div>
		</div>
	</form>
</admin:modal-dialog>
</html>