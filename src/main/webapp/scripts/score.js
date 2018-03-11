/**
 * 本文件为score.jsp使用的初始化脚本。
 * 脚本中需要用到common.js的getWebRoot()函数，所以需要同时导入common.js文件。
 */
var webroot = getWebRoot();
$(document).ready(function(){
	//如果学期下拉菜单变化，则查询该学期下的所有考试信息。成绩查询将根据这个选中的考试信息进行。
//	$('#semesterSelect').change(function(){
//		var selectedSemester = $(this).children('option:selected').val();//获取selected的值
//		$.ajax({
//			type: 'GET',
//			url: webroot + '/rest/exam/findBySemester?semester=' + selectedSemester,
//			success: function (data) {
//				//移除examSelect所有带有value的option
//				$("#examSelect option[value]").remove();
//				
//				var examArray = data.response;
//				if (examArray.length > 0) {
//					for(var index in examArray) {
//						var exam = examArray[index];
//						//动态创建并添加select的option
//						var option = new Option(exam.name, exam.id);
//						$("#examSelect").append(option);
//					}
//				}
//			},
//			error: function () {
//				console.log("调用查询考试信息接口失败！");
//			}
//		});
//	});
	
	$('#queryBtn').click(function(){
		//先执行必要的字段检查
		if(!checkRequiredField()) {
			return;
		}
		var queryType = $("button[class*='selected']").attr('value');
		var name = $("#name").val();
		var birthday = $("#birthday").val();
		var verifyCode = $("#verifyCode").val();
		$.ajax({
			type: 'POST',
			url: webroot + '/rest/score/query',
			data: 	'query_type=' + queryType + 
					'&name=' + name + 
					'&birthday=' + birthday,
			success: function (data) {
				var array = data.response;
				if (data.errorMessage != null) {
					alert(data.errorMessage);
					return;
				}
				if (array.length > 0) {
					addRows(array);
				}
			},
			error: function () {
				alert("调用成绩查询接口失败！");
				console.log("调用查询接口失败！");
			}
		});
	});
	table = initDataTable("scoreTable");
});

function initDataTable(id) {
	return $('#' + id).DataTable({
		language: {
			url: webroot + '/localization/chinese.json'
		},
		bPaginate: true,//是否显示分页器（左上角显示 ‘每页显示x条记录’）
		bFilter: true, //是否显示搜索框（右上角）
		bSort: true, //是否允许列排序
		aaSorting: [[0, "asc"]] //默认的排序方式，第1列，升序排列
	});
}
function addRows(array) {
	//先删除现有的行，再动态创建并添加行。
	//注意:
	//1. 这里使用的是rows(selector)方法而不是row(selector)方法，这里用于选择多行
	//2. 从表格中移除之后，需要重新调用draw()方法
	table.rows("tr").remove().draw();
	for(var index in array) {
		var score = array[index];
		table.row.add([
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
		]).draw(false);
	}
}