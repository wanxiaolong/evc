/**
 * 本文件为message_add.jsp使用的初始化脚本。
 */
var webroot = getWebRoot();
$(document).ready(function(){
	$('#submit').click(function(){
		if(!checkRequiredField()) {
			return;
		}
	});
});