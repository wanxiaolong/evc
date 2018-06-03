/**
 * 本文件为admin/score.jsp使用的初始化脚本。
 */
var webroot = getWebRoot();
var table;
//缓存最后一次查询的结果，用于在“显示单科排名”复选框重新初始化数据，减少网络请求。
var lastScoreQuery;
$(document).ready(function(){
	//移除select2下拉菜单的title属性，没用
	$("#select2-semesterSelect-container").removeAttr("title");
	
	//监听查询按钮点击事件
	$('#queryBtn').click(function(){
		executeQuery();
	});
	
	//初始化学期下拉菜单
	queryAllSemesters();
	//如果学期下拉菜单变化，则查询该学期下的所有考试信息。成绩查询将根据这个选中的考试信息进行。
	$('#semesterSelect').change(function(){
		initExamSelect();
	});
	//用select2初始化考试下拉菜单
	$("#examSelect").select2({
		language: "zh-CN"
	});
	
	//表格操作列上的“编辑”按钮的点击事件
	$('#scoreTable tbody').on('click', 'button#editrow', function() {
		//先将行的数据转换成数组
		var data = table.row($(this).parents('tr')).data();
		var score = data[0];
		var fields = $("#edit-form").serializeArray();
		$.each(fields, function(i, field){
			//jquery根据name属性查找input，然后从score中获取对应的属性值，并将值设置给input
			//注意这里只能通过[]的方式来获取属性值
			$(":input[name='"+field.name+"']").val(score[field.name]);
		});
	});
	
	//点击成绩修改模态框的提交按钮，执行表单提交
	$("#confirm-update").click(function(){
		submitUpdate();
	});
	
	//勾选复选框时，将显示或隐藏单科排名
	$("#isShowRankChkbox").change(function(){
		addRows(lastScoreQuery);
	});
});


//提交数据，执行成绩更新。成功或失败都会在页面上显示消息。
function submitUpdate() {
	var id = $("#edit-form input[name='id']").val();
	var chinese = $("#edit-form input[name='chinese']").val();
	var math = $("#edit-form input[name='math']").val();
	var english = $("#edit-form input[name='english']").val();
	var physics = $("#edit-form input[name='physics']").val();
	var chemistry = $("#edit-form input[name='chemistry']").val();
	var biologic = $("#edit-form input[name='biologic']").val();
	var politics = $("#edit-form input[name='politics']").val();
	var history = $("#edit-form input[name='history']").val();
	var geography = $("#edit-form input[name='geography']").val();
	
	var data =	'id=' + id +
				'&chinese=' + chinese + 
				'&math=' + math + 
				'&english=' + english +
				'&physics=' + physics +
				'&chemistry=' + chemistry +
				'&biologic=' + biologic +
				'&politics=' + politics +
				'&history=' + history +
				'&geography=' + geography;
	ajax('POST', '/score/update', data, null, updateSuccessCallback);
}

//发送请求到后台执行成绩查询
function executeQuery() {
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

//初始化Datatables表格
function initDataTable(id) {
	var config = {
		language: {
			url: webroot + '/localization/chinese.json'
		},
		columnDefs: [
			//--------------------------------  //前几列的名字固定，宽度固定
			{"targets": 1, "sWidth": "100px"},  //学号
			{"targets": 2, "sWidth": "70px"},   //姓名
			//----------------------------------//后续列为成绩列，宽度自动确定
			//----------------------------------//操作列
			{
				"targets": -1,//倒数第1列，编辑
				"sortable": false,//不能排序
				"searchable": false,//不能搜索
				"data": null,//data指定要显示的字段。这里设为null，即不显示任何字段
				"defaultContent": 	"<button id='editrow' class='btn btn-primary' type='button' " +
										"data-toggle='modal' data-target='#myModal' data-backdrop='false'>" +
										"<i class='fa fa-edit'></i>编辑" +
									"</button>"
			},
			//为了不让没值的单元格报错，需要为其他没指定target的列设置默认值，否则会弹出警告信息。
			//注意：
			//1.这里的_all代表所有的列，
			//2.由于最前面的优先级最高，所以这个_all要放到最后，这样才不会对之前的定义覆盖掉（否则最后一列的编辑按钮不会显示）
			//详见：https://datatables.net/reference/option/columnDefs
			//Properties which are higher in the columnDefs array will take priority over those below.
			{"targets": "_all", "defaultContent": ""}, 
		],
		
		bAutoWidth: false,
		bPaginate: true,//是否显示分页器（左上角显示 ‘每页显示x条记录’）
		bFilter: true, //是否显示搜索框（右上角）
		bSort: true, //是否允许列排序
		aaSorting: [[0, "asc"]] //默认的排序方式，第1列，升序排列
	};
	return $('#' + id).DataTable(config);
}

//动态的向表格中增加数据
function addRows(array) {
	//更新上次查询结果
	lastScoreQuery = array;
	
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
	var isShowRank = getIsShowRank(array[0]);
	initDynamicColumns(array[0], isShowRank);
	
	//初始化“显示单科排名”复选框的状态
	if (array[0].isShowRank) {
		$("#isShowRankChkbox").removeAttr("disabled");
	} else {
		$("#isShowRankChkbox").attr("disabled","disabled");
	}
	
	table = initDataTable("scoreTable");
	$("#scoreTable").removeClass('hide');
	
	
	//准备数据
	for(var index in array) {
		var score = array[index];
		var data = [
			score,
			score.studentNumber,
			score.studentName
		];
		if (score.chinese) {
			data.push(score.chinese);
			if(isShowRank) {
				data.push(score.chineseRank);
			}
		}
		if (score.math) {
			data.push(score.math);
			if(isShowRank) {
				data.push(score.mathRank);
			}
		}
		if (score.english) {
			data.push(score.english);
			if(isShowRank) {
				data.push(score.englishRank);
			}
		}
		if (score.physics) {
			data.push(score.physics);
			if(isShowRank) {
				data.push(score.physicsRank);
			}
		}
		if (score.chemistry) {
			data.push(score.chemistry);
			if(isShowRank) {
				data.push(score.chemistryRank);
			}
		}
		if (score.biologic) {
			data.push(score.biologic);
			if(isShowRank) {
				data.push(score.biologicRank);
			}
		}
		if (score.politics) {
			data.push(score.politics);
			if(isShowRank) {
				data.push(score.politicsRank);
			}
		}
		if (score.history) {
			data.push(score.history);
			if(isShowRank) {
				data.push(score.historyRank);
			}
		}
		if (score.geography) {
			data.push(score.geography);
			if(isShowRank) {
				data.push(score.geographyRank);
			}
		}
		if (score.total) {
			data.push(score.total);
		}
		
		//移除掉undefined或者是空字符串
		data = data.filter(function(val){
			return !(typeof(val) == 'undefined' || val === "");
		});
		//TODO: 根据考试的配置，决定是否显示班级排名和年级排名
		data.push(score.isShowClassRank ? score.totalRank : '-');
		data.push(score.isShowGradeRank ? '1' : '-');
		
		//操作列
		data.push('');
		table.row.add(data).draw(false);
	}
	table.column(0).visible(false);//隐藏ID列（第1列），用于更新的时候的主键
	table.columns.adjust().draw(false);//调整宽度，然后重画表格
}

//根据成绩数据的值隐藏没有值的列
function initDynamicColumns(score, isShowRank) {
	if (score.chinese) {
		addField('chinese','语文');
		if(isShowRank) {
			addField('chineseRank','');
		}
	}
	if (score.math) {
		addField('math','数学');
		if(isShowRank) {
			addField('mathRank','');
		}
	}
	if (score.english) {
		addField('english','英语');
		if(isShowRank) {
			addField('englishRank','');
		}
	}
	if (score.physics) {
		addField('physics','物理');
		if(isShowRank) {
			addField('physicsRank','');
		}
	}
	if (score.chemistry) {
		addField('chemistry','化学');
		if(isShowRank) {
			addField('chemistryRank','');
		}
	}
	if (score.biologic) {
		addField('biologic','生物');
		if(isShowRank) {
			addField('biologicRank','');
		}
	}
	if (score.politics) {
		addField('politics','政治');
		if(isShowRank) {
			addField('politicsRank','');
		}
	}
	if (score.history) {
		addField('history','历史');
		if(isShowRank) {
			addField('historyRank','');
		}
	}
	if (score.geography) {
		addField('geography','地理');
		if(isShowRank) {
			addField('geographyRank','');
		}
	}
	if (score.total) {
		addField('total','总分');
	}
	
	//是否显示班级排名和年级排名
	addField('clazzrank','班名');
	addField('graderank','级名');
	addField('action','操作');
}

function addField(field, name) {
	var th = '<th dynamic-field="' + field + '">' + name + '</th>';
	$("#scoreTable thead tr").append(th);
}

//【管理员成绩查询页】【成绩上传页】改变学期下拉菜单的时候，重新获取该学期的考试信息，并初始化考试下拉菜单
function initExamSelect() {
	//获取selected的值
	var selectedSemester = $("#semesterSelect").children('option:selected').val();
	//如果没有选择有效的，则直接返回
	if (selectedSemester == 'none') {
		clearSelectOption("#examSelect");
		return;
	}
	var url = '/exam/findBySemester?semester_id=' + selectedSemester;
	ajax('GET', url, null, null, addExamOption);
}

//将查询到的学期信息动态增加到下拉菜单中
function addExamOption(array) {
	clearSelectOption("#examSelect");
	//再依次添加
	for(var index in array) {
		var exam = array[index];
		var option = "<option value='" + exam.id+"'>" + exam.name + "</option>";
		$("#examSelect").append(option);
	}
	//用中文渲染select2
	$("#examSelect").select2({
		language: "zh-CN"
	});
}

function updateSuccessCallback() {
	//隐藏模态框
	$('#myModal').modal('hide');
	//模拟用户点击查询，以刷新数据
	$('#queryBtn').trigger('click');
	//用toastr显示一个会自动消失的消息
	toastr.success("修改成功！数据已更新。");
}

//决定时否显示“单科排名”。只有当考试允许显示排名，而且复选框选中时，才显示排名
function getIsShowRank(score) {
	//默认不显示
	var isShowRank = false;
	//检查复选框是否可用且被勾选
	var isCheckboxChecked = $("#isShowRankChkbox").attr("disabled") != 'disabled' 
		&& $("#isShowRankChkbox").prop("checked");
	//考试允许显示排名，而且复选框选中时，才显示排名
	isShowRank = score.isShowRank && isCheckboxChecked;
	return isShowRank;
}