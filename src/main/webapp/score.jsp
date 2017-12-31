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
</head>
<body>
	<common:page-header/>
	<div class="evc-content">
		<!-- 查询条件 -->
		<form class="form-inline" role="form" method="POST" action="<%=basePath%>/rest/score/query">
			<div>查询条件</div>
			<div class="form-group">
				<label>学期：</label>
				<select class="form-control">
					<option>2017年第二学期</option>
					<option>2017年第一学期</option>
					<option>2016年第二学期</option>
					<option>2016年第一学期</option>
				</select>
			</div>
			<div class="form-group">
				<label>考试：</label>
				<select class="form-control" name="exam_id">
					<option value="3">半期考试</option>
					<option value="1">第一次月考</option>
					<option value="4">期末考试</option>
				</select>
			</div>
			<div class="form-group">
				<label>姓名：</label>
				<input type="text" class="form-control" name="name" placeholder="学生姓名" value="${name}">
			</div>
			<div class="form-group">
				<label>生日：</label>
				<input type="text" class="form-control" name="birthday" placeholder="学生生日" value="${birthday}">
			</div>
			<div class="form-group">
				<label>验证码：</label>
				<input type="text" class="form-control" name="verify_code" placeholder="验证码">
				<img/>
			</div>
			<button type="submit" class="btn btn-default">提交</button>
		</form>

		<!-- 表格布局 -->
		<table class="table table-striped table-bordered">
			<caption>查询结果</caption>
			<thead>
				<tr>
					<th>学号</th>
					<th>姓名</th>
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
			<tbody>
				<c:if test="${empty model}">
					<div>没有记录！</div>
				</c:if>
				<c:if test="${not empty model}">
					<c:forEach items="${model}" var="score">
						<tr>
							<td>${score.studentNumber}</td>
							<td>${score.studentName}</td>
							<td>${score.chinese}</td>
							<td>${score.math}</td>
							<td>${score.english}</td>
							<td>${score.physics}</td>
							<td>${score.chemistry}</td>
							<td>${score.biologic}</td>
							<td>${score.politics}</td>
							<td>${score.history}</td>
							<td>${score.geography}</td>
							<td>${score.total}</td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
	</div>
	<common:page-footer/>
</body>
</html>