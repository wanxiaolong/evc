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
				最近一次考试：<span id="examSemester"></span>-<span id="examName"></span>，
				考试日期：<span id="examDate"></span>，
				参考人数：<span id="examPeople"></span>。
			</div>
			<div class="title">查询说明</div>
			<div class="desc">
				<p>1. 勾选“<b>查询所有历史成绩</b>”，则“学期”不可选，查询该学生所有的历史成绩，否则查询指定学期所有考试的成绩
				<p>2. 姓名支持中文或<span class="highlight">拼音首字母查询</span>。比如姓名是“张三”，则可输入“张三”或“zs”</p>
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
					<input type="tel" class="form-control" id="birthday" placeholder="4位数字，比如0308" value="${birthday}" maxlength="4" required>
				</div>
				<button type="button" id="queryBtn" class="btn btn-default query-btn">查询</button>
			</div>
		</div>

		<!-- 表格布局 -->
		<div class="title">查询结果</div>
		<table id="scoreTable" class="table score-table hide">
		</table>
	</div>
	<common:page-footer/>
</body>
<script type="text/javascript" src="<%=basePath%>/scripts/score.js"></script>
</script>
</html>