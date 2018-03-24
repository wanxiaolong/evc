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
	
	<!-- For toastr(a message notifier plugin) -->
	<link href="<%=basePath%>/vendor/toastr-2.1.3/css/toastr.min.css" rel="stylesheet"/>
	<script src="<%=basePath%>/vendor/toastr-2.1.3/js/toastr.min.js"></script>
</head>
<body>
	<common:page-header/>
	<div class="admin evc-content">
		<admin:menu/>
		<div class="content">
			<table id="examTable" class="table table-striped table-bordered">
				<thead>
					<tr>
						<th>ID</th>
						<th>学期</th>
						<th>考试名称</th>
						<th>参考人数</th>
						<th>考试日期</th>
						<th>班级排名</th>
						<th>年级排名</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody></tbody>
			</table>
		</div>
	</div>
	<common:page-footer/>
</body>
<script type="text/javascript" src="<%=basePath%>/scripts/common.js"></script>
<script type="text/javascript" src="<%=basePath%>/scripts/admin/exam.js"></script>
</script>

<!-- 弹出层，用于修改数据 -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">修改考试信息</h4>
			</div>
			<div class="modal-body">
				<form id="edit-form">
					<input type="hidden" name="id">
					<div class="field">
						<span class="name">学期</span>
						<input type="text" class="value" name="semesterName" placeholder="学期" disabled="disabled">
					</div>
					<div class="field">
						<span class="name">考试名称</span>
						<input type="text" class="value" name="name" placeholder="考试名称">
					</div>
					<div class="field">
						<span class="name">参考人数</span>
						<input type="text" class="value" name="people" placeholder="参考人数">
					</div>
					<div class="field">
						<span class="name">考试日期</span>
						<input type="text" class="value" name="date" placeholder="考试日期">
					</div>
					<div class="field">
						<span class="name">显示班级排名</span>
						<input type="radio" class="value" name="isShowClassRank" value="true">是&nbsp;
						<input type="radio" class="value" name="isShowClassRank" value="false">否
					</div>
					<div class="field">
						<span class="name">显示年级排名</span>
						<input type="radio" class="value" name="isShowGradeRank" value="true">是&nbsp;
						<input type="radio" class="value" name="isShowGradeRank" value="false">否
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