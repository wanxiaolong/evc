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
			查询条件
			<div class="form-group">
				<label for="name">选择列表</label> <select class="form-control">
					<option>1</option>
					<option>2</option>
					<option>3</option>
				</select>
			</div>
			<div class="form-group">
				<label class="sr-only" for="name">名称</label> <input type="text"
					class="form-control" id="name" placeholder="请输入名称">
			</div>
			<div class="checkbox">
				<label> <input type="checkbox">请打勾
				</label>
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