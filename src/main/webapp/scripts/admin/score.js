/**
 * 本文件为score.jsp使用的初始化脚本。
 * 脚本中需要用到common.js的getWebRoot()和initExamSelect()函数，所以需要同时导入common.js文件。
 */
var webroot = getWebRoot();
toastr.options = {
	positionClass: "toast-top-center", //显示消息的位置
	timeOut: "3000",//显示的时间
};
$(document).ready(function(){
	table = initDataTable("scoreTable");
	
	//移除select2下拉菜单的title属性，没用
	$("#select2-semesterSelect-container").removeAttr("title");
	
	//监听查询按钮点击事件
	$('#queryBtn').click(function(){
		executeScoreQuery();
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
		submitScoreUpdate();
	});
});

function submitScoreUpdate() {
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
	
	$.ajax({
		type: 'POST',
		url: webroot + '/score/update',
		data: 	'id=' + id +
				'&chinese=' + chinese + 
				'&math=' + math + 
				'&english=' + english +
				'&physics=' + physics +
				'&chemistry=' + chemistry +
				'&biologic=' + biologic +
				'&politics=' + politics +
				'&history=' + history +
				'&geography=' + geography,
		success: function (data) {
			if (data.status != 0) {
				alert(data.errorMessage);
				return;
			}
			//隐藏模态框
			$('#myModal').modal('hide');
			//模拟用户点击查询，以刷新数据
			$('#queryBtn').trigger('click');
			//用toastr显示一个会自动消失的消息
			toastr.success("修改成功！数据已更新。");
		},
		error: function () {
			toastr.error("修改失败！请稍后再试。");
		}
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
										"<i class='fa fa-edit'></i>" +
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
	config.columnDefs
	return $('#' + id).DataTable(config);
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
			score,
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
		//TODO: 根据考试的配置，决定是否显示班级排名和年级排名
		data.push(score.isShowClassRank ? '1' : '-');
		data.push(score.isShowGradeRank ? '1' : '-');
		
		//操作列
		data.push('');
		table.row.add(data).draw(false);
	}
	table.column(0).visible(false);//隐藏ID列（第1列），用于更新的时候的主键
	table.columns.adjust().draw(false);//调整宽度，然后重画表格
}