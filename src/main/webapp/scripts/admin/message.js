/**
 * 本文件为admin/message.jsp使用的初始化脚本。
 */
var allSubjects;//保存所有的科目信息
var webroot = getWebRoot();
$(document).ready(function(){
	table = initDataTable("messageTable");
	
	//表格操作列上的“编辑”按钮的点击事件
	$('#messageTable tbody').on('click', 'button#editrow', function() {
		//先将行的数据转换成数组
		var data = table.row($(this).parents('tr')).data();
		//第一列（页面上不显示）里保存的是完整的数据对象
		edit(data[0]);
	});

	//表格操作列上的“删除”按钮的点击事件
	$('#messageTable tbody').on('click', 'button#deleterow', function() {
		//先将行的数据转换成数组
		var data = table.row($(this).parents('tr')).data();
		//第一列（页面上不显示）里保存的是完整的数据对象
		del(data[0]);
	});
	
	//点击模态框的提交按钮，执行表单提交
	$("#confirm-update").click(function(){
		submitUpdate();
	});
	
	//“添加公告”按钮的点击事件
	$("#addBtn").click(function(){
		add();
	});
	
	queryAll();
});

//查询所有的学期信息
function queryAll() {
	ajax('GET', '/message/all', null, null, addRows);
}

//点击“增加学生”按钮后的操作
function add() {
	//清除表单中所有的值（点击“编辑”后，表单的值会被初始化掉，之后若点击“新增学生”，则此时表单中的值必须先清除掉）
	var fields = $("#edit-form").serializeArray();
	$.each(fields, function(i, field){
		$(":input[name='"+field.name+"']").val('');
	});
}

//点击“删除”按钮后的操作
function del(message) {
	ajax('POST', '/message/delete', 'id=' + message.id, null, deleteSuccessCallback);
}

//点击“编辑”按钮后的操作
function edit(message) {
	var fields = $("#edit-form").serializeArray();
	$.each(fields, function(i, field){
		if(field.name == 'type') {
			var typeStr = convertTypeToStr(message[field.name]);
			$(":input[name='"+field.name+"']").val(typeStr);
		}
		
		//jquery根据name属性查找input，然后从score中获取对应的属性值，并将值设置给input
		//注意这里只能通过[]的方式来获取属性值
		$(":input[name='"+field.name+"']").val(message[field.name]);
	});
}

//提交数据，执行更新。成功或失败都会在页面上显示消息。
function submitUpdate() {
	//如果id为空，则是创建（因为创建的时候会清除掉这个input中的值）
	var id = $("#edit-form input[name='id']").val();
	var type = $("#edit-form input[name='type']").val();
	var title = $("#edit-form input[name='title']").val();
	var content = $("#edit-form input[name='content']").val();
	var contact = $("#edit-form input[name='contact']").val();
	
	var isCreate = id == '';
	var url = isCreate ? '/message/create' : '/message/update';
	var data =	'id=' + id +
				'&type=' + convertTypeToInt(type) +
				'&title=' + title +
				'&content=' + content +
				'&contact=' + contact;
	ajax('POST', url, data, null, updateSuccessCallback);
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
			{	"targets": 1, "sWidth": "60px"},
			{	"targets": 2, "sWidth": "150px"},
			{	"targets": 4, "sWidth": "100px"},
			{
				"targets": -1,//倒数第1列，编辑
				"sWidth": "160px",
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
		var message = array[index];
		var data = [
			message,
			message.type,
			message.title,
			message.content,
			message.nick,
			message.contact,
			message.creationDate,
		];
		//操作列
		data.push('');
		table.row.add(data).draw(false);
	}
	table.column(0).visible(false);//隐藏ID列（第1列），用于更新的时候的主键
	table.columns.adjust().draw(false);//调整宽度，然后重画表格
}

//转换留言类型。
function convertTypeToStr(type) {
	if (type == 1) return '提问';
	if (type == 2) return '建议';
	if (type == 3) return '吐槽';
}
//转换留言类型。
function convertTypeToInt(type) {
	if (type == '提问') return 1;
	if (type == '建议') return 2;
	if (type == '吐槽') return 3;
}