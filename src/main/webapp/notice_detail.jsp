<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
<title>中国英语村-公告详情</title>
<common:import />
</head>
<body>
	<common:page-header />
	<div class="evc-content">
		<div class="evc-notice">
			<div><h3>公告详情</h3></div>
			<div class="row">
				<div class="col-md-6">
					<label for="title">标题</label><br>
					<input name="title" value="${model.title}" disabled="disabled"><br>
				</div>
				<div class="col-md-6">
					<label for="author">发布者</label><br>
					<input name="author" value="${model.adminName}" disabled="disabled"><br>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6">
					<label for="importantLevel">重要性</label><br>
					<input name="importantLevel" value="${model.importantLevel}" disabled="disabled"><br>
				</div>
				<div class="col-md-6">
					<label for="creationDate">创建时间</label><br>
					<input name="creationDate" value="${model.creationDate}" disabled="disabled"><br>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<label for="content">内容</label><br>
					<textarea name="content" style="width: 1200px; height: 200px;">${model.content}</textarea><br>
				</div>
			</div>
		</div>
	</div>
	<common:page-footer />
</body>
</html>