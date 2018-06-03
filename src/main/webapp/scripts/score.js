/**
 * 本文件为score.jsp使用的初始化脚本。
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
			{ "sWidth": "80px", "targets": 0 },//学号
			{ "sWidth": "60px" , "targets": 1 },//姓名
			{ "sWidth": "130px", "targets": 2 },//学期
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
	var isShowRankColumn = getIsShowRankColumn(array);
	initDynamicColumns(array[0], isShowRankColumn);
	
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
			if (isShowRankColumn) {
				data.push(score.isShowRank ? score.chineseRank : '-');
			}
		}
		if (score.math) {
			data.push(score.math);
			if (isShowRankColumn) {
				data.push(score.isShowRank ? score.mathRank : '-');
			}
		}
		if (score.english) {
			data.push(score.english);
			if (isShowRankColumn) {
				data.push(score.isShowRank ? score.englishRank : '-');
			}
		}
		if (score.physics) {
			data.push(score.physics);
			if (isShowRankColumn) {
				data.push(score.isShowRank ? score.physicsRank : '-');
			}
		}
		if (score.chemistry) {
			data.push(score.chemistry);
			if (isShowRankColumn) {
				data.push(score.isShowRank ? score.chemistryRank : '-');
			}
		}
		if (score.biologic) {
			data.push(score.biologic);
			if (isShowRankColumn) {
				data.push(score.isShowRank ? score.biologicRank : '-');
			}
		}
		if (score.politics) {
			data.push(score.politics);
			if (isShowRankColumn) {
				data.push(score.isShowRank ? score.politicsRank : '-');
			}
		}
		if (score.history) {
			data.push(score.history);
			if (isShowRankColumn) {
				data.push(score.isShowRank ? score.historyRank : '-');
			}
		}
		if (score.geography) {
			data.push(score.geography);
			if (isShowRankColumn) {
				data.push(score.isShowRank ? score.geographyRank : '-');
			}
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
function initDynamicColumns(score, isShowRankColumn) {
	if (score.chinese) {
		addField('chinese','语文');
		if(isShowRankColumn) {
			addField('chineseRank','');
		}
	}
	if (score.math) {
		addField('math','数学');
		if(isShowRankColumn) {
			addField('mathRank','');
		}
	}
	if (score.english) {
		addField('english','英语');
		if(isShowRankColumn) {
			addField('englishRank','');
		}
	}
	if (score.physics) {
		addField('physics','物理');
		if(isShowRankColumn) {
			addField('physicsRank','');
		}
	}
	if (score.chemistry) {
		addField('chemistry','化学');
		if(isShowRankColumn) {
			addField('chemistryRank','');
		}
	}
	if (score.biologic) {
		addField('biologic','生物');
		if(isShowRankColumn) {
			addField('biologicRank','');
		}
	}
	if (score.politics) {
		addField('politics','政治');
		if(isShowRankColumn) {
			addField('politicsRank','');
		}
	}
	if (score.history) {
		addField('history','历史');
		if(isShowRankColumn) {
			addField('historyRank','');
		}
	}
	if (score.geography) {
		addField('geography','地理');
		if(isShowRankColumn) {
			addField('geographyRank','');
		}
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

//决定时否显示“单科排名”。当该生所有考试中有任何一个允许显示排名，就显示排名
function getIsShowRankColumn(array) {
	//默认不显示
	var isShowRank = false;
	//如果结果中有任何一个考试需要显示排名，则排名就要显示
	for(var score in array) {
		if (array[score].isShowRank) {
			isShowRank = true;
			break;
		}
	}
	return isShowRank;
}