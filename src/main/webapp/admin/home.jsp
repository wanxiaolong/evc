<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title>中国英语村-管理员主页</title>
	<common:import/>
	<link rel="stylesheet" href="<%=basePath%>/styles/admin/home.css">
	<script src="<%=basePath%>/scripts/admin/home.js"></script>
</head>
<body>
	<common:page-header/>
	<div id="wrapper" class="evc-content">
		<nav class="navbar-default navbar-side" role="navigation">
			<div class="sidebar-collapse">
				<ul class="nav" id="main-menu">
					<li>
						<a href="#"><i class="fa fa-desktop"></i>成绩查询（全班）</a>
					</li>
					<li>
						<a href="#"><i class="fa fa-table"></i>成绩上传</a>
					</li>
					<li>
						<a href="#">
							<i class="fa fa-edit"></i>文件上传 <span class="badge">新增</span>
						</a>
					</li>
					<li><a href="#"><i class="fa fa-qrcode"></i>考试管理</a></li>
					<li><a href="#"><i class="fa fa-bar-chart-o"></i>成绩管理</a></li>
					<li><a href="#"><i class="fa fa-edit"></i>密码修改</a></li>
				</ul>
			</div>
		</nav>
		<div id="page-wrapper">
			<h2>这里是主要内容</h2>
			<hr />
		</div>
	</div>
	<common:page-footer/>
</body>
</html>
