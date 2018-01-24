<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
<title>中国英语村-公告栏</title>
<common:import />
<script type="text/javascript" src="http://cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js"></script>
<link type="text/css" rel="stylesheet" href="http://cdn.datatables.net/1.10.15/css/jquery.dataTables.min.css"></link>
</head>
<body>
	<common:page-header />
	<div class="evc-content">
		<div class="evc-notice">
			<table id="_table" class="table table-striped">
				<thead>
					<tr class="row">
						<th class="col-md-7">标题</th>
						<th class="col-md-1">重要性</th>
						<th class="col-md-2">发布者</th>
						<th class="col-md-2">创建日期</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${model}" var="notice">
						<tr class="row">
							<td class="col-md-7">
								<a href="${notice.id}">${notice.title}</a>
							</td>
							<td class="col-md-1">${notice.importantLevel}</td>
							<td class="col-md-2">${notice.userName}</td>
							<td class="col-md-2">${notice.creationDate}</td>
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