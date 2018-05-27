/**
 * 本文件为score.jsp使用的初始化脚本。
 * 脚本中需要用到common.js的getWebRoot()函数，所以需要同时导入common.js文件。
 */
var webroot = getWebRoot();
var table;
$(document).ready(function(){
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
	ajax('GET', '/exam/findLast', null, null, findLastExamSuccessCallback, findLastExamErrorCallback);
}

//查询所有学生信息（用于初始化下拉列表）
function queryAllStudents() {
	ajax('GET', '/student/all', null, null, addNameOption);
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
	
	var data =	'query_all=' + isQueryAll +
				'&semester_id=' + semesterId + 
				'&name_pinyin=' + namePinyin + 
				'&birth_day=' + birthday +
				(examId != null ? '&exam_id=' + examId : '');
	
	ajax('POST', '/score/query', data, null, addRows);
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
	if (array.length == 0) {
		if (table) {
			table.rows("tr").remove().draw();
		}
		toastr.info("没有查到成绩！");
		return;
	}
	
	//如果表格已被初始化（页面上非第一次查询），先删除现有的行，再重新初始化表格后重新添加行（因为列有可能已经改变）。
	if (table) {
		//注意:
		//1. 这里使用的是rows(selector)方法而不是row(selector)方法，这里用于选择多行
		//2. 从表格中移除之后，需要重新调用draw()方法
		table.rows("tr").remove().draw();
		table.destroy();
		$("#scoreTable thead tr th[dynamic-field]").remove();
	}
	
	//先处理表头，根据第一行数据的初始化表头
	initDynamicColumns(array[0]);
	table = initDataTable("scoreTable");
	$("#scoreTable").removeClass('hide');
	
	//准备数据
	for(var index in array) {
		var score = array[index];
		var data = [
			score.studentNumber,
			score.studentName,
			score.semesterName,
			score.examName
		];
		if (score.chinese) {
			data.push(score.chinese);
			data.push(score.isShowRank ? score.chineseRank : '-');
		}
		if (score.math) {
			data.push(score.math);
			data.push(score.isShowRank ? score.mathRank : '-');
		}
		if (score.english) {
			data.push(score.english);
			data.push(score.isShowRank ? score.englishRank : '-');
		}
		if (score.physics) {
			data.push(score.physics);
			data.push(score.isShowRank ? score.physicsRank : '-');
		}
		if (score.chemistry) {
			data.push(score.chemistry);
			data.push(score.isShowRank ? score.chemistryRank : '-');
		}
		if (score.biologic) {
			data.push(score.biologic);
			data.push(score.isShowRank ? score.biologicRank : '-');
		}
		if (score.politics) {
			data.push(score.politics);
			data.push(score.isShowRank ? score.politicsRank : '-');
		}
		if (score.history) {
			data.push(score.history);
			data.push(score.isShowRank ? score.historyRank : '-');
		}
		if (score.geography) {
			data.push(score.geography);
			data.push(score.isShowRank ? score.geographyRank : '-');
		}
		if (score.total) {
			data.push(score.total);
		}
		
		//移除掉undefined或者是空字符串
//		data = data.filter(function(val){
//			return !(typeof(val) == 'undefined' || val === "");
//		});
		
		//根据考试的配置，决定是否显示班级排名和年级排名
		if (score.isShowClassRank) {
			//添加班名信息
			data.push(score.totalRank);
		}
		if (score.isShowGradeRank) {
			//TODO 添加级名信息
			data.push(10);
		}
		
		table.row.add(data).draw(false);
	}
}

//根据成绩数据的值隐藏没有值的列
function initDynamicColumns(score) {
	if (score.chinese) {
		addField('chinese','语文');
		addField('chineseRank','');
	}
	if (score.math) {
		addField('math','数学');
		addField('mathRank','');
	}
	if (score.english) {
		addField('english','英语');
		addField('englishRank','');
	}
	if (score.physics) {
		addField('physics','物理');
		addField('physicsRank','');
	}
	if (score.chemistry) {
		addField('chemistry','化学');
		addField('chemistryRank','');
	}
	if (score.biologic) {
		addField('biologic','生物');
		addField('chineseRank','');
	}
	if (score.politics) {
		addField('politics','政治');
		addField('politicsRank','');
	}
	if (score.history) {
		addField('history','历史');
		addField('historyRank','');
	}
	if (score.geography) {
		addField('geography','地理');
		addField('geographyRank','');
	}
	if (score.total) {
		addField('total','总分');
	}
	
	//是否显示班级排名和年级排名
	if (score.isShowClassRank) {
		addField('clazzrank','班名');
	}
	if (score.isShowGradeRank) {
		addField('graderank','级名');
	}
}

function addField(field, name) {
	var th = '<th dynamic-field="' + field + '">' + name + '</th>';
	$("#scoreTable thead tr").append(th);
}

//查找最后一次考试信息，成功时的回调
function findLastExamSuccessCallback(data) {
	$("#examSemester").html(data.semesterName);
	$("#examName").html(data.name);
	$("#examPeople").html(data.people);
	$(".score.evc-content .message").show();
}
//查找最后一次考试信息，失败时的回调
function findLastExamErrorCallback() {
	$(".score.evc-content .message").hide();
}