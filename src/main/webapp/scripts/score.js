/**
 * 本文件为score.jsp使用的初始化脚本。
 * 脚本中需要用到common.js的getWebRoot()函数，所以需要同时导入common.js文件。
 */
var webroot = getWebRoot();
$(document).ready(function(){
	table = initDataTable("scoreTable");

	//监听查询按钮点击事件
	$('#queryBtn').click(function(){
		executeScoreQuery();
	});
	
	//监听复选框状态变化事件
	$("#queryAll").change(function() {
		initSemesterSelect();
	});
	
	queryAllStudents();
});

//根据拼音首字母查询学生
function queryAllStudents() {
	$.ajax({
		type: 'GET',
		url: webroot + '/rest/student/all',
		success: function (data) {
			var array = data.response;
			if (data.errorMessage != null) {
				alert(data.errorMessage);
				return;
			}
			addNameOption(array);
		},
		error: function () {
			alert("调用学生信息查询接口失败！");
			console.log("调用查询接口失败！");
		}
	});
}

//将查询到的学生动态增加到下拉菜单中
function addNameOption(array) {
	//先清空原来的选择项
	$("#name").empty();
	$("#name").append("<option>--请选择--</option>");
	//再依次添加
	for(var index in array) {
		var student = array[index];
		var option = "<option value='" + student.namePinyin+"'>" + student.name + "</option>";
		$("#name").append(option);
	}
}

function executeScoreQuery() {
	//先执行必要的字段检查
	if(!checkRequiredField()) {
		return;
	}
	var isQueryAll = $("#queryAll").is(':checked');
	var semesterId = $("#semesterSelect").children('option:selected').val();
	var name = $("#name").val();
	var birthday = $("#birthday").val();
	
	$.ajax({
		type: 'POST',
		url: webroot + '/rest/score/query',
		data: 	'query_all=' + isQueryAll +
				'&semester_id=' + semesterId + 
				'&name=' + name + 
				'&birthday=' + birthday,
		success: function (data) {
			var array = data.response;
			if (data.errorMessage != null) {
				alert(data.errorMessage);
				return;
			}
			addRows(array);
		},
		error: function () {
			alert("调用成绩查询接口失败！");
			console.log("调用查询接口失败！");
		}
	});
}
function initSemesterSelect() {
	//如果查询所有历史成绩，则学期的下拉菜单不可用
	var isQueryAll = $("#queryAll").is(':checked');
	var semester = $("#semesterSelect");
	if(isQueryAll) {
		semester.attr("disabled","disabled");
		semester.removeAttr("required");
		semester.removeClass("err-bdr");
	} else {
		semester.removeAttr("disabled");
		semester.attr("required","required");
	}
}
function initDataTable(id) {
	return $('#' + id).DataTable({
		language: {
			url: webroot + '/localization/chinese.json'
		},
		bPaginate: true,//是否显示分页器（左上角显示 ‘每页显示x条记录’）
		bFilter: true, //是否显示搜索框（右上角）
		bSort: true, //是否允许列排序
		aaSorting: [[0, "asc"]] //默认的排序方式，第1列，升序排列
	});
}
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
		table.row.add([
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
		]).draw(false);
	}
}