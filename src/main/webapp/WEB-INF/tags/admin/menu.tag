<%@ tag language="java" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<div>
	<h1>管理员主页</h1>
</div>
<nav class="navbar-default navbar-side" role="navigation">
	<div class="sidebar-collapse">
		<ul class="nav" id="main-menu">
			<li>
				<a href="<%=basePath%>/admin/score.jsp">
					<i class="fa fa-desktop"></i>成绩查询（全班）
				</a>
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