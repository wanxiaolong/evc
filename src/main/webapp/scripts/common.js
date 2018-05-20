//本文件定义了非常常用的公共方法。

//用于配置消息提示插件toastr。nenxitong zhong suoyou d xiaoti tishi dou yong zhege chajian .
toastr.options = {
	positionClass: "toast-top-center", //显示消息的位置
	timeOut: "3000",//显示的时间
};

/**
 * 获取App的根目录
 */
function getWebRoot() {
	var path = location.href;
	var protocal = path.substring(0, path.indexOf('//') + 2);
	path = path.substring(path.indexOf('//') + 2);
	var host = path.substring(0, path.indexOf('/'));
	path = path.substring(path.indexOf('/') + 1);
	var app = path.substring(0, path.indexOf('/'));
	return protocal + host + '/' + app;
}

/**
 * 检查所有必填的字段，看是否为空。如果为空，则将边框标红。
 */
function checkRequiredField() {
	var errorFields = [];
	//对每个含有required属性的控件绑定事件
	$("[required]").each(function(index, element){
		var tagName = element.tagName;
		//获取控件值
		var val = '';
		if (tagName == 'SELECT') { //普通的select
			val = $(this).children('option:selected').attr('value');
		} else if (tagName == 'INPUT') {//普通的input
			val = $(this).val();
		}

		//检查输入
		if (val == null || val == '') {
			$(this).addClass('err-bdr');
			errorFields.push($(this).attr('id'));
		} else {
			$(this).removeClass('err-bdr');
		}
		
		
		//对于select2的下拉菜单，需要检查修饰原始select的span的值，该span的id为：select2-[id]-container
		var isSelect2 = $(this).hasClass('select2-hidden-accessible');
		if (isSelect2) {
			var select2Id = 'select2-' + $(this).attr('id') + '-container';
			var select2Span = $('#' + select2Id);
			val = select2Span.html();
			
			//检查输入：如果select的值是'--请选择--'，那等于没选
			if (val == '--请选择--') {
				select2Span.addClass('err-bdr');
				errorFields.push($(this).attr('id'));
			} else {
				select2Span.removeClass('err-bdr');
			}
		}
		
	});
	return errorFields.length == 0 ? true : false;
}


/**
 * 工具类，用于发送ajax请求。超时时间为5s。
 * @param type 请求类型，如GET,POST
 * @param url 请求地址，如/file/upload，注意这里不需要加host和port和appName
 * @param data 要发送的数据
 * @param dataType 数据类型，通常为json
 * @param successCallback 请求成功后的回调函数
 * @param errorCallback 请求失败后的回调函数
 */
function ajax(type, url, data, dataType, successCallback, errorCallback) {
	var log = "[" + type + "] URL=" + url + ", Data=" + JSON.stringify(data);
	printLog("请求： --> " + log);
	$.ajax({
		type: type,
		url: webroot + url,
		data: data,
		dataType: dataType,
		timeout: 5000, //超时时间
		success: function (data) {
			printLog("成功！ <-- [" + url + "] "+ JSON.stringify(data))
			if (data.errorMessage != null) {
				toastr.error(data.errorMessage);
				return;
			}
			if (successCallback != null && typeof(successCallback) == 'function') {
				//如果提供了成功回调，则调用
				successCallback(data.response);
			} else {
				//如果没提供成功回调，这里只是弹出一个错误消息提示
				toast.error("操作成功！");
			}
		},
		error: function (jqXHR, status, error) {
			printLog("失败！ <-- [" + url + "] ，状态：" + status + "，异常：" + error);
			if (errorCallback != null && typeof(errorCallback) == 'function') {
				//如果提供了失败回调，则调用
				errorCallback();
			} else {
				//如果没提供失败回调，这里只是弹出一个错误消息提示
				toastr.error("请求失败！" + "状态：" + status + "，异常：" + error);
			}
		}
	});
}

//按照指定格式输出日志
function printLog(msg) {
	console.log(getCurrTime() + ' - ' + msg);
}
//按照指定格式输出错误日志
function printError(msg) {
	console.error(getCurrTime() + ' - ' + msg);
}

//获取当前时间，以[HH:mm:ss.sss]表示。主要用于日志记录。
function getCurrTime() {
	var dt = new Date();
	var hh = dt.getHours();
	hh = hh < 10 ? '0' + hh : hh;
	var mm = dt.getMinutes();
	mm = mm < 10 ? '0' + mm : mm;
	var ss = dt.getSeconds();
	ss = ss < 10 ? '0' + ss : ss;
	var sss = dt.getMilliseconds();
	sss = sss < 10 ? '00' + sss : (sss < 100 ? '0' + sss : sss);
	return "[" + hh + ":" + mm + ":" + ss + "." + sss + "]";
}

//【成绩查询页】查询所有的学期信息（用于初始化下拉列表）
function queryAllSemesters() {
	ajax('GET', '/semester/all', null, null, addSemesterOption);
}

//【成绩查询页】将查询到的学期信息动态增加到下拉菜单中
function addSemesterOption(array) {
	//先清空原来的选择项
	$("#semesterSelect").empty();
	$("#semesterSelect").append("<option value='none'>--请选择--</option>");
	//再依次添加
	for(var index in array) {
		var semester = array[index];
		var option = "<option value='" + semester.number+"'>" + semester.name + "</option>";
		$("#semesterSelect").append(option);
	}
	//用中文渲染select2
	$("#semesterSelect").select2({
		language: "zh-CN"
	});
}

//删除下拉菜单选项，只留下默认的选项
function clearSelectOption(selector) {
	//先清空原来的选择项
	$(selector).empty();
	$(selector).append("<option value='none'>--请选择--</option>");
}

//给select2包装的select设置选中项
function setSelect2SelectedOption(elementId, selectedValue) {
	var select2 = $("#" + elementId).select2({
		language: "zh-CN"
	});
	select2.val(selectedValue).trigger("change");
	select2.change();
}

//带有对话框的ajax调用成功的回调（更新或创建，需要关闭对话框）
function updateSuccessCallback() {
	//隐藏模态框
	$('#myModal').modal('hide');
	deleteSuccessCallback();
}

//删除操作的回调
function deleteSuccessCallback() {
	//再次查询，以刷新数据
	queryAll();
	//用toastr显示一个会自动消失的消息
	toastr.success("操作成功！数据已刷新。");
}

//解决模态框中的select2不能搜索（不能获取焦点）的问题。见help.txt#17
$.fn.modal.Constructor.prototype.enforceFocus = function () {}