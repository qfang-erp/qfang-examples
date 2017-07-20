/*

我们前面两节课一直在强调javascript中的函数是对象？
那么什么是对象呢？

*/
/*
  Javascript中的对象其实是可变的键控集合（key collections）
  
  在Javascript中除了基本数据类型包括数字、字符串、布尔值和undefined外
  其它所有的值都是对象。比如数组、函数、甚至正则表达式也是对象
  
  怎么定义一个对象呢？
*/

//声明对象，最简单的方式是使用对象字面量进行声明，主要是{},里面包含零个或多个“键值”对，比如

var obj = {};//空对象
var person = {'first-name':'MA','second-name':'BOB'};

//对象的属性名可以包括空字符串在内的任何字符串。如果属性名是一个合法的JavaScript标识符且不是保留字
//则不强制要求用引号括住属性名。
//能否这样呢
var person = {first-name:'MA',second-name:'BOB'}; //不可以，-不是合法的
var person = {first_name:'MA',second_name:'BOB'};//可以,_连接的是合法的标识符

var first_name = '';

var obj = {'undefined':'test'};// 这个声明是否有效？


//对象的值，可以是从任意表达式获取，所以，属性的值可以是另一个对象，也可以是函数等
//对象是可嵌套的

//现在回过头来看数字、字符串、布尔值这些基本类型，它们貌似拥有一些属性,比如方法，比如
'test'.length;
(123).toString()

//那么这些类型是否就是对象呢？再来看下面的程序
Number.prototype.toString = function(){
	alert('hi,I\'m a number ');
};
(123).toString();//结果是什么？

//从这个简单例子可以看到，这些类型之所有有方法，主要还是因为有相应的对象来包装它们。
//当然，主要原因是这些类型的值是不可变的，对象是可变的。



/*
 那么怎么访问对象的属性值呢?
 可以通过.或者[]进行访问[]
 如果属性名是一个字符串，且是一个合法的JavaScript标识符且不是保留字，那么可以用.否则用
*/
var person = {first_name:'MA',second_name:'BOB'};
person.first_name;
person['first_name'];

//如果访问一个不存在的成员，不会报错，但返回undefined。
person.name ; //undefined

//可以使用||来填充默认值
var name = person.name ||'bob';

//如果尝试去undefined的成员属性中取值，则会报错
person.family.child;
//可以使用&&避免报错
person.family&&person.family.child;


//怎么更新对象的值？可以通过赋值语句进行更新，比如
person.first_name = 'yang';
person['second_name']='jian';

//如果属性已经存在对象中，则属性的值会被替换。如果不存在，则该属性会扩充到对象中，比如
person.name ='wow';


//复制还是引用?
var x = person;
x.first_name = 'test';
console.log(person.first_name);//test
//对象通过引用来传递，它们永远不会被复制

//原型
/*
 每个对象都连接到一个原型对象，并且可以从中继承属性。
 
 所有通过{}创建的对象都连接到Object.prototype。Object.prototype的原型是什么呢？第二课的时候说过，是null
 这体现了一个哲学思想，无中生有，万物生于空
*/


/*
  在javascript中，创建一个新对象时，可以选择某个对象作为它的原型，比如
*/

Object.create = function(o){
	var F = function(){};
	F.prototype = o ;
	return new F();
};

var anotherPerson = Object.create(person);
anotherPerson.first_name;//test
//对这个对象进行更新
anotherPerson.first_name ='hello';
person.name //显示的是什么？

//原型连接在更新时是不起作用的。当对某个对象做出改变时，不会触及该对象的原型
//所以person.name显示的还是test

//原型连接什么时候起作用呢？只有在检索值的时候才被用到
//如果尝试者去获取对象的某个属性值，但该对象没有此属性名，那么JAVASCRIPT会尝试着从原型对象中获取属性值。
//如果原型对象也没有该属性值则再从它的原型找，直到该过程最后达到终点Object.prototype。Object
//如果想要的属性完全不存在于原型链中，那么结果就是undefined。

person.age = 18;
anotherPerson.age//显示什么？

//原型关系是一种动态的关系。如果添加一个新的属性到原型中，该属性会立即对所有基于该原型创建的对象可见。


/*
  反射
  在javascript中，检查对像并确定对象有什么属性是很容易的事情，只要试着去检索该属性并验证取到的值
  可以使用typeof来确定属性的类型
*/

typeof person.age; //'number'
typeof person.name ; //'string'

//可以使用hasOwnProperty方法来判断对像是否含有某个属性，如果对象拥有独有的属性，则返回true
//这个方法不会去检查原型链
person.hasOwnProperty('name');
anotherPerson.hasOwnProperty('age');


/*
   枚举
   
   可以使用for in 语句来遍历一个对象的所有属性名。
*/
for(v in person){
	console.log(person[v]);
}
//该过程会列出所有的属性，包括函数和原型中的属性。可以使用上面所说的两个方法进行过滤

//属性名出现的顺序是不确定的，因此要对任何可能出现的顺序有所准备
//如果想要获取特定顺序的属性，则尽量避免使用for in 语句，可以创建一个数组
//在其中以正确的顺序包含属性名



/*
  怎么删除对象的属性呢？
  使用delete来删除对象的属性,如果对象包含属性，那么该属性就会被移除。
  该方法不会触及原型链中任何对象
*/

delete anotherPerson.age;
person.age ; //18

person.name ='1';
anotherPerson.name ='2';
delete anotherPerson.name;

anotherPerson.name //显示啥？
//删除对象的属性可能会让来自原型链中的属性暴露出来

/*
  使用对象可以减少全局变量的污染。比如
*/

var myApp = {};
//可以在此对象上定义变量和方法
myApp.person = {first_name:'MA',second_name:'BOB'};
myApp.showPerson = function(o){ console.log(o.first_name);};

//如上做法，只创建了一个唯一的全局变量，把全局性的资源都纳入到一个名称空间下。
//这样做最小化使用全局变量。减少组件间的冲突
//当然还有另外一种减少全局污染的方法，就是利用闭包来隐藏信息。


/*
  了解了什么是对象，接下来我们来看看JAVASCRIPT中的面向对象编程

*/


/* 
静态类型语言和动态类型语言
类型检查：前者在编译时已确定变量的类型，后者要到程序运行时，待变量被赋予某个值后，才会具有某种类型

优缺点：前者优点，是可以在编译时就能发现类型不匹配的错误，避免一些运行期间可能发生的错误
缺点是首先迫使程序员依照强契约来编写程序，为每个变量规定数据类型，其次类型声明会增加更多的代码

后者优点：编写的代码数量更少，看起来也更加简洁，程序员可以把更多的精力放到业务逻辑上面
缺点是无法保证变量的类型，从而程序在运行期间有可能发生跟类型相关的错误

*/

//动态类型语言对变量类型的宽容给实际编码带来很大的灵活性，事实上，在编程过程中，我们更关注一个对象能“干什么”，而不是“是什么”

//如果它走起路来像鸭子，叫起来也是鸭子，那么它就是鸭子。这就是鸭子类型

//在动态类型语言的面向对象设计中，鸭子类型的概念至关重要。
//利用这个概念，就能轻松地在动态类型语言中实现一个原则：面向接口编程，而不是面向实现编程。

var duck = {
	duckSinging: function(){
		console.log('嘎嘎嘎');
	}
};

var chicken = {
      duckSinging: function(){
		console.log('嘎嘎嘎');
	}	
};
var choir = [];

var joinChoir= function(animal){
	if(animal&typeof animal.duckSinging==='function'){
		choir.push(animal);
		console.log('恭喜加入合唱团')
	}
};
joinChoir(duck);
joinChoir(chicken);
//就像在工作中一样，领导只关注你能干什么，而不在乎你是什么人，从哪个学校毕业

//多态是面向对象的一个非常重要的思想
//什么是多态呢？我经常在面试中问这个问题，但得到的答案是类似方法重载之类的答案，很少有说得清楚的
//多态的实际含义：同一个操作作用于不同的对象上面，可以产生不同的解释和不同的执行结果。通俗的讲
//给不同的对象发送同一个消息时，这些对象会根据这个消息分别给出不同的反馈

var shout = function(animal){
	if(animal instanceof Duck){
		console.log('嘎嘎嘎');
	}else if(animal instanceof Chicken){
		console.log('咯咯咯');
	}
};
var Duck = function(){};
var Chicken = function(){};
shout(new Duck());
shout(new Chicken());

//这段代码体现了多态性。
//但想想这么写代码好吗？如果新增一个动物进来，比如狗，就得修改shout这个函数，而且如果添加的动物越来越多
//shout将会变成一个庞大的函数


//多态背后的思想，是将“做什么” 和“谁去做以及怎么做”分离开来，也就是将“不变的事物”和“可能改变的事物”分离开来。
//以上的例子，会叫是不变的，至于怎么叫发出什么声音是可变的。
//所以把可变的封装起来，不变的部分隔离开来，给予我们扩展程序的能力。所以改写上面的例子
var shout = function(animal){
	animal.shout();
};
var Duck = function(){};
Duck.prototype.shout = function(){
		console.log('嘎嘎嘎');
};

var Chicken = function(){};
Chicken.prototype.shout = function(){
		console.log('咯咯咯');
};
shout(new Duck());//嘎嘎嘎
shout(new Chicken()); //咯咯咯

//这样改写后，如果新增一个动物，就不必去修改shout方法，只需要增加一个对象就好，比如
var Dog = function(){};
Dog.prototype.shout = function(){
		console.log('汪汪汪');
};
shout(new Dog());//汪汪汪


//静态类型语言，比如JAVA是怎么实现多态的？

//Javascript的变量类型在运行期间是可变的。一个JAVASCRIPT对象，既可以表示Duck类型的对象
//又可以表示Chicken类型的对象，这意味着Javascript中的多态性是与生俱来的
//类型检查?消除类型之间的耦合关系？

//多态的作用，最根本的作用是通过把过程化的条件分支语句转化为对象的多态性，从而消除这些条件分支语句

//再举例说明，作业


/*
  面向对象另一个重要思想是，封装
  封装的目的是将信息隐藏。
*/

//封装数据
//在JAVASCRIPT,我们依赖变量的作用域来实现数据的分装特性，但只能实现private和public，比如我们之前所讲的闭包

var myObject = (function(){
	var name = 'seven'; //私有变量
	return {
		getName : function(){ //公有方法
			return name ;
		}
	}
})();
console.log(myObjec.getName());//seven
console.log(myObjec.name);//undefined

//封装实现：封装使得对象内部的变化对其它对象而言是透明的，也就是不可见的。对象对它的自己的行为负责
//其它对象或者用户都不关心它的内部实现。对象之间只通过暴露的API进行通信

//封装类型：封装类型是通过抽象类和接口来进行的。把对象的真正类型隐藏在抽象类或者接口之后
//相比对象的类型，客户更关心对象的行为
//JAVASCIPT对封装类型方面，没有能力，也没有必要


//封装变化
//把系统中稳定不变的部分和容易变化的部分分离开来，在系统的演变过程中，只需要替换那些容易变化的部分
//如果这部分已经封装好，替换起来相对容易



/*
基于原型的继承
有四个基本规则
1. 所有的数据都是对象
2. 要得到一个对象，不是通过实例化类，而是找到一个对像作为原型并克隆它
3. 对象会记住它的原型
4.如果对象无法响应某个请求，它会把这个请求委托给它自己的原型

*/





