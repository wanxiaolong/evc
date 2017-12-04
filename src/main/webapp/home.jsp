<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<!DOCTYPE html>
<html>
<head>
	<title>中国英语村-主页</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="<%=basePath%>/vendor/bootstrap-3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" href="<%=basePath%>/vendor/font-awesome-4.7.0/css/font-awesome.min.css">
	<link rel="stylesheet" href="styles/home.css">
	<script src="<%=basePath%>/vendor/jquery-3.2.1/jquery-3.2.1.min.js"></script>
	<script src="<%=basePath%>/vendor/bootstrap-3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
	<jsp:include page="common/page-header.jsp"/>
	<div class="evc-content">
		<div class="jumbotron">
			<h1>Content example</h1>
			<p>This is a template showcasing the optional theme stylesheet included in Bootstrap. Use it as a starting point to create something more unique by building on or modifying it.</p>
		</div>
		<div class="container">
			<!-- Example row of columns -->
			<div class="row">
				<div class="col-md-4">
					<h2>Heading</h2>
					<p>Donec id elit non mi porta gravida at eget metus. Fusce
						dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh,
						ut fermentum massa justo sit amet risus. Etiam porta sem malesuada
						magna mollis euismod. Donec sed odio dui.</p>
					<p><a class="btn btn-default" href="#" role="button">View details»</a></p>
				</div>
				<div class="col-md-4">
					<h2>Heading</h2>
					<p>Donec id elit non mi porta gravida at eget metus. Fusce
						dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh,
						ut fermentum massa justo sit amet risus. Etiam porta sem malesuada
						magna mollis euismod. Donec sed odio dui.</p>
					<p><a class="btn btn-default" href="#" role="button">View details»</a></p>
				</div>
				<div class="col-md-4">
					<h2>Heading</h2>
					<p>Donec sed odio dui. Cras justo odio, dapibus ac facilisis
						in, egestas eget quam. Vestibulum id ligula porta felis euismod
						semper. Fusce dapibus, tellus ac cursus commodo, tortor mauris
						condimentum nibh, ut fermentum massa justo sit amet risus.</p>
					<p><a class="btn btn-default" href="#" role="button">View details»</a></p>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="common/page-footer.jsp"/>
</body>
</html>