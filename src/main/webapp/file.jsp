<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
<title>中国英语村-资料下载</title>
<common:import />
</head>
<body>
	<common:page-header />
	<div class="evc-content">
		<div class="evc-resource">
			<table class="table table-striped">
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
							<td class="col-md-8">${file.name}</td>
							<td class="col-md-1">${file.downloadCount}</td>
							<td class="col-md-3">${file.creationDate}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<common:page-footer />
</body>
</html>