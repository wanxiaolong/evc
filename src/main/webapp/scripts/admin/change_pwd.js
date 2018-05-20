/**
 * 本文件为admin/change_pwd.jsp使用的初始化脚本。
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
	
	var data =	'password=' + pwd + 
				'&password_new=' + pwd_new + 
				'&password_confirm=' + pwd_confirm +
				'&verify_code=' + verify_code,
				
	ajax('POST', '/admin/change_pwd', data, null, changePwdSuccessCallback, changePwdErrorCallback);
}

//设置验证码图片
function setVerifyCodeImg() {
	$('#verify_code_img').attr('src', webroot + '/server/getcode?t=' + Math.random());
}

//ajax调用成功的回调
function changePwdSuccessCallback() {
	setVerifyCodeImg();
	//执行成功之后，跳转到主页
	location.href=webroot + "/admin/home.jsp";
}

//ajax调用失败的回调
function changePwdErrorCallback() {
	setVerifyCodeImg();
	toastr.error("登录失败！请稍后再试。");
}