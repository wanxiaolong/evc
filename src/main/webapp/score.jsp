<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common"%>

<!DOCTYPE html>
<html>
<head>
	<title>中国英语村-主页</title>
	<common:import/>
</head>
<body>
	<common:page-header/>
	<div class="evc-content">
		<!-- 查询条件 -->
		<form class="form-inline" role="form">
			<div>查询条件</div>
			<div class="form-group">
				<label for="name">学期：</label>
				<select class="form-control">
					<option>2017年第二学期</option>
					<option>2017年第一学期</option>
					<option>2016年第二学期</option>
					<option>2016年第一学期</option>
				</select>
			</div>
			<div class="form-group">
				<label for="name">科目：</label>
				<select class="form-control">
					<option>语文</option>
					<option>数学</option>
					<option>英语</option>
				</select>
			</div>
			<div class="form-group">
				<label class="sr-only" for="name">姓名：</label>
				<input type="text" class="form-control" id="name" placeholder="请输入学生姓名">
			</div>
			<div class="checkbox">
				<label> <input type="checkbox">请打勾</label>
			</div>
			<button type="submit" class="btn btn-default">提交</button>
		</form>

		<!-- 表格布局 -->
		<table class="table table-striped table-bordered">
			<caption>查询结果</caption>
			<thead>
				<tr>
					<th>名称</th>
					<th>城市</th>
					<th>邮编</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>Tanmay</td>
					<td>Bangalore</td>
					<td>560001</td>
				</tr>
				<tr>
					<td>Sachin</td>
					<td>Mumbai</td>
					<td>400003</td>
				</tr>
				<tr>
					<td>Uma</td>
					<td>Pune</td>
					<td>411027</td>
				</tr>
			</tbody>
		</table>
	</div>
	<common:page-footer/>
</body>
</html>