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
	<title>中国英语村-考试管理</title>
	<common:import/>
	<admin:import/>
	
	<!-- For datatable -->
	<script src="http://cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js"></script>
	<link href="http://cdn.datatables.net/1.10.15/css/jquery.dataTables.min.css" rel="stylesheet"/>
	
	<!-- For select2 -->
	<link href="<%=basePath%>/vendor/select2-4.0.5/css/select2.min.css" rel="stylesheet"/>
	<script src="<%=basePath%>/vendor/select2-4.0.5/js/select2.min.js"></script>
	<!-- Select2 language resource file -->
	<script src="<%=basePath%>/vendor/select2-4.0.5/js/i18n/zh-CN.js"></script>
</head>
<body>
	<common:page-header/>
	<div class="admin evc-content">
		<admin:menu/>
		<div class="content">
			<div class="form-inline filter-block">
				<div class="title">系统中的所有考试信息</div>
				<div class="right">
					<button class="btn btn-primary btn-success" id="addBtn" data-toggle='modal' data-target='#myModal'>
						<i class="fa fa-plus-circle"></i>添加考试
					</button>
				</div>
			</div>
			
			<table id="examTable" class="table table-striped table-bordered">
				<thead>
					<tr>
						<th>ID</th>
						<th>学期</th>
						<th>考试名称</th>
						<th>参考科目</th>
						<th>人数</th>
						<th>考试日期</th>
						<th>班名</th>
						<th>级名</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody></tbody>
			</table>
		</div>
	</div>
	<common:page-footer/>
</body>
<script type="text/javascript" src="<%=basePath%>/scripts/admin/exam.js"></script>

<!-- 弹出层，用于修改数据 -->
<admin:modal-dialog action="提交更改" title="修改考试信息">
	<form id="edit-form">
		<input type="hidden" name="id">
		<div class="field">
			<div class="name">学期：</div>
			<div class="value">
				<select class="form-control" id="semesterSelect" name="semester" required>
					<option value="none">--请选择--</option>
				</select>
			</div>
		</div>
		<div class="field">
			<div class="name">考试名称：</div>
			<div class="value">
				<input type="text" name="name">
			</div>
		</div>
		<div class="field">
			<div class="name">参考人数：</div>
			<div class="value">
				<input type="text" name="people">
			</div>
		</div>
		<div class="field">
			<div class="name">考试日期：</div>
			<div class="value">
				<input type="text" name="date">yyyy-MM-dd
			</div>
		</div>
		<div class="field">
			<div class="name">显示班级排名：</div>
			<div class="value">
				<input type="text" name="isShowClassRank">'是'或'否'
			</div>
		</div>
		<div class="field">
			<div class="name">显示年级排名：</div>
			<div class="value">
				<input type="text" name="isShowGradeRank">'是'或'否'
			</div>
		</div>
		<div class="field" >
			<div class="name">参考科目：</div>
			<div class="value" id="subjects"></div>
		</div>
	</form>
</admin:modal-dialog>
</html>