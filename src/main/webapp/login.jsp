<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common"%>

<!DOCTYPE html>
<html>
<head>
	<title>中国英语村-登录</title>
	<common:import/>
</head>
<body>
	<div class="evc-content">
		<form action="rest/user/login" method="POST">
			<div>
				<input type="text" name="username" placeholder="请输入用户名"/>
			</div>
			<div>
				<input type="password" name="password" placeholder="请输入密码"/>
			</div>
			<div>
				<input type="submit" value="提交"/>
			</div>
		</form>
	</div>
	<common:page-footer/>
</body>
</html>