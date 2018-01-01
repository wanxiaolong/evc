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
				<select class="form-control" id="semesterSelect" name="semester">
					<option>--请选择--</option>
				</select>
			</div>
			<div class="form-group">
				<label>考试：</label>
				<select class="form-control" id="examSelect" name="exam_id">
					<option>--请选择--</option>
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
					<tr><td colspan="12">没有记录！</td></tr>
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
<script type="text/javascript">
$(document).ready(function(){
	$.ajax({
		type: 'GET',
		url: '<%=basePath%>/rest/semester/findAll',
		success: function (data) {
			var semesterArray = data.response;
			if (semesterArray.length > 0) {
				for(var index in semesterArray) {
					var semester = semesterArray[index];
					//动态创建并添加select的option
					var option = new Option(semester.name, semester.number);
					$("#semesterSelect").append(option);
				}
			}
			
		},
		error: function () {
			console.log("调用查询考试信息接口失败！");
		}
	});
	
	
	$('#semesterSelect').change(function(){
		var selectedSemester = $(this).children('option:selected').val();//这就是selected的值
		$.ajax({
			type: 'GET',
			url: '<%=basePath%>/rest/exam/findBySemester?semester=' + selectedSemester,
			success: function (data) {
				//移除examSelect所有带有value的option
				$("#examSelect option[value]").remove();
				
				var examArray = data.response;
				if (examArray.length > 0) {
					for(var index in examArray) {
						var exam = examArray[index];
						//动态创建并添加select的option
						var option = new Option(exam.name, exam.id);
						$("#examSelect").append(option);
					}
				}
				
			},
			error: function () {
				console.log("调用查询考试信息接口失败！");
			}
		});
	});
});
</script>
</html>