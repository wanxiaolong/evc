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
	<title>中国英语村-全班成绩查询</title>
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
	<div class="admin evc-content score">
		<admin:menu/>
		<div class="content">
			<!-- 查询条件 -->
			<div class="form-inline filter-block">
				<div class="title">查询条件</div>
				
				<div class="filter-row">
					<div class="filter" id="semesterFilter">
						<label>学期：</label>
						<select class="form-control" id="semesterSelect" name="semester" data-live-search="true" required>
							<option>--请选择--</option>
						</select>
					</div>
					<div class="filter" id="semesterFilter">
						<label>考试：</label>
						<select class="form-control" id="examSelect" name="exam" data-live-search="true" required>
							<option>--请选择--</option>
						</select>
					</div>
					<button type="button" id="queryBtn" class="btn btn-default query-btn">
						<i class="fa fa-search"></i>查询
					</button>
				</div>
			</div>
	
			<!-- 表格布局 -->
			<div class="title">查询结果</div>
			<table id="scoreTable" class="table table-striped table-bordered">
				<thead>
					<tr>
						<th>ID</th>
						<th>学号</th>
						<th>姓名</th>
						<th>语文</th>
						<th>数学</th>
						<th>英语</th>
						<th>物理</th>
						<th>化学</th>
						<th>生物</th>
						<th>政治</th>
						<th>历史</th>
						<th>地理</th>
						<th>总分</th>
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
<script type="text/javascript" src="<%=basePath%>/scripts/admin/score.js"></script>

<!-- 弹出层，用于修改数据 -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">修改成绩</h4>
			</div>
			<div class="modal-body">
				<form id="edit-form">
					<input type="hidden" name="id">
					<div class="field">
						<span class="name">语文</span>
						<div class="value">
							<input type="text" name="chinese">
						</div>
					</div>
					<div class="field">
						<span class="name">数学</span>
						<div class="value">
							<input type="text" name="math">
						</div>
					</div>
					<div class="field">
						<span class="name">英语</span>
						<div class="value">
							<input type="text" name="english">
						</div>
					</div>
					<div class="field">
						<span class="name">物理</span>
						<div class="value">
							<input type="text" name="physics">
						</div>
					</div>
					<div class="field">
						<span class="name">化学</span>
						<div class="value">
							<input type="text" name="chemistry">
						</div>
					</div>
					<div class="field">
						<span class="name">生物</span>
						<div class="value">
							<input type="text" name="biologic">
						</div>
					</div>
					<div class="field">
						<span class="name">政治</span>
						<div class="value">
							<input type="text" name="politics">
						</div>
					</div>
					<div class="field">
						<span class="name">历史</span>
						<div class="value">
							<input type="text" name="history">
						</div>
					</div>
					<div class="field">
						<span class="name">地理</span>
						<div class="value">
							<input type="text" name="geography">
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary" id="confirm-update">提交更改</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>
</html>