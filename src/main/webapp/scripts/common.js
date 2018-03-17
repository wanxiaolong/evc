//本文件定义了非常常用的公共方法。

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
			} else {
				select2Span.removeClass('err-bdr');
			}
		}
		
	});
	return errorFields.length == 0 ? true : false;
}