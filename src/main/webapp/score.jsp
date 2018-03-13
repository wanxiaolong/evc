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
	
	<!-- For datatable -->
	<script src="http://cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js"></script>
	<link href="http://cdn.datatables.net/1.10.15/css/jquery.dataTables.min.css" rel="stylesheet"/>
	
	<!-- For select2 -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/js/select2.min.js"></script>
	<link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.6-rc.0/css/select2.min.css" rel="stylesheet"/>
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
					<select class="form-control" id="semesterSelect" name="semester" data-live-search="true" required>
						<option>--请选择--</option>
						<c:forEach items="${semesters}" var="semester">
							<option value="${semester.number}">${semester.name}</option>
						</c:forEach>
					</select>
				</div>
				<div class="filter" id="nameFilter">
					<label>姓名：</label>
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