<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.net.URLEncoder" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
<title>中国英语村-资料下载</title>
<common:import />
<script type="text/javascript" src="http://cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js"></script>
<link type="text/css" rel="stylesheet" href="http://cdn.datatables.net/1.10.15/css/jquery.dataTables.min.css"></link>
</head>
<body>
	<common:page-header />
	<div class="evc-content">
		<div class="evc-file">
			<table id="file_table" class="table table-striped">
				<thead>
					<tr class="row">
						<th class="col-md-8">文件名</th>
						<th class="col-md-1">下载量</th>
						<th class="col-md-3">创建日期</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${model}" var="file">
						<tr class="row">
							<td class="col-md-8">
								<a href="/evc${file.path}/${file.name}">${file.name}</a>
							</td>
							<td class="col-md-1">${file.downloadCount}</td>
							<td class="col-md-3">${file.creationDate}</td>
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
	$('#file_table').DataTable({
		language: {
			url: '/evc/localization/Chinese.json'
		}
	});
});
</script>
</body>
</html>