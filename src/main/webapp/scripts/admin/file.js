/**
 * 本文件为admin/file.jsp使用的初始化脚本。
 * 脚本中需要用到common.js的getWebRoot()和initExamSelect()函数，所以需要同时导入common.js文件。
 */
var webroot = getWebRoot();
$(document).ready(function(){
	table = initDataTable("fileTable");

	//表格操作列上的“删除”按钮的点击事件
	$('#fileTable tbody').on('click', 'button#deleterow', function() {
		//先将行的数据转换成数组
		var data = table.row($(this).parents('tr')).data();
		//第一列（页面上不显示）里保存的是完整的数据对象
		del(data[0]);
	});
	
	//“增加考试”按钮的点击事件
	$("#addBtn").click(function(){
		window.location.href = webroot + "/admin/file_upload.jsp";
	});
	
	queryAllFiles();
});

//查询所有的文件信息
function queryAllFiles() {
	$.ajax({
		type: 'GET',
		url: webroot + '/file/all',
		success: function (data) {
			var array = data.response;
			addRows(array);
		},
		error: function () {
			toastr.error("调用查询接口失败！");
		}
	});
}

//点击“删除”按钮后的操作
function del(file) {
	$.ajax({
		type: 'POST',
		url: webroot + '/file/delete',
		data: 	'id=' + file.id + 
				'&name=' + file.name,
		success: function (data) {
			//删除成功后，重新查询数据
			queryAllFiles();
			toastr.success("删除成功！数据已刷新。");
		},
		error: function () {
			toastr.error("删除失败！");
		}
	});
}

//初始化Datatables表格
function initDataTable(id) {
	var deleteBtn = "<button id='deleterow' class='btn btn-primary btn-danger' type='button'>" +
						"<i class='fa fa-trash'></i>删除" +
					"</button>"
	
	var config = {
		language: {
			url: webroot + '/localization/chinese.json'
		},
		columnDefs: [
			{
				"targets": -1,//倒数第1列，编辑
				"sWidth": "100px",
				"sortable": false,//不能排序
				"searchable": false,//不能搜索
				"data": null,//data指定要显示的字段。这里设为null，即不显示任何字段
				"defaultContent": deleteBtn
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
		var file = array[index];
		var data = [
			file,
			file.name,
			file.path,
			file.downloadCount,
			file.creationDate
		];
		//操作列
		data.push('');
		table.row.add(data).draw(false);
	}
	table.column(0).visible(false);//隐藏ID列（第1列），用于更新的时候的主键
	table.columns.adjust().draw(false);//调整宽度，然后重画表格
}