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
	
	//显示文件信息列表
	queryAllFiles();
	
	//文件上传模块也在这个页面中，这里需要初始化
	initFileUpload();
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

//初始化文件上传模块
function initFileUpload() {
	$("#uploadfile").fileinput({
		language: 'zh', //设置语言
		uploadUrl: webroot + "/file/upload", //上传的地址
		//allowedFileExtensions: ['jpg', 'gif', 'png'],//接收的文件后缀
		//uploadExtraData:{"id": 1, "fileName":'123.mp3'},
		uploadAsync: true, //默认异步上传
		showUpload: true, //是否显示上传按钮
		showRemove: true, //显示移除按钮
		showPreview: true, //是否显示预览
		showCaption: true,//是否显示标题
		browseClass: "btn btn-primary", //按钮样式
		dropZoneEnabled: false,//是否显示拖拽区域
		//minImageWidth: 50, //图片的最小宽度
		//minImageHeight: 50,//图片的最小高度
		//maxImageWidth: 1000,//图片的最大宽度
		//maxImageHeight: 1000,//图片的最大高度
		minFileSize: 1,
		maxFileSize: 102400,//单位为kb，如果为0表示不限制文件大小
		//minFileCount: 1,
		maxFileCount: 10, //表示允许同时上传的最大文件个数
		enctype:'multipart/form-data',
		validateInitialCount:true,
		previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
		msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
	}).on("fileuploaded", function (event, data, previewId, index){
		//上传成功后的回调
		toastr.success("文件【" + data.jqXHR.responseJSON.response[0] + "】上传成功！");
		//刷新文件信息列表
		queryAllFiles();
	});
}