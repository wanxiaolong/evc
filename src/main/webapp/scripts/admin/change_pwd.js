/**
 * 本文件为score.jsp使用的初始化脚本。
 * 脚本中需要用到common.js的getWebRoot()函数，所以需要同时导入common.js文件。
 */

var webroot = getWebRoot();
$(document).ready(function(){
	//设置验证码图片
	setVerifyCodeImg();
	$("#verify_code_img").click(function(){
		setVerifyCodeImg();
	});
	
	
	//点击成绩修改模态框的提交按钮，执行表单提交
	$("#submitBtn").click(function(){
		changePassword();
	});
});

//提交数据，执行成绩更新。成功或失败都会在页面上显示消息。
function changePassword() {
	var pwd = $("input[name='password']").val();
	var pwd_new = $("input[name='password_new']").val();
	var pwd_confirm = $("input[name='password_confirm']").val();
	var verify_code = $("input[name='verify_code']").val();
	
	$.ajax({
		type: 'POST',
		url: webroot + '/admin/change_pwd',
		data: 	'password=' + pwd + 
				'&password_new=' + pwd_new + 
				'&password_confirm=' + pwd_confirm +
				'&verify_code=' + verify_code,
		success: function (data) {
			if (data.status != 0) {
				toastr.error(data.errorMessage);
				//失败的时候也刷新验证码。
				setVerifyCodeImg();
				return;
			}
			//执行成功之后，跳转到主页
			location.href=webroot + "/admin/home.jsp";
		},
		error: function () {
			toastr.error("修改失败！请稍后再试。");
			setVerifyCodeImg();
		}
	});
}

//设置验证码图片
function setVerifyCodeImg() {
	$('#verify_code_img').attr('src', webroot + '/server/getcode?t=' + Math.random());
}