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
				<span class="key">标题：</span>
				<span>${model.title}</span>
			</div>
			<div class="row">
				<span class="key">发布者：</span>
				<span>${model.userName}</span>
			</div>
			<div class="row">
				<span class="key">重要性：</span>
				<span>${model.importantLevel}</span>
			</div>
			<div class="row">
				<span class="key">创建时间：</span>
				<span>${model.creationDate}</span>
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