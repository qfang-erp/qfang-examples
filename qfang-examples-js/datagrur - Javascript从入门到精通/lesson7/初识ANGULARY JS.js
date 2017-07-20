//初识ANGULARY JS 

//上节课对AJAX讲得太少，认识ANGULARY JS之前，我们先来重新认识下AJAX
/*
   AJAX 是  Asynchronous JavaScript and XML的缩写，异步的Javascript和xml
   
   这不是一门新的编程语言，而是一种使用现有标准的新方法
   
   通过在后台与服务器进行少量数据交换，AJAX 可以使网页实现异步更新
   
   在动态web应用中，广泛使用到这门技术，实现局部刷新的效果，比如新浪微博，人人网，百度和google搜索提示等等
   
   ajax的原理；
   XMLHttpRequest对象是AJAX的基础
   
   
*/
var xmlhttp;
if (window.XMLHttpRequest){
    xmlhttp=new XMLHttpRequest();
 }
else{
     xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
}

//XMLHttpRequest通过open 和send方法，向服务器发送请求
open(method,url,async);//method：请求的类型：post或者get,url:请求的文件地址,async：true（异步）或 false（同步）
send(string)//将请求发送给服务器，string在请求类型是post的情况下传递

//发送请求前，还可以模拟http请求，设置http头部
setRequestHeader(header,value)//header: 规定头的名称，比如content-type，value: 规定头的值Content-type

//如果服务器有相应，使用 responseText和responseXML来接收来自服务器的数据
//responseText:获得字符串形式的响应数据
//responseXML:获得 XML 形式的响应数据

//XMLHttpRequest对象有个readyState的状态，这个状态从0到4发生变化
/*
0: 请求未初始化
1: 服务器连接已建立
2: 请求已接收
3: 请求处理中
4: 请求已完成，且响应已就绪
*/

//当状态变化时，我们可以每个状态都指定一定的行为，使用如下方法

onreadystatechange//	存储函数（或函数名），每当 readyState 属性改变时，就会调用该函数,回调函数


//我们现在来构建一个ajax调用，应该怎么处理呢

function myAjax(url)
{
var xmlhttp;

//获取XMLHttpRequest对象，需要兼容不同的浏览器
if (window.XMLHttpRequest){
  xmlhttp=new XMLHttpRequest();
}
else{// code for IE6, IE5
  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
}
//设置回调函数，当异步调用成功之后，做些后续处理
xmlhttp.onreadystatechange=function()
  {
  if (xmlhttp.readyState==4 && xmlhttp.status==200)
    {
        //do somethint 
    }
  }
 //向服务器发送请求
xmlhttp.open("GET",url,true);
xmlhttp.send();
};

//如果我们使用ajax都按照上面的来，麻烦而且不容易维护，所以可以把相同的地方进行抽象封装
//jquery就帮我们做了所有的事情，我们不需要关注ajax相应对象是怎么生成的，怎么向服务器发送请求的，我们只要准备好数据就好了
//我们可以使用以下方法来使用ajax
load('',data,callback);
$.get(url,data,callback);//封装get的请求
$.post(url,data,callback);//封装POST的请求
$.ajax({key:value...})//原始的AJAX方法
/*

async	布尔值，表示请求是否异步处理。默认是 true。
beforeSend(xhr)	发送请求前运行的函数。
cache	布尔值，表示浏览器是否缓存被请求页面。默认是 true。
complete(xhr,status)	请求完成时运行的函数（在请求成功或失败之后均调用，即在 success 和 error 函数之后）。
contentType	发送数据到服务器时所使用的内容类型。默认是："application/x-www-form-urlencoded"。
context	为所有 AJAX 相关的回调函数规定 "this" 值。
data	规定要发送到服务器的数据。
dataFilter(data,type)	用于处理 XMLHttpRequest 原始响应数据的函数。
dataType	预期的服务器响应的数据类型。
error(xhr,status,error)	如果请求失败要运行的函数。
global	布尔值，规定是否为请求触发全局 AJAX 事件处理程序。默认是 true。
ifModified	布尔值，规定是否仅在最后一次请求以来响应发生改变时才请求成功。默认是 false。
jsonp	在一个 jsonp 中重写回调函数的字符串。
jsonpCallback	在一个 jsonp 中规定回调函数的名称。
password	规定在 HTTP 访问认证请求中使用的密码。
processData	布尔值，规定通过请求发送的数据是否转换为查询字符串。默认是 true。
scriptCharset	规定请求的字符集。
success(result,status,xhr)	当请求成功时运行的函数。
timeout	设置本地的请求超时时间（以毫秒计）。
traditional	布尔值，规定是否使用参数序列化的传统样式。
type	规定请求的类型（GET 或 POST）。
url	规定发送请求的 URL。默认是当前页面。
username	规定在 HTTP 访问认证请求中使用的用户名。
xhr	用于创建 XMLHttpRequest 对象的函数。
*/




//Angular团队致力于减轻开发人员在开发AJAX应用过程中的痛苦
//使用Angular 框架，可以帮助我们在设计方面做出抉择
//让应用从一开始就易于创建和理解
//同时，当应用变得很大的时候，还能持续做出正确的选择，让我们的应用易于测试、扩展和维护


//示例

//学习Angular前，我们先来理解一些概念

/*

  客户端模版
  
   多页面WEB应用会在服务器端创建HTML，把HTML和数据装配并混合起来，然后再把生成的页面发送都浏览器中。
   ANGULAR在这方面的处理方式不同，在ANGULAR中，模版和数据都会都会发送到浏览器中，然后在客户端进行装配.
   如此一来，服务器的角色就变成了仅仅为这些模板提供一些静态的资源，然后为这些模板提供需要的正确数据

*/
/*

  Model View Controller(MVC)
  
  MVC的核心理念: 把管理数据的代码(model)、应用逻辑代码（controller）以及向用户展示数据的代码（view）清晰地分离开
  
  视图会从模型中获取数据，然后展示给用户。当用户通过鼠标点击或者键盘输入与应用进行交互的时候，控制器将会做出响应并修改
  模型中的数据。最后模型会通知视图数据已经发生了变更，这样视图就可以刷新其中显示的内容。
  
  
  在ANGULAR应用中，视图就是DOM，控制器就是JAVASCRIPT类，而模型数据则被存储在对象的属性中

*/
/*
  数据绑定
  
  以前开发Php的时候，怎么创建用户界面的呢，把HTML字符串和数据混合在一起，然后再发送给用户并显示
  
  Jquery在客户端继承了同样的模型，但可以单独刷新DOM中的局部内容，而不是刷新整个页面
  在jquery中会把HTML模版字符串和数据混合起来，然后把得到的结果插入到DOM中我们所期待的位置
  插入的方式是把结果设置给一个占位符元素的innerHtml属性
  
  这种方式不错，但如果想要把最新的数据插到UI中，或者根用户输入来修改数据的时候，就需要做很多及其繁琐的工作
  来保证数据的状态是正确的。
  
  数据绑定是一种编程风格，可以仅仅声明UI中的某个部分需要映射到某个JAVASCRIPT属性，然后让它们自己去同步

*/
//看示例

/*

  如果需要一个对象，应该如何处理？自己建一个？从全局注册表里获取？还是衣来伸手，饭来张口
  
  
  依赖注入
  
  在前面的例子里，进行数据绑定的$scope对象会自动传递给我们；我们并不需要调用任何函数去创建这个对象
  。只要把$scope对象放在controller的构造函数中，就可以获取它了
  
  当然,$scope并不是唯一可以获取的东西，如果想把数据绑定到浏览器中的URL地址，可以使用$location对象
  只要把$location对象放到我们的构造函数中即可
  
  这种效果是通过Angular的依赖注入机制实现的。
  
  
  通过JAVASCRIPT实现依赖注入的原理，很简单
  核心技术就是Function对象的toString(),它的返回值是函数的源码
  ，获取了函数源码，然后对函数的声明进行解析
  1.使用正则表达式匹配的方式拿到这个函数的参数列表

*/

var giveMe = function(config) {};
var registry = {};
var inject = function(func, thisForFunc) {
    // 获取源码
    var source = func.toString();
    // 用正则表达式解析源码
    var matcher = source.match(/^[^\(]*\(\s*([^\)]*)\)/m);
    // 解析结果是各个参数的名称
    var objectIds = matcher[1].split(',');
    // 查阅出相应的对象，放到数组中准备作为参数传过去
    var objects = [];
    for (var i = 0; i < objectIds.length; ++i)
        objects.push(registry[objectIds[i]]);
    // 调用这个函数，并且把参数传过去
    func.apply(thisForFunc || func, objects)
};

inject(giveMe);

//课程的最后，我们通过一个例子来看下ANGULAR JS的更多特性

