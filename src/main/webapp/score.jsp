<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common"%>
<%@ page isELIgnored="false" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<!DOCTYPE html>
<html>
<head>
	<title>中国英语村-成绩查询</title>
	<common:import/>
	<script type="text/javascript" src="http://cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js"></script>
	<link type="text/css" rel="stylesheet" href="http://cdn.datatables.net/1.10.15/css/jquery.dataTables.min.css"></link>
</head>
<body>
	<common:page-header/>
	<div class="evc-content score">
		<!-- 查询条件 -->
		<div class="form-inline filter-block">
			<div class="title">查询条件</div>
			
			<div class="filter-row">
				<div class="filter">
					<input type="checkbox" id="queryAll">
					<label>查询所有历史成绩</label>
				</div>
				<div class="filter">
					<label>学期：</label>
					<select class="form-control" id="semesterSelect" name="semester" required>
						<option>--请选择--</option>
						<c:forEach items="${semesters}" var="semester">
							<option value="${semester.number}">${semester.name}</option>
						</c:forEach>
					</select>
				</div>
				<!-- 查询成绩时，不需要指定考试，选择学期后直接查询该学生该学期所有的考试成绩 -->
				<!-- <div class="filter">
					<label>考试：</label>
					<select class="form-control" id="examSelect" required>
						<option>--请选择--</option>
					</select>
				</div> -->
				<div class="filter" id="nameFilter">
					<label>姓名：</label>
					<%-- <input type="text" class="form-control" id="name" placeholder="支持首字母查询" value="${name}" required> --%>
					<select class="form-control" id="name" data-live-search="true"></select>
				</div>
				<div class="filter" id="birthdayFilter">
					<label>生日：</label>
					<input type="text" class="form-control" id="birthday" placeholder="学生生日" value="${birthday}" required>
				</div>
				<button type="button" id="queryBtn" class="btn btn-default query-btn">查询</button>
			</div>
		</div>

		<!-- 表格布局 -->
		<div class="title">查询结果</div>
		<table id="scoreTable" class="table table-striped table-bordered">
			<thead>
				<tr>
					<th>学号</th>
					<th>姓名</th>
					<th>学期</th>
					<th>考试</th>
					<th>语文</th>
					<th>数学</th>
					<th>英语</th>
					<th>物理</th>
					<th>化学</th>
					<th>生物</th>
					<th>政治</th>
					<th>历史</th>
					<th>地理</th>
					<th>总分</th>
				</tr>
			</thead>
			<tbody></tbody>
		</table>
	</div>
	<common:page-footer/>
</body>
<script type="text/javascript" src="<%=basePath%>/scripts/common.js"></script>
<script type="text/javascript" src="<%=basePath%>/scripts/score.js"></script>
</script>
</html>