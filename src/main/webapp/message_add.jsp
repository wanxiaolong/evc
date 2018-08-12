<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<!DOCTYPE html>
<html>
<head>
	<title>中国英语村-我要留言</title>
	<common:import />
</head>
<body>
	<common:page-header />
	<div class="evc-content">
		<div class="evc-message">
			<div>
				<h3>添加留言</h3>
			</div>
			<form action="message/create" method="POST">
				<div class="row">
					<span class="key">标题：</span>
					<input type="text" name="title" maxlength="50" required>
				</div>
				<div class="row">
					<span class="key">类型：</span>
					<input type="radio" name="type" value="1">提问
					<input type="radio" name="type" value="2">建议
					<input type="radio" name="type" value="3">吐槽
				</div>
				<div class="row">
					<span class="key">昵称：</span>
					<input type="text" name="contact" maxlength="50" required>
				</div>
				<div class="row">
					<span class="key">内容：</span>
					<div class="content">
						<textarea name="content" maxlength="500" placeholder="请输入内容，500字以内" required></textarea>
					</div>
				</div>
				<div class="submit">
					<input type="submit" id="submit" value="提交">
				</div>
			</form>
		</div>
	</div>
	<common:page-footer />
</body>
<script type="text/javascript" src="<%=basePath%>/scripts/message_add.js"></script>
</html>