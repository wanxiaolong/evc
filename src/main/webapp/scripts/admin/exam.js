/**
 * 本文件为score.jsp使用的初始化脚本。
 * 脚本中需要用到common.js的getWebRoot()和initExamSelect()函数，所以需要同时导入common.js文件。
 */
toastr.options = {
	positionClass: "toast-top-center", //显示消息的位置
	timeOut: "3000",//显示的时间
};
var webroot = getWebRoot();
$(document).ready(function(){
	table = initDataTable("examTable");
	
	//表格操作列上的“编辑”按钮的点击事件
	$('#examTable tbody').on('click', 'button#editrow', function() {
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
		submitExamUpdate();
	});
	
	queryAllExams();
});

//【成绩查询页】查询所有的学期信息（用于初始化下拉列表）
function queryAllExams() {
	$.ajax({
		type: 'GET',
		url: webroot + '/exam/all',
		success: function (data) {
			var array = data.response;
			addRows(array);
		},
		error: function () {
			alert("调用学生信息查询接口失败！");
			console.log("调用查询接口失败！");
		}
	});
}

//提交数据，执行成绩更新。成功或失败都会在页面上显示消息。
function submitExamUpdate() {
	var id = $("#edit-form input[name='id']").val();
	var name = $("#edit-form input[name='name']").val();
	var people = $("#edit-form input[name='people']").val();
	var date = $("#edit-form input[name='date']").val();
	var isShowClassRank = $("#edit-form input[name='isShowClassRank']:checked").val();
	var isShowGradeRank = $("#edit-form input[name='isShowGradeRank']:checked").val();
	
	$.ajax({
		type: 'POST',
		url: webroot + '/exam/update',
		data: 	'id=' + id +
				'&name=' + name + 
				'&people=' + people +
				'&date=' + date +
				'&isShowClassRank=' + isShowClassRank +
				'&isShowGradeRank=' + isShowGradeRank,
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
			toastr.success("修改成功！请刷新页面。");
		},
		error: function () {
			toastr.error("修改失败！请稍后再试。");
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
			//--------------------------------      //前几列的名字固定，宽度固定
			{"targets": [1,2], "sWidth": "150px"},  //学期
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
	//先删除现有的行，再动态创建并添加行。
	table.rows("tr").remove().draw();
	if (array.length == 0) {
		return;
	}
	
	for(var index in array) {
		var exam = array[index];
		var data = [
			exam,
			exam.semesterName,
			exam.name,
			exam.people,
			exam.date,
		];
		data.push(exam.isShowClassRank ? '是' : '否');
		data.push(exam.isShowGradeRank ? '是' : '否');
		
		//操作列
		data.push('');
		table.row.add(data).draw(false);
	}
	table.column(0).visible(false);//隐藏ID列（第1列），用于更新的时候的主键
	table.columns.adjust().draw(false);//调整宽度，然后重画表格
}