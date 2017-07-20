"use strict";

(function() {
	
	// 第一题
	
	// 从 jquery 源代码中抓出部分代码如下，从这个代码就很容易看出为什么 jquery 中支持 $("#id").addClass("class1").removeClass("class2") 这种语法了
	// 因为 jquery 在执行完成方法内部逻辑之后同时将 jquery 对象本身返回了
	/*
	jQuery.fn.extend({
		addClass: function( value ) {
			// do something ...
			return this;
		},
		removeClass: function( value ) {
			// do something ...
			return this;
		}
	});
	*/
	
	
	// 第二题
	$("li > a").parent().siblings().addClass("testLink");
	
})();
