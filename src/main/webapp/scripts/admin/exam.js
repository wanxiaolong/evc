/**
 * 本文件为admin/exam.jsp使用的初始化脚本。
 */
var allSubjects;//保存所有的科目信息
var webroot = getWebRoot();
$(document).ready(function(){
	table = initDataTable("examTable");
	
	//表格操作列上的“编辑”按钮的点击事件
	$('#examTable tbody').on('click', 'button#editrow', function() {
		//先将行的数据转换成数组
		var data = table.row($(this).parents('tr')).data();
		//第一列（页面上不显示）里保存的是完整的数据对象
		edit(data[0]);
	});
	
	//表格操作列上的“删除”按钮的点击事件
	$('#examTable tbody').on('click', 'button#deleterow', function() {
		//先将行的数据转换成数组
		var data = table.row($(this).parents('tr')).data();
		//第一列（页面上不显示）里保存的是完整的数据对象
		del(data[0]);
	});
	
	//表格操作列上的“删除成绩”按钮的点击事件
	$('#examTable tbody').on('click', 'button#deletescore', function() {
		//先将行的数据转换成数组
		var data = table.row($(this).parents('tr')).data();
		//第一列（页面上不显示）里保存的是完整的数据对象
		deleteScoreByExam(data[0]);
	});

	//表格操作列上的“删除成绩”按钮的点击事件
	$('#examTable tbody').on('click', 'button#uploadscore', function() {
		//先将行的数据转换成数组
		var data = table.row($(this).parents('tr')).data();
		//第一列（页面上不显示）里保存的是完整的数据对象
		uploadScoreForExam(data[0]);
	});
	
	//点击模态框的提交按钮，执行表单提交
	$("#confirm-update").click(function(){
		submitUpdate();
	});
	
	//“增加考试”按钮的点击事件
	$("#addBtn").click(function(){
		add();
	});
	
	queryAll();
	
	//初始化学期下拉菜单（common.js中）
	queryAllSemesters();
});

//根据考试ID，删除该考试下的所有成绩
function deleteScoreByExam(exam) {
	var input = confirm("确定删除 [" + exam.name + "] 的所有成绩吗？注意：成绩将永久被删除，不可恢复。");
	
	if (input) {
		ajax('POST', '/score/deletebyexam', 'exam_id=' + exam.id, null, deleteSuccessCallback);
	}
}

//点击“删除”按钮后的操作
function del(exam) {
	ajax('POST', '/exam/delete', 'id=' + exam.id, null, deleteSuccessCallback);
}

//查询所有的学期信息（用于初始化下拉列表）
function queryAll() {
	ajax('GET', '/exam/all', null, null, queryExamSuccessCallback);
}

//点击“增加考试”按钮后的操作
function add() {
	//清除表单中所有的值（点击“编辑”后，表单的值会被初始化掉，之后若点击“新增考试”，则此时表单中的值必须先清除掉）
	var fields = $("#edit-form").serializeArray();
	$.each(fields, function(i, field){
		$(":input[name='"+field.name+"']").val('');
	});
	
	//对于select2，需要设置其选中项
	setSelect2SelectedOption("semesterSelect", "none");
	
	//重新创建所有的checkbox
	createAllSubjectCheckbox("subjects");
}

//点击“编辑”按钮后的操作
function edit(exam) {
	var fields = $("#edit-form").serializeArray();
	$.each(fields, function(i, field){
		//jquery根据name属性查找input，然后从score中获取对应的属性值，并将值设置给input
		//注意这里只能通过[]的方式来获取属性值
		$(":input[name='"+field.name+"']").val(exam[field.name]);
	});
	//初始化字段之后，对个别字段进行特殊处理
	$("input[name='isShowRank']").val(exam.isShowRank ? '是' : '否');
	$("input[name='isShowClassRank']").val(exam.isShowClassRank ? '是' : '否');
	$("input[name='isShowGradeRank']").val(exam.isShowGradeRank ? '是' : '否');
	
	//给select2包装的select设置选中项
	setSelect2SelectedOption("semesterSelect", exam.semesterNumber);
	
	//初始化参考科目，并添加到页面中
	createAllSubjectCheckbox("subjects");
	
	//设置参考科目选中
	var ids = exam.subjectIds.split(',');
	$("#subjects input[type='checkbox']").each(function(){
		var optionValue = $(this).attr('value');
		for(var j in ids) {
			if (optionValue == ids[j]) {
				$(this).attr('checked','checked');
				break;
			}
		}
	});
}

//为所有的科目创建都没有选中的复选框
function createAllSubjectCheckbox(elementId) {
	$("#" + elementId).empty();
	for(var i in allSubjects) {
		var checkbox = "<input type='checkbox' value='" + allSubjects[i].id + "' name='subjects'/>" + allSubjects[i].name;
		$("#" + elementId).append(checkbox + "&nbsp;&nbsp;");
	}
}

//提交数据，执行更新。成功或失败都会在页面上显示消息。
function submitUpdate() {
	//如果id为空，则是创建（因为创建的时候会清除掉这个input中的值）
	var id = $("#edit-form input[name='id']").val();
	var name = $("#edit-form input[name='name']").val();
	var people = $("#edit-form input[name='people']").val();
	var date = $("#edit-form input[name='date']").val();
	var isShowRank = $("#edit-form input[name='isShowRank']").val();
	var isShowClassRank = $("#edit-form input[name='isShowClassRank']").val();
	var isShowGradeRank = $("#edit-form input[name='isShowGradeRank']").val();
	var semesterNumber = $("#semesterSelect").children('option:selected').val();
	
	//由于科目是个数组，所以这里需要用foreach
	var subjectIds = [];
	$.each($("#edit-form input[name='subjects']:checked"), function(){
		subjectIds.push($(this).val());
	});
	
	var isCreate = id == '';
	var url = isCreate ? '/exam/create' : '/exam/update';
	var data =	'id=' + id +
				'&name=' + name + 
				'&subject_ids=' + subjectIds + 
				'&people=' + people +
				'&date=' + date +
				'&is_show_rank=' + isShowRank +
				'&is_show_class_rank=' + isShowClassRank +
				'&is_show_grade_rank=' + isShowGradeRank +
				'&semester_id=' + semesterNumber;
	
	ajax('POST', url, data, null, updateSuccessCallback);
}

//初始化Datatables表格
function initDataTable(id) {
	var config = {
		language: {
			url: webroot + '/localization/chinese.json'
		},
		columnDefs: [
			//--------------------------------      //前几列的名字固定，宽度固定
			{"targets": 1, "sWidth": "120px"},  //学期
			{"targets": 2, "sWidth": "100px"},  //考试名称
			//----------------------------------//后续列为成绩列，宽度自动确定
			//----------------------------------//操作列
			//{"targets": 3, "sWidth": "80px"},  //参考科目
			{"targets": 4, "sWidth": "80px"},   //考试日期
			{
				"targets": [5,6,7,8,9], 
				"sWidth": "40px", //参考人数、是否已上传成绩、单科排名、班级排名、年级排名
				"sortable": false,
			},
			{
				"targets": -1,//倒数第1列，编辑
				"sWidth": "300px",
				"sortable": false,//不能排序
				"searchable": false,//不能搜索
				//"data": null,//data指定要显示的字段。这里设为null，即不显示任何字段
				//"defaultContent": ""
			},
			//为了不让没值的单元格报错，需要为其他没指定target的列设置默认值，否则会弹出警告信息。
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

//跳转到score_upload.jsp并传入参数，那边直接用这两个参数初始化
function uploadScoreForExam(exam) {
	var data = 'exam_id=' + exam.id + '&semester_number=' + exam.semesterNumber;
	location.href = webroot + '/admin/score_upload.jsp?' + data;
}

//动态的向表格中增加数据
function addRows(array) {
	//先删除现有的行，再动态创建并添加行。
	table.rows("tr").remove().draw();
	if (array.length == 0) {
		return;
	}
	
	//每一行都有这两个操作
	var editBtn = 	"<button id='editrow' class='btn btn-primary' type='button' " +
						"data-toggle='modal' data-target='#myModal' data-backdrop='false'>" +
						"<i class='fa fa-edit'></i>编辑" +
					"</button>";
	var deleteBtn = "<button id='deleterow' class='btn btn-primary btn-danger' type='button'>" +
						"<i class='fa fa-trash'></i>删除考试" +
					"</button>";
	
	var uploadBtn = "<button id='uploadscore' class='btn btn-primary btn-success' type='button'>" +
						"<i class='fa fa-upload'></i>上传成绩" +
					"</button>";
	
	//有成绩的行，才显示“删除成绩”按钮
	var deleteScoreBtn = 	"<button id='deletescore' class='btn btn-primary btn-danger' type='button'>" +
								"<i class='fa fa-trash'></i>删除成绩" +
							"</button>";
	
	
	for(var index in array) {
		var exam = array[index];
		var data = [
			exam,
			exam.semesterName,
			exam.name,
			exam.subjectNames,//参考科目
			exam.date,
			exam.people,
		];
		data.push(exam.isScoreUploaded ? '是' : '否');
		data.push(exam.isShowRank ? '是' : '否');
		data.push(exam.isShowClassRank ? '是' : '否');
		data.push(exam.isShowGradeRank ? '是' : '否');
		//操作列
		if (exam.isScoreUploaded) {
			//如果该考试已经上传成绩，则“删除”按钮不可用，“删除成绩”按钮可用
			data.push(editBtn + deleteScoreBtn);
		} else {
			//如果该考试没有上传成绩，则“删除”按钮可用，“删除成绩”按钮不可用
			data.push(editBtn + deleteBtn + uploadBtn);
		}
		table.row.add(data).draw(false);
	}
	table.column(0).visible(false);//隐藏ID列（第1列），用于更新的时候的主键
	table.columns.adjust().draw(false);//调整宽度，然后重画表格
}

//ajax查询成功的回调
function queryExamSuccessCallback(data) {
	allSubjects = data.subjects;
	var array = data.exams;
	addRows(array);
}
