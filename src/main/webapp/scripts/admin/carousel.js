/**
 * 本文件为admin/subject.jsp使用的初始化脚本。
 * 脚本中需要用到common.js的getWebRoot()和initExamSelect()函数，所以需要同时导入common.js文件。
 */
var allSubjects;//保存所有的科目信息
var webroot = getWebRoot();
$(document).ready(function(){
	table = initDataTable("dataTable");
	
	//表格操作列上的“编辑”按钮的点击事件
	$('#dataTable tbody').on('click', 'button#editrow', function() {
		//先将行的数据转换成数组
		var data = table.row($(this).parents('tr')).data();
		//第一列（页面上不显示）里保存的是完整的数据对象
		edit(data[0]);
	});

	//表格操作列上的“删除”按钮的点击事件
	$('#dataTable tbody').on('click', 'button#deleterow', function() {
		//先将行的数据转换成数组
		var data = table.row($(this).parents('tr')).data();
		//第一列（页面上不显示）里保存的是完整的数据对象
		del(data[0]);
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
});

//查询所有的列表信息
function queryAll() {
	$.ajax({
		type: 'GET',
		url: webroot + '/carousel/all',
		success: function (data) {
			var array = data.response;
			addRows(array);
		},
		error: function () {
			toastr.error("调用查询接口失败！");
		}
	});
}

//点击“增加xx”按钮后的操作
function add() {
	//清除表单中所有的值（点击“编辑”后，表单的值会被初始化掉，之后若点击“新增考试”，则此时表单中的值必须先清除掉）
	var fields = $("#edit-form").serializeArray();
	$.each(fields, function(i, field){
		$(":input[name='"+field.name+"']").val('');
	});
}

//点击“删除”按钮后的操作
function del(subject) {
	$.ajax({
		type: 'POST',
		url: webroot + '/carousel/delete',
		data: 	'id=' + subject.id,
		success: function (data) {
			//删除成功后，重新查询数据
			queryAll();
			toastr.success("删除成功！数据已刷新。");
		},
		error: function () {
			toastr.error("删除失败！");
		}
	});
}

//点击“编辑”按钮后的操作
function edit(subject) {
	var fields = $("#edit-form").serializeArray();
	$.each(fields, function(i, field){
		//jquery根据name属性查找input，然后从score中获取对应的属性值，并将值设置给input
		//注意这里只能通过[]的方式来获取属性值
		$(":input[name='"+field.name+"']").val(subject[field.name]);
	});
	//初始化字段之后，对个别字段进行特殊处理
	$("input[name='enabled']").val(subject.enabled ? '是' : '否');
}

//提交数据，执行更新。成功或失败都会在页面上显示消息。
function submitUpdate() {
	//如果id为空，则是创建（因为创建的时候会清除掉这个input中的值）
	var id = $("#edit-form input[name='id']").val();
	var imgUrl = $("#edit-form input[name='imgUrl']").val();
	var linkUrl = $("#edit-form input[name='linkUrl']").val();
	var altText = $("#edit-form input[name='altText']").val();
	var order = $("#edit-form input[name='order']").val();
	var enabled = $("#edit-form input[name='enabled']").val();
	
	var isCreate = id == '';
	$.ajax({
		type: 'POST',
		url: webroot + (isCreate ? '/carousel/create' : '/carousel/update'),
		data: 	'id=' + id +
				'&img_url=' + imgUrl +
				'&link_url=' + linkUrl +
				'&alt_text=' + altText +
				'&order=' + order +
				'&enabled=' + enabled,
		success: function (data) {
			if (data.status != 0) {
				toastr.error(data.errorMessage);
				return;
			}
			//隐藏模态框
			$('#myModal').modal('hide');
			//再次查询，以刷新数据
			queryAll();
			//用toastr显示一个会自动消失的消息
			toastr.success((isCreate ? "创建" : "修改") + "成功！数据已刷新。");
		},
		error: function () {
			toastr.error("修改失败！请稍后再试。");
		}
	});
}

//初始化Datatables表格
function initDataTable(id) {
	var editBtn = "<button id='editrow' class='btn btn-primary' type='button' " +
						"data-toggle='modal' data-target='#myModal' data-backdrop='false'>" +
						"<i class='fa fa-edit'></i>编辑" +
					"</button>";
	
	var deleteBtn = "<button id='deleterow' class='btn btn-primary btn-danger' type='button'>" +
						"<i class='fa fa-trash'></i>删除" +
					"</button>"
	
	var config = {
		language: {
			url: webroot + '/localization/chinese.json'
		},
		columnDefs: [
			{
				"targets": [-2, -3],//倒数第1列，编辑
				"sWidth": "70px",
				"sortable": false,//不能排序
				"searchable": false,//不能搜索
			},
			{
				"targets": -1,//倒数第1列，编辑
				"sWidth": "200px",
				"sortable": false,//不能排序
				"searchable": false,//不能搜索
				"data": null,//data指定要显示的字段。这里设为null，即不显示任何字段
				"defaultContent": 	editBtn + deleteBtn
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
		var carousel = array[index];
		var data = [
			carousel,
			carousel.imgUrl,
			carousel.linkUrl,
			carousel.altText,
			carousel.order,
			carousel.enabled ? '是' : '否',
		];
		//操作列
		data.push('');
		table.row.add(data).draw(false);
	}
	table.column(0).visible(false);//隐藏ID列（第1列），用于更新的时候的主键
	table.columns.adjust().draw(false);//调整宽度，然后重画表格
}