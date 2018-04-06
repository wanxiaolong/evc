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
		<div class="evc-p-container">
			<h3>中国英语村秦妈妈留守儿童之家简介</h3>
			<p>
			中国英语村秦妈妈留守儿童之家(Qin Mama Parental Home for Left-behind Kids, EVC) 位于四川省资阳市雁江区石岭镇金带铺场镇。
			自2000年以来，为社会托管留守儿童200余人（其中有些是当地留 守儿童，也有来自于外地的留守儿童---诸如小院、伍隍、资阳、资中、内江、威远、巴中、自贡、成都、德阳、德昌、凉山等地），
			现有留守儿童70余人，孩子们都将秦妈妈留守儿童之家当成自己的家，秦妈妈留守儿童之家为孩子们提供良好的学习环境，营造温馨、舒适的家园，将留守儿童当作自己亲身儿女来管理、教育。
			周末、节假日常开展一些丰富多彩的有益于孩子身心健康的各类活动，让孩子们在这个充满爱心的大家园里健康地成长。让在外务工的父母放心地工作，解决了农民工的后顾之虑。
			</p>
			<p>
			中国英语村秦妈妈留守儿童之家专为留守儿童编有中学英语电子试卷(High School English Electronic Paper)，
			适用于中学生、家长、英语教师及其他英语爱好者。它有利于学生学习、巩固、提高英语知识；教师可用它来检测学生的英语学习，
			避免以前考试后批阅大堆大堆试卷的苦恼；也方便家长敦促孩子的英语学习。供大家免费下载使用。
			</p>
		</div>
		<div id="home_carousel" class="carousel slide">
			<!-- 轮播指示器，将会根据后端配置动态显示 -->
			<ol class="carousel-indicators"></ol>
			<!-- 轮播内容，将会根据后端配置动态显示 -->
			<div class="carousel-inner"></div>
			<!-- 轮播左右控制按钮 -->
			<a class="carousel-control left" href="#home_carousel" data-slide="prev">&lsaquo;</a>
			<a class="carousel-control right" href="#home_carousel" data-slide="next">&rsaquo;</a>
		</div> 
		<div class="container">
			<!-- Example row of columns -->
			<div class="row">
				<div class="col-md-4">
					<h3>文化交流活动</h3>
					<p>秦妈妈留守儿童到大学校园与外国人用英语交流，旨在提高孩子学习语言的兴趣，让英语学以致用，让孩子体会语言交际功能。</p>
					<p><a class="btn btn-default" href="#" role="button">查看详情»</a></p>
				</div>
				<div class="col-md-4">
					<h3>献爱心活动</h3>
					<p>秦妈妈留守儿童之家会不定期组织孩子到敬老院为老人献爱心，包括赠送水果，打扫卫生，搀扶老人走路等，旨在培养孩子要尊敬
					老人，孝敬老人的思想。</p>
					<p><a class="btn btn-default" href="#" role="button">查看详情»</a></p>
				</div>
				<div class="col-md-4">
					<h3>文体活动</h3>
					<p>秦妈妈留守儿童之家还会不定期的组织孩子参加各类文体活动，包括打乒乓球，打篮球，象棋比赛，围棋比赛，书画比赛等，
					让孩子充分发挥自己的特长，培养其兴趣爱好，充实孩子的学习生活。</p>
					<p><a class="btn btn-default" href="#" role="button">查看详情»</a></p>
				</div>
			</div>
		</div>
	</div>
	<common:page-footer/>
	<script type="text/javascript" src="<%=basePath%>/scripts/home.js"></script>
</body>
</html>