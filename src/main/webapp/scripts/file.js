/**
 * 本文件为file.jsp使用的初始化脚本。
 * 脚本中需要用到common.js的getWebRoot()函数，所以需要同时导入common.js文件。
 */
var webroot = getWebRoot();
$(document).ready(function(){
	table = initDataTable("fileTable");
	
	queryAllFiles();
});

//查询所有的资料信息
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

//初始化Datatables表格
function initDataTable(id) {
	var config = {
		language: {
			url: webroot + '/localization/chinese.json'
		},
		columnDefs: [
			{
				"targets": 1,//这里是第二列（第1列是json对象，没显示）
				"render": function(data, type, row) { //row是一个数组，代表这一行的所有数据
					return "<a href='" + webroot + "/file/" + row[0].name + "'>" + data + "</a>";
				}
			},
			{"targets": -1, "sWidth": "200px"},
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
			file.downloadCount,
			file.creationDate
		];
		table.row.add(data).draw(false);
	}
	table.column(0).visible(false);//隐藏ID列（第1列），用于更新的时候的主键
	table.columns.adjust().draw(false);//调整宽度，然后重画表格
}