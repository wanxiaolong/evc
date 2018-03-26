<%@ tag language="java" pageEncoding="utf-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- For fontawsome -->
<link rel="stylesheet" href="<%=basePath%>/vendor/font-awesome-4.7.0/css/font-awesome.min.css">

<!-- For jquery -->
<script src="<%=basePath%>/vendor/jquery-3.2.1/jquery-3.2.1.min.js"></script>

<!-- For bootstrap -->
<link href="<%=basePath%>/vendor/bootstrap-3.3.7/css/bootstrap.min.css" rel="stylesheet">
<script src="<%=basePath%>/vendor/bootstrap-3.3.7/js/bootstrap.min.js"></script>

<!-- For toastr(a message notifier plugin) -->
<link href="<%=basePath%>/vendor/toastr-2.1.3/css/toastr.min.css" rel="stylesheet"/>
<script src="<%=basePath%>/vendor/toastr-2.1.3/js/toastr.min.js"></script>

<!-- Customized common js -->
<link href="<%=basePath%>/styles/evc.css" rel="stylesheet">
<script src="<%=basePath%>/scripts/common.js"></script>