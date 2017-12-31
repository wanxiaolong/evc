<%@ tag language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
	String username = request.getParameter("username");
%>

<div class="evc-page-header">
	<!-- 顶部 -->
	<div class="header row">
		<div class="logo col-md-3"></div>
		<div class="title col-md-9"></div>
	</div>
	<nav class="navbar navbar-default" role="navigation">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="<%=basePath%>/home.jsp">中国英语村</a>
			</div>
			<div>
				<!--向左对齐-->
				<ul class="nav navbar-nav navbar-left">
					<li><a href="<%=basePath%>/home.jsp">首页</a></li>
					<li><a href="<%=basePath%>/rest/score/query">成绩查询</a></li>
					<li><a href="<%=basePath%>/rest/file/list">资料下载</a></li>
					<li><a href="<%=basePath%>/rest/notice/list">公告栏</a></li>
					<li><a href="#">留言板</a></li>
					<li><a href="<%=basePath%>/contact.jsp">联系我们</a></li>
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
							更多
							<b class="caret"></b>
						</a>
						<ul class="dropdown-menu">
							<li><a href="<%=basePath%>/history.jsp">发展历程</a></li>
							<li><a href="#">家规</a></li>
							<li><a href="#">文章栏</a></li>
							<li><a href="#">教育业绩</a></li>
							<li><a href="<%=basePath%>/support.jsp">社会资助</a></li>
							<li class="divider"></li>
							<li><a href="<%=basePath%>/file_upload.jsp">资料上传</a></li>
							<li><a href="<%=basePath%>/score_upload.jsp">成绩上传</a></li>
							<li class="divider"></li>
							<li><a href="#">测试菜单</a></li>
						</ul>
					</li>
				</ul>
				<!--向右对齐-->
				<c:if test="${not empty model}">
					<p class="navbar-text navbar-right">
						<a href="<%=basePath%>/rest/user/logout">退出</a>
					</p>
					<p class="navbar-text navbar-right">欢迎：${model}</p>
				</c:if>
				<c:if test="${empty model}">
					<ul class="nav navbar-nav navbar-right">
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown">
								登录 <b class="caret"></b>
							</a>
							<ul class="dropdown-menu">
								<li><a href="<%=basePath%>/login.jsp">用户登录</a></li>
								<li class="divider"></li>
								<li><a href="#">管理员登录</a></li>
							</ul>
						</li>
					</ul>
				</c:if>
				<p class="navbar-text navbar-right">2017年12月5日</p>
			</div>
		</div>
	</nav>
</div>