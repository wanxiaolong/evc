<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
	<title>中国英语村-留言详情</title>
	<common:import />
</head>
<body>
	<common:page-header />
	<div class="evc-content">
		<div class="evc-message">
			<div><h3>留言详情</h3></div>
			<form action="message/create" method="POST">
				<div class="row">
					<div class="col-md-6">
						<label for="type">类型</label><br>
						<input name="type" value="${model.type}"><br>
					</div>
					<div class="col-md-6">
						<label for="contact">联系方式</label><br>
						<input name="contact" value="${model.contact}"><br>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<label for="title">标题</label><br>
						<input name="title" value="${model.title}"><br>
					</div>
					<div class="col-md-6">
						<label for="creationDate">创建时间</label><br>
						<input name="creationDate" value="${model.creationDate}"><br>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<label for="content">内容</label><br>
						<textarea name="content" style="width: 1200px; height: 200px;">${model.content}</textarea><br>
					</div>
				</div>
				<c:if test="${empty model}">
					<div class="submit">
						<input type="submit" value="提交">
					</div>
				</c:if>
			</form>
		</div>
	</div>
	<common:page-footer />
</body>
</html>