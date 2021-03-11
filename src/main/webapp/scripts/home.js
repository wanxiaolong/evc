/**
 * 本文件为home.jsp使用的初始化脚本。
 * 脚本中需要用到common.js的getWebRoot()函数，所以需要同时导入common.js文件。
 */
var webroot = getWebRoot();
$(document).ready(function(){
	queryAllCarousels();
});

//查询所有的轮播信息
function queryAllCarousels() {
	ajax('GET', '/carousel/all', null, null, initCarousel);
}


//动态增加查询到的轮播图片
function initCarousel(array) {
	//先清空原来的轮播图片
	var jqIndicator = $("#home_carousel .carousel-indicators");
	var jqImgDiv = $("#home_carousel .carousel-inner");
	
	jqIndicator.empty();
	jqImgDiv.empty();
	
	//再依次添加
	for(var index in array) {
		var carousel = array[index];
		
		//只对enabled的元素进行轮播
		if (!carousel.enabled) {
			continue;
		}
		
		//添加锚点元素
		var li = '<li data-target="#home_carousel" data-slide-to="' + index + '"></li>';
		jqIndicator.append(li);
		
		//添加图片元素
		var div = '<div class="item">' + 
						'<img src="' + webroot + carousel.imgUrl + '" height="500px" alt="' + carousel.altText + '">' +
					'</div>';
		jqImgDiv.append(div);
	}
	//设置第一张图片为'active'。这里是jQuery的方法操作，也可以用jQuery的选择器
	jqIndicator.children().first().addClass("active");
	jqImgDiv.children().first().addClass("active");
}

