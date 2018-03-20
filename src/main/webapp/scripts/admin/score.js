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
	
	//如果学期下拉菜单变化，则查询该学期下的所有考试信息。成绩查询将根据这个选中的考试信息进行。
	$('#semesterSelect').change(function(){
		initExamSelect();
	});
	
	queryAllSemesters();
});

//改变学期下拉菜单的时候，重新获取该学期的考试信息，并初始化考试下拉菜单
function initExamSelect() {
	var selectedSemester = $("#semesterSelect").children('option:selected').val();//获取selected的值
	$.ajax({
		type: 'GET',
		url: webroot + '/exam/findBySemester?semester_id=' + selectedSemester,
		success: function (data) {
			var array = data.response;
			if (data.errorMessage != null) {
				alert(data.errorMessage);
				return;
			}
			addExamOption(array)
		},
		error: function () {
			console.log("调用查询考试信息接口失败！");
		}
	});
}

//将查询到的学期信息动态增加到下拉菜单中
function addExamOption(array) {
	//先清空原来的选择项
	$("#examSelect").empty();
	$("#examSelect").append("<option>--请选择--</option>");
	//再依次添加
	for(var index in array) {
		var exam = array[index];
		var option = "<option value='" + exam.id+"'>" + exam.name + "</option>";
		$("#examSelect").append(option);
	}
	$("#examSelect").select2();
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
				'&birthday=' + birthday +
				(examId != null ? '&exam_id=' + examId : ''),
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
			//----------------------------------//后续列为成绩列，宽度自动确定
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