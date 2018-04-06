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
	<title>中国英语村-学期管理</title>
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
				<div class="title">系统中的所有轮播信息</div>
				<div class="right">
					<button class="btn btn-primary btn-success" id="addBtn" data-toggle='modal' data-target='#myModal'>
						<i class="fa fa-plus-circle"></i>添加轮播
					</button>
				</div>
			</div>
			
			<table id="dataTable" class="table table-striped table-bordered">
				<thead>
					<tr>
						<th>ID</th>
						<th>图片地址</th>
						<th>链接地址</th>
						<th>文字</th>
						<th>显示顺序</th>
						<th>是否显示</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody></tbody>
			</table>
		</div>
	</div>
	<common:page-footer/>
</body>
<script type="text/javascript" src="<%=basePath%>/scripts/admin/carousel.js"></script>

<!-- 弹出层，用于修改数据 -->
<admin:modal-dialog action="提交更改" title="修改轮播信息">
	<form id="edit-form">
		<input type="hidden" name="id">
		<div class="field">
			<div class="name">图片地址：</div>
			<div class="value">
				<input type="text" name="imgUrl">
			</div>
		</div>
		<div class="field">
			<div class="name">链接地址：</div>
			<div class="value">
				<input type="text" name="linkUrl">
			</div>
		</div>
		<div class="field">
			<div class="name">文字：</div>
			<div class="value">
				<input type="text" name="altText">
			</div>
		</div>
		<div class="field">
			<div class="name">显示顺序：</div>
			<div class="value">
				<input type="text" name="order">
			</div>
		</div>
		<div class="field">
			<div class="name">是否显示：</div>
			<div class="value">
				<input type="text" name="enabled">'是'或'否'
			</div>
		</div>
	</form>
</admin:modal-dialog>
</html>