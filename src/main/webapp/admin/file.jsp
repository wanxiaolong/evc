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

	<link rel="stylesheet" href="<%=basePath%>/vendor/bootstrap-fileinput-4.4.7/css/fileinput.min.css">
	<script src="<%=basePath%>/vendor/bootstrap-fileinput-4.4.7/js/fileinput.min.js"></script>
	<!-- FileInput插件的本地化设置，使用时需提前引入\locales文件夹下对应的语言文件，中文zh，引入语言文件必须放在fileinput.js之后 -->
	<script src="<%=basePath%>/vendor/bootstrap-fileinput-4.4.7/js/locales/zh.js"></script>
</head>
<body>
	<common:page-header/>
	<div class="admin evc-content">
		<admin:menu/>
		<div class="content tab-content">
			<!-- 标签页 -->
			<ul id="myTab" class="nav nav-tabs">
				<li class="active">
					<a href="#fileManagement" data-toggle="tab">文件管理</a>
				</li>
				<li>
					<a href="#fileUpload" data-toggle="tab">文件上传</a>
				</li>
			</ul>
			
			<!-- 文件管理Tab页 -->
			<div id="fileManagement" class="tab-pane fade in active">
				<div class="form-inline filter-block">
					<div class="title">系统中的所有文件信息</div>
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
			
			<!-- 文件上传Tab页 -->
			<div id="fileUpload" class="tab-pane fade">
				<div class="row">
					<p>支持一次上传多个文件，支持预览：</p>
				</div>
				<div class="row">
					<input id="uploadfile" name="input-b1" type="file" multiple>
				</div>
			</div>
		</div>
	</div>
	<common:page-footer/>
</body>
<script type="text/javascript" src="<%=basePath%>/scripts/admin/file.js"></script>
</html>