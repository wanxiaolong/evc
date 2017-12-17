<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common"%>

<!DOCTYPE html>
<html>
<head>
	<title>中国英语村-用户登录</title>
	<common:import/>
</head>
<body>
	<common:page-header/>
	<div class="evc-content">
		<form action="rest/user/login" method="POST">
			<div class="evc-login">
				<div class="text-center">
					<h3>用户登录</h3>
				</div>
				<div class="row">
					<input type="text" class="form-control" name="username" placeholder="请输入账户名" required autofocus/>
				</div>
				<div class="row">
					<input type="password" class="form-control" name="password" placeholder="请输入密码" required autofocus/>
				</div>
				<div class="row">
					<div class="col-lg-6">
						<input type="checkbox">记住密码</input>
					</div>
					<a class="col-lg-6 text-right" href="#">忘记密码？</a>
				</div>
				<div class="row">
					<button type="submit" class="btn btn-success col-lg-12">登录</button>
				</div>
			</div>
		</form>
	</div>
	<common:page-footer/>
</body>
</html>