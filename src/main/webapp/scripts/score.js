/**
 * 本文件为score.jsp使用的初始化脚本。
 */
var webroot = getWebRoot();
var table;
$(document).ready(function(){
	//移除select2下拉菜单的title属性，没用
	$("#select2-semesterSelect-container").removeAttr("title");
	
	table = $("#scoreTable");
	
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

//动态的向表格中增加数据
function addRows(array) {
	table.children("tr").remove();
	if (array.length == 0) {
		toastr.info("没有查到成绩！");
		return;
	}
	
	table.removeClass("hide");
	
	//只要所有的考试中有一个需要显示单科排名，那么就显示名次这列。如果某个考试设置不显示单科排名，则该值为“-”。
	var isShowRankColumn = getIsShowRankColumn(array);
	
	//准备数据：每个考试都有两行数据：一行是标题，一行是值。标题和值根据考试成绩的科目动态确定。
	for(var index in array) {
		var score = array[index];
		
		//固定不变的列
		var title = ["学号","姓名","学期","考试"];
		var data = [
			score.studentNumber,
			score.studentName,
			score.semesterName,
			score.examName
		];
		
		//动态变化的列（因为每次考试的科目不同）
		if (score.chinese) {
			title.push("语文");
			data.push(score.chinese);
			if (isShowRankColumn) {
				title.push(" ");
				data.push(score.isShowRank ? score.chineseRank : '-');
			}
		}
		if (score.math) {
			title.push("数学");
			data.push(score.math);
			if (isShowRankColumn) {
				title.push(" ");
				data.push(score.isShowRank ? score.mathRank : '-');
			}
		}
		if (score.english) {
			title.push("英语");
			data.push(score.english);
			if (isShowRankColumn) {
				title.push(" ");
				data.push(score.isShowRank ? score.englishRank : '-');
			}
		}
		if (score.physics) {
			title.push("物理");
			data.push(score.physics);
			if (isShowRankColumn) {
				title.push(" ");
				data.push(score.isShowRank ? score.physicsRank : '-');
			}
		}
		if (score.chemistry) {
			title.push("化学");
			data.push(score.chemistry);
			if (isShowRankColumn) {
				title.push(" ");
				data.push(score.isShowRank ? score.chemistryRank : '-');
			}
		}
		if (score.biologic) {
			title.push("生物");
			data.push(score.biologic);
			if (isShowRankColumn) {
				title.push(" ");
				data.push(score.isShowRank ? score.biologicRank : '-');
			}
		}
		if (score.politics) {
			title.push("政治");
			data.push(score.politics);
			if (isShowRankColumn) {
				title.push(" ");
				data.push(score.isShowRank ? score.politicsRank : '-');
			}
		}
		if (score.history) {
			title.push("历史");
			data.push(score.history);
			if (isShowRankColumn) {
				title.push(" ");
				data.push(score.isShowRank ? score.historyRank : '-');
			}
		}
		if (score.geography) {
			title.push("地理");
			data.push(score.geography);
			if (isShowRankColumn) {
				title.push(" ");
				data.push(score.isShowRank ? score.geographyRank : '-');
			}
		}
		if (score.experiment) {
			title.push("实验");
			data.push(score.experiment);
			if (isShowRankColumn) {
				title.push(" ");
				data.push(score.isShowRank ? score.experimentRank : '-');
			}
		}
		if (score.physical) {
			title.push("体育");
			data.push(score.physical);
			if (isShowRankColumn) {
				title.push(" ");
				data.push(score.isShowRank ? score.physicalRank : '-');
			}
		}
		if (score.score1) {
			title.push("成绩1");
			data.push(score.score1);
			if (isShowRankColumn) {
				title.push(" ");
				data.push(score.isShowRank ? score.score1Rank : '-');
			}
		}
		if (score.score2) {
			title.push("成绩2");
			data.push(score.score2);
			if (isShowRankColumn) {
				title.push(" ");
				data.push(score.isShowRank ? score.score2Rank : '-');
			}
		}
		
		if (score.total) {
			title.push("总分");
			data.push(score.total);
		}
		
		//根据考试的配置，决定是否显示班级排名和年级排名
		if (score.isShowClassRank) {
			//添加班名信息
			title.push("班名");
			data.push(score.totalRank);
		}
		if (score.isShowGradeRank) {
			//TODO 添加级名信息
			title.push("级名");
			data.push(10);
		}
		
		//将数组转换成HTML row并添加到表格中
		addTableRow(title);
		addTableRow(data);
	}
}

//将数组转换成HTML row并添加到表格中
function addTableRow(data) {
	var tr = $('<tr></tr>');
	for (var i in data) {
		var td = $('<td>' + data[i] + '</td>');
		tr.append(td);
	}
	table.append(tr);
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