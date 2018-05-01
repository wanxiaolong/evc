/**
 * 本文件为score.jsp使用的初始化脚本。
 * 脚本中需要用到common.js的getWebRoot()函数，所以需要同时导入common.js文件。
 */
var webroot = getWebRoot();
$(document).ready(function(){
	table = initDataTable("scoreTable");
	
	//移除select2下拉菜单的title属性，没用
	$("#select2-semesterSelect-container").removeAttr("title");
	
	//监听查询按钮点击事件
	$('#queryBtn').click(function(){
		executeScoreQuery();
	});
	
	//监听复选框状态变化事件
	$("#queryAll").change(function() {
		initSemesterSelect();
	});
	
	queryAllSemesters();
	queryAllStudents();
	queryLastExam();
});

//查询最近一次考试信息
function queryLastExam() {
	$.ajax({
		type: 'GET',
		url: webroot + '/exam/findLast',
		success: function (data) {
			if (data.status == 0) {
				//success
				var data = data.response;
				$("#examSemester").html(data.semesterName);
				$("#examName").html(data.name);
				$("#examPeople").html(data.people);
				$(".score.evc-content .message").show();
			} else {
				//failure
				$(".score.evc-content .message").hide();
			}
		},
		error: function () {
			$(".score.evc-content .message").hide();
			toastr.error("查询最近一次考试失败！");
		}
	});
}

//查询所有学生信息（用于初始化下拉列表）
function queryAllStudents() {
	$.ajax({
		type: 'GET',
		url: webroot + '/student/all',
		success: function (data) {
			var array = data.response;
			addNameOption(array);
		},
		error: function () {
			toastr.error("查询学生列表失败！");
		}
	});
}

//将查询到的学生动态增加到下拉菜单中
function addNameOption(array) {
	//先清空原来的选择项
	$("#nameSelect").empty();
	$("#nameSelect").append("<option>--请选择--</option>");
	//再依次添加
	for(var index in array) {
		var student = array[index];
		var option = "<option value='" + student.namePinyin+"'>" + student.name + "("+student.namePinyin+")" + "</option>";
		$("#nameSelect").append(option);
	}
	
	//用中文渲染select2
	$("#nameSelect").select2({
		language: "zh-CN"
	});
}

//发送请求到后台执行成绩查询
function executeScoreQuery() {
	//先执行必要的字段检查
	if(!checkRequiredField()) {
		return;
	}
	var isQueryAll = $("#queryAll").is(':checked');
	var semesterId = $("#semesterSelect").children('option:selected').val();
	var examId = $("#examSelect").children('option:selected').val();
	var namePinyin = $("#nameSelect").children('option:selected').val();
	var birthday = $("#birthday").val();
	
	$.ajax({
		type: 'POST',
		url: webroot + '/score/query',
		data: 	'query_all=' + isQueryAll +
				'&semester_id=' + semesterId + 
				'&name_pinyin=' + namePinyin + 
				'&birth_day=' + birthday +
				(examId != null ? '&exam_id=' + examId : ''),
		success: function (data) {
			var array = data.response;
			addRows(array);
		},
		error: function () {
			toastr.error("查询成绩失败！");
		}
	});
}

//根据是否选中“查询所有历史成绩”初始化各个控件的状态
function initSemesterSelect() {
	//如果查询所有历史成绩，则学期的下拉菜单不可用
	var isQueryAll = $("#queryAll").is(':checked');
	var semesterSelect = $("#semesterSelect");
	var semesterSelect2Container = $("#select2-semesterSelect-container");
	semesterSelect2Container.removeAttr("title");
	if(isQueryAll) {
		semesterSelect.attr("disabled","disabled");
		semesterSelect2Container.addClass("select2-disabled");
		semesterSelect.removeAttr("required");
		semesterSelect2Container.removeClass("err-bdr");
	} else {
		semesterSelect.removeAttr("disabled");
		semesterSelect2Container.removeClass("select2-disabled");
		semesterSelect.attr("required","required");
	}
}

//初始化Datatables表格
function initDataTable(id) {
	return $('#' + id).DataTable({
		language: {
			url: webroot + '/localization/chinese.json'
		},
		columnDefs: [
			//--------------------------------  //前几列的名字固定，宽度固定
			{ "sWidth": "100px", "targets": 0 },//学号
			{ "sWidth": "70px" , "targets": 1 },//姓名
			{ "sWidth": "150px", "targets": 2 },//学期
			{ "sWidth": "150px", "targets": 3 },//考试
			//----------------------------------//后续列为成绩列，宽度自动确定
			{"targets": "_all", "defaultContent": ""}, 
		],
		bAutoWidth: false,
		bPaginate: true,//是否显示分页器（左上角显示 ‘每页显示x条记录’）
		bFilter: true, //是否显示搜索框（右上角）
		bSort: true, //是否允许列排序
		aaSorting: [[0, "asc"]] //默认的排序方式，第1列，升序排列
	});
}

//动态的向表格中增加数据
function addRows(array) {
	//先删除现有的行，再动态创建并添加行。
	//注意:
	//1. 这里使用的是rows(selector)方法而不是row(selector)方法，这里用于选择多行
	//2. 从表格中移除之后，需要重新调用draw()方法
	table.rows("tr").remove().draw();
	if (array.length == 0) {
		return;
	}
	
	for(var index in array) {
		var score = array[index];
		var data = [
			score.studentNumber,
			score.studentName,
			score.semesterName,
			score.examName,
			score.chinese,
			score.math,
			score.english,
			score.physics,
			score.chemistry,
			score.biologic,
			score.politics,
			score.history,
			score.geography,
			score.total
		];
		//根据考试的配置，决定是否显示班级排名和年级排名
		if (score.isShowClassRank) {
			data.push(1);
		} else {
			data.push('-');
		}
		if (score.isShowGradeRank) {
			data.push(10);
		} else {
			data.push('-');
		}
		
		table.row.add(data).draw(false);
	}
}