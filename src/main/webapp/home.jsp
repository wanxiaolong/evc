<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<!DOCTYPE html>
<html>
<head>
	<title>中国英语村-主页</title>
	<common:import/>
</head>
<body>
	<common:page-header/>
	<div class="evc-content">
		<div id="myCarousel" class="carousel slide">
			<!-- 轮播（Carousel）指标 -->
			<ol class="carousel-indicators">
				<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
				<li data-target="#myCarousel" data-slide-to="1"></li>
				<li data-target="#myCarousel" data-slide-to="2"></li>
				<li data-target="#myCarousel" data-slide-to="3"></li>
				<li data-target="#myCarousel" data-slide-to="4"></li>
			</ol>
			<!-- 轮播（Carousel）项目 -->
			<div class="carousel-inner">
				<div class="item active">
					<img src="<%=basePath%>/images/slides/slide1.jpg" alt="打篮球">
				</div>
				<div class="item">
					<img src="<%=basePath%>/images/slides/slide2.jpg" alt="乒乓球">
				</div>
				<div class="item">
					<img src="<%=basePath%>/images/slides/slide3.jpg" alt="学生作品">
				</div>
				<div class="item">
					<img src="<%=basePath%>/images/slides/slide5.jpg" alt="圣诞晚会">
				</div>
				<div class="item">
					<img src="<%=basePath%>/images/slides/slide6.jpg" alt="一起学习">
				</div>
			</div>
			<!-- 轮播（Carousel）导航 -->
			<a class="carousel-control left" href="#myCarousel" data-slide="prev">&lsaquo;</a>
			<a class="carousel-control right" href="#myCarousel" data-slide="next">&rsaquo;</a>
		</div> 
		<div class="evc-desc">
			<h3>简介</h3>
			<p>
			中国英语村秦妈妈留守儿童之家(Qin Mama Parental Home for Left-behind Kids, EVC) 位于四川省资阳市雁江区石岭镇金带铺场镇。
			自2000年以来，为社会托管留守儿童200余人（其中有些是当地留 守儿童，也有来自于外地的留守儿童---诸如小院、伍隍、资阳、资中、内江、威远、巴中、自贡、成都、德阳、德昌、凉山等地），
			现有留守儿童70余人，孩子们都将秦妈妈留守儿童之家当成自己的家，秦妈妈留守儿童之家为孩子们提供良好的学习环境，营造温馨、舒适的家园，将留守儿童当作自己亲身儿女来管理、教育。
			周末、节假日常开展一些丰富多彩的有益于孩子身心健康的各类活动，让孩子们在这个充满爱心的大家园里健康地成长。让在外务工的父母放心地工作，解决了农民工的后顾之虑。
			秦妈妈留守儿童之家日益受到社会的青睐。
			</p>
			<p>
			中国英语村秦妈妈留守儿童之家专为留守儿童编有中学英语电子试卷(High School English Electronic Paper)，
			适用于中学生、家长、英语教师及其他英语爱好者。它有利于学生学习、巩固、提高英语知识；教师可用它来检测学生的英语学习，
			避免以前考试后批阅大堆大堆试卷的苦恼；也方便家长敦促孩子的英语学习。供大家免费下载使用。
			</p>
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
	<common:page-footer/>
</body>
</html>