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
					<i class="fa fa-search"></i>成绩查询（全班）
				</a>
			</li>
			<li>
				<a href="<%=basePath%>/admin/score_upload.jsp">
					<i class="fa fa-upload"></i>成绩上传
				</a>
			</li>
			<li><a href="#"><i class="fa fa-bar-chart"></i>成绩管理</a></li>
			<li><a href="#"><i class="fa fa-bar-chart"></i>科目管理</a></li>
			<li><a href="#"><i class="fa fa-bar-chart"></i>考试管理</a></li>
			<li>
				<a href="<%=basePath%>/admin/file_upload.jsp">
					<i class="fa fa-upload"></i>文件上传 <span class="badge">新增</span>
				</a>
			</li>
			<li>
				<a href="#">
					<i class="fa fa-cog"></i>首页轮播设置
				</a>
			</li>
			<li>
				<a href="<%=basePath%>/admin/change_pwd.jsp">
					<i class="fa fa-wrench"></i>密码修改
				</a>
			</li>
			<li>
				<a href="<%=basePath%>/admin/change_pwd.jsp">
					<i class="fa fa-exclamation-triangle"></i>备份还原<span class="badge">二期</span>
				</a>
			</li>
		</ul>
	</div>
</nav>