<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
<title>中国英语村-留言栏</title>
<common:import />
<script type="text/javascript" src="http://cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js"></script>
<link type="text/css" rel="stylesheet" href="http://cdn.datatables.net/1.10.15/css/jquery.dataTables.min.css"></link>
</head>
<body>
	<common:page-header />
	<div class="evc-content">
		<div class="evc-message">
			<div class="right">
				<a href="/evc/message_detail.jsp">我要留言>>></a>
			</div>
			<table id="_table" class="table table-striped column1-left">
				<thead>
					<tr class="row">
						<th class="col-md-7">标题</th>
						<th class="col-md-2">类型</th>
						<th class="col-md-1">发布者</th>
						<th class="col-md-2">创建日期</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${model}" var="message">
						<tr class="row">
							<td class="col-md-8">
								<a href="${message.id}">${message.title}</a>
							</td>
							<td class="col-md-1">${message.type}</td>
							<td class="col-md-1">${message.contact}</td>
							<td class="col-md-2">${message.creationDate}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<common:page-footer />
<script type="text/javascript">
$(document).ready(function(){
	//Use DataTable plugin to provide sort/search/pagination feature for table.
	//By default, this plugin uses English, provide this URL to do localization for this plugin.
	$('#_table').DataTable({
		language: {
			url: '/evc/localization/chinese.json'
		}
	});
});
</script>
</body>
</html>