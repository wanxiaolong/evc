<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/common"%>
<%@ taglib prefix="admin" tagdir="/WEB-INF/tags/admin"%>
<%@ page isELIgnored="false" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<!DOCTYPE html>
<html>
<head>
	<title>中国英语村-成绩上传</title>
	<common:import/>
	<admin:import/>
	
	<!-- For fileinput -->
	<link rel="stylesheet" href="<%=basePath%>/vendor/bootstrap-fileinput-4.4.7/css/fileinput.min.css">
	<script src="<%=basePath%>/vendor/bootstrap-fileinput-4.4.7/js/fileinput.min.js"></script>
	<!-- FileInput插件的本地化设置，使用时需提前引入\locales文件夹下对应的语言文件，中文zh，引入语言文件必须放在fileinput.js之后 -->
	<script src="<%=basePath%>/vendor/bootstrap-fileinput-4.4.7/js/locales/zh.js"></script>
	
	<!-- For select2 -->
	<link href="<%=basePath%>/vendor/select2-4.0.5/css/select2.min.css" rel="stylesheet"/>
	<script src="<%=basePath%>/vendor/select2-4.0.5/js/select2.min.js"></script>
	<!-- Select2 language resource file -->
	<script src="<%=basePath%>/vendor/select2-4.0.5/js/i18n/zh-CN.js"></script>
</head>
<body>
	<common:page-header/>
	<div class="admin evc-content">
		<admin:menu/>
		<div class="content tab-content">
			<!-- 标签页 -->
			<ul id="myTab" class="nav nav-tabs">
				<li class="active">
					<a href="#uploadByExam" data-toggle="tab">按考试上传</a>
				</li>
				<li>
					<a href="#uploadBatch" data-toggle="tab">批量上传</a>
				</li>
			</ul>
			
			<!-- 按考试上传成绩 -->
			<div id="uploadByExam" class="form-inline filter-block tab-pane fade in active">
				<div class="description">
					<p class="strong">注意：</p>
					<p>1. 仅支持.xls或.xlsx格式的Excel文件</p>
					<p>2. 科目信息和顺序必须和显示的考试信息一致</p>
					<p>3. 文件中的行数应等于考试人数，否则报错</p>
					
					<p class="strong">举例说明：</p>
					<p>上传的是2016年下学期的第一学月考试，则</p>
					<p>先选择学期为“2016~2017下学期”，再选择考试为“第一学月考试”，</p>
					<p>确保系统显示的科目及顺序和Excel中的列及顺序一致，最后点击“上传”</p>
				</div>
				<div class="title">请先选择要为哪次考试上传成绩：</div>
				<div class="filter-row">
					<div class="filter">
						<label>学期：</label>
						<select class="form-control" id="semesterSelect" name="semester" required>
							<option value="none">--请选择--</option>
						</select>
					</div>
					<div class="filter">
						<label>考试：</label>
						<select class="form-control" id="examSelect" required>
							<option value="none">--请选择--</option>
						</select>
					</div>
					<%-- <div class="filter">
						没有找到考试？&nbsp;&nbsp;
						<button class="btn btn-primary" onclick="javascript:window.location.href='<%=basePath%>/admin/exam.jsp'">
							<i class="fa fa-plus-circle"></i>考试管理
						</button>
					</div> --%>
				</div>
				<div class="subject-msg-div">
					请确保Excel第一列<b>学生信息</b>和<b class="red">科目信息</b>按照如下顺序（仅支持.xls和.xlsx格式的文件）：
					<div>
						<span id="studentmsg">学号，姓名，生日，年级，班级，</span>
						<span id="subjectmsg"></span>
					</div>
				</div>
				<div class="upload-div">
					<input id="upload-file" name="input-b1" type="file">
				</div>
			</div>
			
			<!-- 批量上传成绩 -->
			<div id="uploadBatch" class="form-inline filter-block tab-pane fade">
				<div class="description">
					<p>支持上传文件夹，同一个学期的考试放在一个以学期命名的文件夹中。系统将根据文件夹名字创建学期，将根据文件名字创建考试信息，并自动上传成绩。</p>
					<p class="strong">注意：</p>
					<p>1. 仅支持.xls或.xlsx格式的Excel文件并且只支持压缩成.zip格式</p>
					<p>2. 文件夹名字必须按照“2016~2017上学期”规范</p>
					<p>3. 文件名字即为考试名字，比如：第一学月考试.xls，考试名称则为：第一学月考试</p>
					<p>4. 文件中的列即为考试的科目信息</p>
					<p>5. 文件中的行数即为考试总人数</p>
					
					<p class="strong">举例说明：</p>
					<p>上传的是2016年下学期的第一学月、第二学月、期末考试，和2017年上学期的半期考试、期末考试，则</p>
					<p>在“2016~2017下学期”文件夹中包含第一学月.xls，第二学月.xls，期末考试.xls，</p>
					<p>在“2017~2018上学期”文件夹中包含半期考试.xls，期末考试.xls，然后把这两个文件夹压缩成zip文件并选择它们即可</p>
				</div>
				<div class="upload-batch-div">
					<input id="upload-batch" name="input-b1" type="file">
				</div>
			</div>
		</div>
	</div>
	<common:page-footer/>
<!-- FileInput插件的初始化脚本 -->
<script type="text/javascript" src="<%=basePath%>/scripts/admin/score_upload.js"></script>
</body>
</html>