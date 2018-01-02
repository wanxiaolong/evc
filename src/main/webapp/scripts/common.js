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