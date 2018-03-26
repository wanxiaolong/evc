<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common"%>

<%
	String ru = request.getParameter("ru");
%>

<!DOCTYPE html>
<html>
<head>
	<title>中国英语村-管理员登录</title>
	<common:import/>
</head>
<body>
	<common:page-header/>
	<div class="login evc-content">
		<form action="/evc/admin/login" method="POST">
			<div id="login">
				<div class="title">
					<p align="center">系统登录</p>
				</div>
				<div class="content">
					<div class="banner">
						<img src="../images/login/banner.jpg">
					</div>
					<div class="logmain">
						<div class="logdiv">
							<span class="text">账 号：</span>
							<input name="username" type="text" class="ipt">
						</div>
						<div class="logdiv">
							<span class="text">密 码：</span>
							<input name="password" type="password" class="ipt">
						</div>
						<div class="logdiv">
							<p class="text">&nbsp;</p>
							<a href="#" class="forgot-pwd">忘记密码</a>
							<input name="remember-pwd" type="checkbox" class="remember-pwd">记住密码
						</div>
						<div class="logdiv">
							<input type="submit" class="btnbg" value="点击登录">
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>
	<common:page-footer/>
</body>
</html>