<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common"%>
<%@ taglib prefix="admin" tagdir="/WEB-INF/tags/admin"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<!DOCTYPE html>
<html>
<head>
	<title>中国英语村-管理员主页</title>
	<common:import/>
	<admin:import/>
</head>
<body>
	<common:page-header/>
	<div class="admin evc-content">
		<admin:menu/>
		<div class="content">
			<div id="login">
				<div class="title">
					<p align="center">修改密码</p>
				</div>
				<div class="main">
					<div class="banner">
						<img src="../images/login/banner.jpg">
					</div>
					<div class="logmain">
						<div class="logdiv">
							<span class="text">原密码：</span>
							<input name="password" type="password" class="ipt">
						</div>
						<div class="logdiv">
							<span class="text">新密码：</span>
							<input name="password_new" type="password" class="ipt">
						</div>
						<div class="logdiv">
							<span class="text">确认密码：</span>
							<input name="password_confirm" type="password" class="ipt">
						</div>
						<div class="logdiv">
							<span class="text">验证码：</span>
							<input name="verify_code" type="text" class="ipt verify_code">
							<img class="verify_code_img" id="verify_code_img"/>
						</div>
						<div class="logdiv">
							<span class="text"></span>
							<input id="submitBtn" type="submit" class="btnbg" value="确认修改">
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<common:page-footer/>
</body>
<script type="text/javascript" src="<%=basePath%>/scripts/admin/change_pwd.js"></script>
</html>
