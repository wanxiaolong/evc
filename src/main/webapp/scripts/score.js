/**
 * 本文件为score.jsp使用的初始化脚本。
 * 脚本中需要用到common.js的getWebRoot()函数，所以需要同时导入common.js文件。
 */
var webroot = getWebRoot();
$(document).ready(function(){
	//如果学期下拉菜单变化，则查询该学期下的所有考试信息。成绩查询将根据这个选中的考试信息进行。
	$('#semesterSelect').change(function(){
		var selectedSemester = $(this).children('option:selected').val();//获取selected的值
		$.ajax({
			type: 'GET',
			url: webroot + '/rest/exam/findBySemester?semester=' + selectedSemester,
			success: function (data) {
				//移除examSelect所有带有value的option
				$("#examSelect option[value]").remove();
				
				var examArray = data.response;
				if (examArray.length > 0) {
					for(var index in examArray) {
						var exam = examArray[index];
						//动态创建并添加select的option
						var option = new Option(exam.name, exam.id);
						$("#examSelect").append(option);
					}
				}
			},
			error: function () {
				console.log("调用查询考试信息接口失败！");
			}
		});
	});
	
	$('#queryBtn').click(function(){
		var examId = $("#examSelect").children('option:selected').val();
		var name = $("#name").val();
		var birthday = $("#birthday").val();
		$.ajax({
			type: 'POST',
			url: webroot + '/rest/score/query',
			data: 'exam_id=' + examId + '&name=' + name + '&birthday=' + birthday,
			success: function (data) {
				var array = data.response;
				if (array.length > 0) {
					//先删除现有的行，再动态创建并添加行
					$("#scoreTable tbody tr").remove();
					for(var index in array) {
						var score = array[index];
						var tr = 
						"<tr>" +
							"<td>" + score.studentNumber + "</td>" +
							"<td>" + score.studentName + "</td>" +
							"<td>" + score.chinese + "</td>" +
							"<td>" + score.math + "</td>" +
							"<td>" + score.english + "</td>" +
							"<td>" + score.physics + "</td>" +
							"<td>" + score.chemistry + "</td>" +
							"<td>" + score.biologic + "</td>" +
							"<td>" + score.politics + "</td>" +
							"<td>" + score.history + "</td>" +
							"<td>" + score.geography + "</td>" +
							"<td>" + score.total + "</td>" +
						"</tr>";
						$("#scoreTable tbody").append(tr);
					}
				} else {
					var tr = "<tr><td colspan=\"12\">没有记录！</td></tr>";
					$("#scoreTable tbody tr").remove();
					$("#scoreTable tbody").append(tr);
				}
			},
			error: function () {
				console.log("调用查询接口失败！");
			}
		});
	});
});