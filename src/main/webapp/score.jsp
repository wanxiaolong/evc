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
	<link href="<%=basePath%>/vendor/select2-4.0.5/css/select2.min.css" rel="stylesheet"/>
	<script src="<%=basePath%>/vendor/select2-4.0.5/js/select2.min.js"></script>
	<!-- Select2 language resource file -->
	<script src="<%=basePath%>/vendor/select2-4.0.5/js/i18n/zh-CN.js"></script>
</head>
<body>
	<common:page-header/>
	<div class="evc-content score">
		<!-- 查询条件 -->
		<div class="form-inline filter-block">
			<div class="message">
				最近一次考试：<span id="examSemester"></span>
				-<span id="examName"></span>，
				参考人数：<span id="examPeople"></span>。
			</div>
			<div class="title">查询条件</div>
			<div class="filter-row">
				<div class="filter">
					<input type="checkbox" id="queryAll">
					<label>查询所有历史成绩</label>
				</div>
				<div class="filter" id="semesterFilter">
					<label>学期：</label>
					<select class="form-control" id="semesterSelect" name="semester" data-live-search="true" required>
						<option value="none">--请选择--</option>
						<c:forEach items="${semesters}" var="semester">
							<option value="${semester.number}">${semester.name}</option>
						</c:forEach>
					</select>
				</div>
				<div class="filter" id="nameFilter">
					<label>姓名：</label>
					<select class="form-control" id="nameSelect" data-live-search="true" required></select>
				</div>
				<div class="filter" id="birthdayFilter">
					<label>生日：</label>
					<input type="tel" class="form-control" id="birthday" placeholder="学生生日" value="${birthday}" maxlength="4" required>
				</div>
				<button type="button" id="queryBtn" class="btn btn-default query-btn">查询</button>
			</div>
		</div>

		<!-- 表格布局 -->
		<div class="title">查询结果</div>
		<table id="scoreTable" class="table table-striped table-bordered hide">
			<thead>
				<tr id="headerRow">
					<!-- 以下字段是固定的，每次查询都有 -->
					<th>学号</th>
					<th>姓名</th>
					<th>学期</th>
					<th>考试</th>
					
					<!-- 这里是动态列，将在JS中初始化 -->
				</tr>
			</thead>
			<tbody></tbody>
		</table>
	</div>
	<common:page-footer/>
</body>
<script type="text/javascript" src="<%=basePath%>/scripts/score.js"></script>
</script>
</html>