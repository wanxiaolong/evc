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
	<title>中国英语村-学生管理</title>
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
				<div class="title">系统中的所有学生信息</div>
				<div class="right">
					<button class="btn btn-primary btn-success" id="addBtn" data-toggle='modal' data-target='#myModal'>
						<i class="fa fa-plus-circle"></i>添加学生
					</button>
				</div>
			</div>
			
			<table id="studentTable" class="table table-striped table-bordered">
				<thead>
					<tr>
						<th>ID</th>
						<th>学号</th>
						<th>姓名</th>
						<th>姓名拼音</th>
						<!-- <th>性别</th> -->
						<th>年级</th>
						<th>班级</th>
						<!-- <th>出生年份</th> -->
						<th>生日</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody></tbody>
			</table>
		</div>
	</div>
	<common:page-footer/>
</body>
<script type="text/javascript" src="<%=basePath%>/scripts/admin/student.js"></script>

<!-- 弹出层，用于修改数据 -->
<admin:modal-dialog action="提交更改" title="修改学生信息">
	<form id="edit-form">
		<input type="hidden" name="id">
		<div class="field">
			<div class="name">学号：</div>
			<div class="value">
				<input type="text" name="number">
			</div>
		</div>
		<div class="field">
			<div class="name">姓名：</div>
			<div class="value">
				<input type="text" name="name">
			</div>
		</div>
		<div class="field">
			<div class="name">拼音：</div>
			<div class="value">
				<input type="text" name="namePinyin">
			</div>
		</div>
		<!-- <div class="field">
			<div class="name">性别：</div>
			<div class="value">
				<input type="text" name="sex">'男'或'女'
			</div>
		</div> -->
		<div class="field">
			<div class="name">年级：</div>
			<div class="value">
				<input type="text" name="grade">
			</div>
		</div>
		<div class="field">
			<div class="name">班级：</div>
			<div class="value">
				<input type="text" name="clazz">
			</div>
		</div>
		<div class="field">
			<div class="name">出生年份：</div>
			<div class="value">
				<input type="text" name="birthYear">
			</div>
		</div>
		<div class="field">
			<div class="name">生日：</div>
			<div class="value">
				<input type="text" name="birthDay">
			</div>
		</div>
	</form>
</admin:modal-dialog>
</html>