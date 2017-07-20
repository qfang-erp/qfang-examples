/*
  大概接触过js的，没有不知道jquery的，jquery是一个优秀的js库，是一个轻量级的库
  兼容CSS3，更是兼容各种浏览器，核心理念是 write less ,do more
  以DOM为核心，一点一点的为给它们添加新的功能
  接下来的课程，我们一起来快速学习JQUERY，有可能的话，可以研究下其源代码。
*/

//jquery能做什么
/*
  jquery能做什么,从其核心特性而言，它满足以下需求
  
 1. 取得文档中的元素：选择器
 2. 修改页面的外观：css，提高跨浏览器的标准解决方案
 3. 改变文档的内容：使用少量的代码，就能改变文档的内容
 4. 响应用户的交互操作：
 5. 为页面添加动态效果
 6. 无需刷新页面从服务器获取信息：AJAX
 7. 简化常见的JavaScript任务：迭代，数组操作等
 
 Jquery的特性
 1.利用CSS的优势
 2.支持扩展：插件
 3. 抽象浏览器不一致性
 4.总是面向集合：隐式迭代
 5.将多重操作集于一行：连缀
 
*/

//在html页面引入jquery副本,如果有其它脚本使用的jquery，则必须把jquery的引入放在自定义脚本文件的<script>之前
<script src = 'jquery.js'></script>

//通过test1.html我们来了解下jquery是怎么使用的

/*
  通过使用$(document).ready()(3803ss)，预定在DOM加载完毕后调用某个函数，而不必等待页面中的图像加载。
  这种方法，提供了很好的跨浏览器的解决方案
  
  1. 尽可能使用浏览器原生的DOM就绪实现，并以window.onload事件处理程序作为后备
  2. 可以多次调用$(document).ready()并按照调用它们的顺序执行
  3. 即便是在浏览器事件发生之后把函数传给$(document).ready()，这些函数也会执行
  4.异步处理事件的预定，必要时脚本可以延迟执行
  5. 通过重复检查一个几乎与DOM同时可用的方法，在较早的版本中模拟DOM就绪事件
  
  $(document).ready()可接受定义好的函数的引用，也可以是匿名函数
*/


//选择元素

/*
  Jquery最伟大的特性之一就是它能够简化在DOM中选择元素的任务
  
  什么是DOM呢？即文档对象模型，充当了Javascript和网页之间的接口，以对象网络而非纯文本的形式来表现HTML的源代码
*/
<html>
<head >
 <title >Test jquery</title>
</head>
<body>
  <div>
     <p>hello,world</p>
	 <p>This is JavaScirpt training</p>
	 <p>welcome to my class</p>
  </div>
</body>
</html>
//DOM中的对象网络与家谱有几分类似
//祖先，子孙，同辈兄弟姐妹

//使用$()函数选择元素，该函数返回一个jQuery对象
//有三种基本的选择符：标签名，ID和类，这些选择符可以单独使用，也可以与其它选择符组合使用
$('p')
$('#some_id'')
$('.some_class')

//接下来通过一个例子，来看下CSS选择符、属性选择符、自定义选择符的应用，test2.html

//CSS选择符
//基于列表项的级别添加样式
$(document).ready(function(){
	$('#selected-plays > li').addClass('horizontal');
});
//其中子元素组合符>将horizontal类只添加到位于顶级的项中
//上面的选择符的含义是，查找ID为selected-plays的元素的子元素中所有的列表项li

//接下来再为例子中非顶级的li项添加方法，采用否定式伪类选择符来识别
$('#selected-plays li:not(.horizontal)').addClass('sub_level');

//属性选择符：通过HTML元素的属性选择元素。例如链接的title属性或图像的alt属性，比如选择带有所有属性alt的图像元素
$('imf[alt]')

//属性选择符选择一种从正则表达式中借鉴来的通配符语法，以^表示值在字符串的开始，以$表示值在字符串的结尾
//而且，用*表示要匹配的值可以出现在字符串的任意位置，用叹号！表示对值取反。

$('a[href^="mailto:"]').addClass('mailto');
$('a[href$=".html"]').addClass('htmlLink')
//属性选择符可以组合使用
$('a[href^="http"][href*="test"]').addClass('htmlLink')


//自定义选择符：支持自定义选择符，语法跟CSS中的伪类相同,选择符以冒号开头
$('div.horizontal:eq(1)')//获取集合中第二个元素 ,eq从零开始计数
//每隔一行为表格添加样式：常用的自定义选择符是:odd和:even
$('tr:even').addClass('alt');//为表格的奇数行添加类，计数从零开始

//:nth-child()选择符，相对于元素的父元素而非当前选择的所有元素来计算位置
//可以接受数值、odd或者even作为参数

$('tr:nth-child(odd)').addClass('alt');//为表格的奇数行添加类,计数从1开始


//基于上下文内容选择元素:contains()选择符,这个选择法区分大小写

$('td:contains(python)').addClass('alt');

//基于表单的选择符
/*
:input 
:button
:enabled
:disabled
:checked
:selected
*/
$('input[type="radio"]:checked')

//DOM遍历方法
//filter方法：可以对遍历的元素进行筛选：可以带自定义选择符，也可以接受函数参数，比如
$('tr').filter(':even').addClass('alt');
//带函数参数
$('a').filter(function(){
	return this.hostname&&this.hostname!=location.hostname;
}).addClass('external') //获取带有外部连接的<a>元素

//为特定单元格添加样式
.next()//筛选下一个最接近的同辈元素
.nextAll() //筛选该元素后面所有的同辈元素
.prev() // 与next()相反
.prevAll()//与nextAll()相反
.addBack() //筛选后又包含本身的元素
.parent() //获取父类元素
.children() //获取子类元素

//连缀：设计方法：每个jQuery方法都会返回一个jQuery对象


//事件：Javascript内置了一些对用户的交互和其他事件给予响应的方式。为了使页面具有动态性和响应性，就需要利用这种能力

//在页面加载后执行任务
//基于页面加载执行任务的一种主要的方式，就是前面使用
$(document).ready();
//原生的window.onload也能实现同样的效果，但两者触发操作的时间存在着微妙的差异
//当文档完全下载到浏览器中时就会触发window.onload事件,意味着页面上所有的元素都可以被操作
//但是使用$(document).ready()注册的事件处理程序，则会在DOM完全就绪并可以使用时调用
//这意味着所有元素对脚本而言是可以访问的，但是并不意味着所有关联文件都已经下载完毕。
//换句话说，当HTML下载完成并解析为DOM树后，代码就可以运行
//使用后面一种方法，可以基于一个页面执行多个脚本

//$(document).ready()简写
$(function(){});

//处理简单的事件：比如鼠标点击，表单被修改以及窗口改变大小等
//具体例子见test3.html，test3.js，通过例子我们来说明下，jquery怎么处理事件


//事件的传播
/*
  当页面上发生一个事件时，每个层次上DOM元素都有机会处理这个事件。
  从逻辑上看，任何事件都可能会有多个元素负责相应
  允许多个元素相应单击事件的一种策略叫做事件捕获
  在事件的捕获过程中，事件首先会交给最外层的元素，接着再交给更具体的元素
*/
<div>
<span>
<a>tt</a>
</span>
</div>

//另一种相反的策略叫做事件冒泡。当事件发生时，会首先发给最具体的元素，在这个元素
//获得响应机会之后，事件会向上冒泡到更一般的元素
//最终出台的DOM标准规定应该同时使用这两种策略。首先，事件要从一般元素到具体元素逐层捕获
//然后事件在通过冒泡返回DOM树的顶层。而事件处理程序可以注册到这个过程的任何一个阶段


//事件冒泡存在副作用，通过事件对象改变事件的旅程
$(document).ready(
function(){
	$('#switcher').click(function(event){ //这里的event就是事件对象，在事件被触发式，传递给这个参数
			$('#switcher button').toggleClass('hidden');
    });
}
);
//获取事件目标，事件处理程序里的event保存着事件对象,event.target属性保存着发生事件的目标元素
$(document).ready(
function(){
	$('#switcher').click(function(event){ //这里的event就是事件对象，在事件被触发式，传递给这个参数
			if(event.target==this){
				$('#switcher button').toggleClass('hidden');
			}
			
    });
}
);
//以上这段代码确保了，被单击的元素是DIV，而不是其他后代元素

//停止事件传播，事件对象提高一个.stopPropagation()方法，该方法可以阻止事件冒泡
$(document).ready(
function(){
	$('#switcher').click(function(event){ //这里的event就是事件对象，在事件被触发式，传递给这个参数
				$('#switcher button').toggleClass('hidden');			
    });
}
);
$(document).ready(
function(){
	$('#switcher-default').addClass('selected');
    $('#switcher button').click(function(event){
		    var bodyClass = this.id.split('-')[1];
		    $('body').removeClass().addClass(bodyClass);
			$('#switcher button').removeClass('selected');
			$(this).addClass('selected');
			event.stopPropagation();
	});
);
 //除了停止事件传播，还可以使用.preventDefault()来阻止默认操作
 //两者是相互独立的机制，任何一方发生时都可以终止另一方
 
 
 //事件委托：事件委托是利用冒泡的一种高级技术，通过事件委托，可以借助一个元素上的事件处理程序完成很多工作
 
$(document).ready(
function(){
    $('#switcher').click(function(event){
		  if($(event.target).is('button')){
			 var bodyClass = this.id.split('-')[1];
		    $('body').removeClass().addClass(bodyClass);
			$('#switcher button').removeClass('selected');
			$(event.target).addClass('selected');
			//event.stopPropagation();
		  }
		  
	});
);
//这种委托方式还有点问题
 $(document).ready(
function(){
	$('#switcher').click(function(event){ //这里的event就是事件对象，在事件被触发式，传递给这个参数
			if(!$(event.target).is('button')){
				$('#switcher button').toggleClass('hidden');
			}
			
    });
}
);
 //事件委托也有内置的功能
$(document).ready(
function(){
    $('#switcher').on('click','button',function(event){
			 var bodyClass = this.id.split('-')[1];
		    $('body').removeClass().addClass(bodyClass);
			$('#switcher button').removeClass('selected');
			$(event.target).addClass('selected');
			//event.stopPropagation();  
	});
);
 //最后，可以使用.off来移除事件处理程序
 
 //样式和动画，操作DOM，由于时间关系，不在这里细讲
 
 
 
//通过AJAX传递数据

/*
从根本上来说，一个Ajax解决方案中涉及如下技术
1.JavaScript：处理与用户及其他浏览器相关事件的交互，解释来自服务器的数据，并将其 呈现在页面上
2.XMLHttpRequest：这个对象可以在不中断其他浏览器任务的情况下向服务器发送请求。
3. 文本文件：服务器提供的XML、HTML或JSON格式的文本数据。 

*/
//基于请求加载数据：在所有炒作和粉饰的背后，Ajax只不过是一种无需刷新页面即可从服务器（或客户端）上加 载数据的手段。
//在页面上追加html，最简单的方式使用load方法，这个方法替我们完成了所有繁琐复杂的动作
//比如例子test.html


//操作Javascript对象
//取得JSON对象：Javascript对象表示法，由{}和里面的键值对组成,通过$.getJSON()解析传过来的字符串，将之解析成JSON对象
//使用法$.get()方法加载xml文档
//因为是异步，加载过来的数据，需要进一步的处理，需要一个回调函数进行处理

//向服务器传递数据
$.get(url,data,callback);
$.post(url,data,callback);

