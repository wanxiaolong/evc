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
			<div>
				<h3>留言详情</h3>
			</div>
			<div class="row">
				<span class="key">标题：</span>
				<span>${model.title}</span>
			</div>
			<div class="row">
				<span class="key">类型：</span>
				<span>${model.type}</span>
			</div>
			<div class="row">
				<span class="key">昵称：</span>
				<span>${model.contact}</span>
			</div>
			<div class="row">
				<span class="key">内容：</span>
				<div class="content">${model.content}</div>
			</div>
		</div>
	</div>
	<common:page-footer />
</body>
</html>