/*
JavaScrip设计得最出色的大概就是它的函数的实现，近乎于完美.
*/

/*

函数包含一组语句，是JavaScript的基础模块单元，用于代码复用，信息隐藏和组合调用
函数用于指定对象的行为，一般来说，所谓编程，就是将一组需求分解成一组函数与数据结构的技能

*/
/*
  函数对象可以通过函数字面量来创建，比如

*/
var add = function(x,y){
	 return  x+y;
};

add(1,2);
add()


function add(x,y){
	return x+y;
};


/*
函数字面量，包括四个部分
第一部分是保留字function
第二个部分是函数名，函数名可以被省略。函数可以用它的名字来递归地调用自己
此名字也可以被调试器和开发工具用来识别函数。
如果没有命名，比如上面这个例子，被称为匿名函数

*/

/*
函数的第三个部分，是包围在圆括号中的一组参数，多个参数用逗号分隔。
这些参数的名称将被定义为函数的变量，它们不像普通变量那样将被初始化为undefined
而是在该函数被调用时，初始化化为实际提供的参数的值

第四个部分是包围在花括号中的一组语句。这些语句是函数的主体，它们在函数被调用时执行

*/
var add = new Function('x','y','return x+y');

/*

 JavaScript中的函数就是对象
 对象是“名/值”对的集合，并拥有一个连到原型对象的隐藏连接
 对象字面量产生的对象连接到Object.prototype
 
 函数对象连接到Function.prototype(该原型对象本身连接到object.prototype)
 
 每个函数在创建的时候会附加两个隐藏属性:函数上下文和实现函数行为的代码
 
 函数对象本身有个prototype属性，这个属性是个普通对象
 
*/
//原型链  __proto__对象的内置属性，用于指向创建它的函数对象的原型对象prototype
var  f = function(){};
console.log(f.prototype);//Object {}
console.log(f.__proto__===Function.prototype); //true
console.log(Function.prototype.__proto__===Object.prototype); //true


/*

 因为函数是一个对象，所以它们可以像任何其它的值一样被使用。
 比如函数可以被保存在变量，对象，数组中
 也可以被当作参数传递给其他函数，函数也可以再返回函数
 同时，因为函数是对象，所以它拥有方法

 函数的与众不同之处在于它们可以被调用
*/


/*
调用一个函数会暂停当前函数的执行，传递控制权和参数给新函数。
除了声明时定义的形式参数，每个函数还接收两个附加的参数：this和arguments。
this的值取决与调用的模式


函数的调用有四种模式：方法调用模式，函数调用模式，构造器调用模式，和apply调用模式
这些模式在如何初始化关键参数this上存在差异
*/

/*
  使用调用运算符来调用函数，这是跟在任何产生一个函数值的表达时之后的一对圆括号，
  圆括号内可包含零个或多个逗号隔开的表达式。每个表达式产生一个参数值。
  每个参数值被赋予函数声明时定义的形式参数名。
*/
/*
  方法调用模式：
  
  当一个函数被保存为对象的一个属性时，我们称之为方法。
  当一个方法被调用时，this被绑定到该对象。
  如果调用表达式包含一个提取属性的动作，那么它就是被当作一个方法来调用

*/
var myobject = {
	num:0,
	increment:function(inc){
		this.num += typeof inc ==='number'?inc:1;
	},
	getNum :function(){
		return  this.num;
	}
};
myobject.increment();
console.log(myobject.getNum());

myobject.increment(5);
console.log(myobject.getNum());

/*
方法可以使用this访问自己所属的对象，所以能从对象中取值或对对象进行修改
this到对象的绑定发生在调用的时候
*/


/*

函数调用模式

当函数并非一个对象的属性时，它被当作一个函数来调用
这种情况下，this被绑定到全局对象
*/

myobject.printNum = function() {
	this.increment();
	var  print = function (){
		console.log(this.num);
	};
	print();
};
var num = 100;
myobject.printNum();
//大家想想，这种情况下，打印的结果是什么？


// 试验的结果是100，为什么呢？因为,print的调用是函数调用模式，this绑定的是全局对象
//理论上正确的设计是，内部函数的this应该被绑定到外部函数的this变量
// 现在内部函数的this被绑定大错误的值上，不能享有该方法对对象的访问权，有什么方法可以解决这个问题呢？
myobject.printNum = function() {
	var that = this;
	that.increment();
	var  print = function (){
		console.log(that.num);
	};
	print();
};
//现在大家可以猜下结果是什么？


/*
构造器调用模式

如果一个函数前面带上new来调用，那么将会创建一个连接到该函数的prototype成员的新对象
同时this会被绑定都这个新对象上

*/
var  Person = function(str){
	 this.name = str ;
};
Person.prototype.getName = function(){
	return this.name ;
}
var myPerson = new Person('Jack');

myPerson.getName();

//原型链  __proto__对象的内置属性，用于指向创建它的函数对象的原型对象prototype
console.log(myPerson.__proto__===Person.prototype);
console.log(Person.prototype.__proto__===Object.prototype);
console.log(Object.prototype.__proto__);

/*
一个函数，如果创建的目的就是希望结合 new前缀来使用，那么它称之为构造函数
一般是保存在以大写格式命名的变量里
如果调用构造函数时，没有加上new 会发生什么事情呢？结果无法预料

*/

/*
  Apply调用模式
  因为函数是对象，所以函数允许有方法
  
  apply方法可以构建一个参数数组传递给调用函数，允许我们选择this的值
  接收两个参数，第一个是要绑定this的值，第二个是参数数组
  
*/
var add = function(x,y){
	return  x+y;
};

var arr = [3,4];
var sum = add.apply(null,arr);

var otherObject = {
	name :'Jeny'
};
var name = Person.prototype.getName.apply(otherObject);
//这个例子，我们改变getName中this的值，this被绑定到了otherObject这个对象

/*
与APPLY相似作用的还有一个call

call传入的参数数量不固定，跟apply相同的是，第一个参数也是代表函数体内的this指向,
从第二个参数开始，每个参数被依次传入函数
*/
var mult = function(x,y,z){
	return x*y*z;
};
mult.call(null,3,4,5);  --60

//这节课，先了解call和apply的用法，下节课，我们在详细讲这两个方法的用途


/*
参数
当函数被调用时，会得到一个免费配送的参数，那就是arguments数组
函数可以通过此参数访问所有它被调用时传递给它的参数列表，包括那些没有被分配给函数声明时定义的形式参数的多余参数
*/

var sum = function(){
	var i ;
	var sum = 0 ;
	for(i=0;i<arguments.length;i++){
		sum += arguments[i];
	};
	console.log(typeof arguments);
	return sum ;
};
sum(1,2,3,3,3);

//值得注意的是，这个参数并非一个数组,只是一个类似数组的对象，拥有length属性，没有任何数组的方法

/*
返回
当一个函数被调用时，从第一个语句开始执行，并在遇到关闭函数体的}结束。
然后函数把控制权交还给调用该函数的程序

return 语句可用来使函数提前返回，当return被执行时，函数立即返回而不再执行余下的语句

一个函数总会返回一个值，如果没有指定返回值，则返回undefined

如果使用构造函数调用模式调用函数，且返回值不是一个对象，则返回this(该新对象) 
*/


/*
函数字面量可以出现在任意允许表达式出现的地方。
函数也可以被定义在其它函数中。一个内部函数除了可以访问自己的参数和变量，还能自由访问把它嵌套在其中的父函数的参数和变量

*/
var f = function(x,y){
	var max = function(){
		if (x>=y){
			return x 
		}else {
			return  y ;
		}
	};
	return max();
};
/*
  通过函数字面量创建的对象包含一个连接到外部上下文的连接，这个被成为闭包。
*/

//接下来我们来了解下什么是闭包，闭包是很难理解和征服的概念，我们先从变量的作用域说起

/*
变量的作用域，指的是变量的有效范围，最经常涉及的是函数中声明的变量作用域

当在函数中声明一个变量时，如果该变量前面没有带上关键字var，这个变量就会成为全局变量
另外一种情况是用var关键字在函数中声明变量，这时候的变量即是局部变量，只有在该函数
内部才能访问到这个变量，在函数外部访问不到
*/

var f = function(){
	var a = 100;
	alert(a);
};
console.log(a);

/*
  在JavaScript中，函数可以用来创造函数作用域
  在函数里面可以看到外面的变量，而在函数外面则无法看到函数里面的变量
  以下例子可以帮助理解变量的函数作用范围
*/
var a =1 ;
var f1 = function {
	var b =2 ;
	var f2 = function (){
		var c =3 ;
		alert(b); //2
		alert(a);//1
	}
	f2();
	alert(c); //报错
};
f1();
/*
  变量的生存周期
  
  对于全局变量来说，全局变量的生存周期是永久的，除非主动销毁这个全局变量
  
  而对于在函数内用var关键字声明的局部变量来说，当退出函数时，这些局部变量就失去了它们的价值
  它们会随着函数调用的结束和被销毁
*/
var f = function(){
	var a = 100;
	alert(a);
};
f();
//退出函数后，局部变量a将被销毁

//现在来看看如下代码

var func = function (){
	var x =1 ;
	return function(){
		x++;
		alert(x);
	};
};
var f = func();
f();//2
f();//3
/*
 从以上的例子，看出，当退出函数func后，局部变量x并没有消失，好像存活在某个地方
 执行f所在的语句时，f返回一个匿名函数的引用，它可以访问到func()被调用时产生的环境
 而x一直处在这个环境中，。
 
 既然局部变量所在的环境还能被外界所访问，就有了不被销毁的理由，这里就产生了一个闭包结构
 局部变量的生命看起来被延续了
*/

//闭包的作用
//大家可以看下这段代码，点击每个DIV，会发生什么事情？
<html>
   <body>
      <div>1</div>
	  <div>2</div>
	  <div>3</div>
	  <div>4</div>
	  <div>5</div>
	  <script>
	      var divs = document.getElementsByTagName('div');
		  for(var i = 0,len = divs.length;i<len;i++){
			   divs[i].onclick = function(){
				   alert(i);
			   };
		  };
	  </script>
   </body>
 </html>

 //无论点击哪个DIV，弹出的结果是5,想想为什么?
 
 //怎么样使得点击每个DIV都显示对应的数字0，1，2，3，4呢,再看如下例子
 <html>
   <body>
      <div>1</div>
	  <div>2</div>
	  <div>3</div>
	  <div>4</div>
	  <div>5</div>
	  <script>
	      var divs = document.getElementsByTagName('div');
		  for(var i = 0,len = divs.length;i<len;i++){
			  (function(i){
				  divs[i].onclick = function(){
						             alert(i);
					                };
			  })(i);
			 
		  };
	  </script>
   </body>
 </html>
//试下这段代码的效果，想想为什么？
//每一次循环，i都被封闭起来了，当事件函数顺着作用域链从内到外查找变量i时，会先找到被封闭在闭包环境下的i

//接下来再看看闭包的作用
//封装变量
//闭包可以帮助把一些不需要暴露在全局的变量封装成私有变量 

var mult = function (){
	var sum =1 ;
	for(var i=0;i<arguments.length;i++){
		sum =sum *arguments[i];
	};
	return sum ;
};
//这个函数接受一些number类型的参数，并返回这些参数的乘积
mult(3,4,5);//60
mult(3,4,5,6)//360

//如果每次传递同样的参数，都去计算一遍，是一种资源上的浪费，可以加入缓存机制来提高这个函数的性能

var cache = {};
var mult = function (){
	var arrgs = Array.prototype.join.call(arguments,',');
	if (cache[arrgs]){
		return cache[arrgs];
	};
	
	var sum =1 ;
	for(var i=0;i<arguments.length;i++){
		sum =sum *arguments[i];
	};
	return cache[arrgs]=sum ;
};
mult(1,2,3);//6
mult(1,2,3); // 6
/*
  以上例子中，cache这个变量仅仅在mult函数中被使用,暴露在全局作用域下
  既然是mult函数专用，干脆将这个变量封闭在mult函数内部，避免这个变量在其它地方被调用或者修改，引起错误
  
*/

var mult = (function (){
	var cache = {};
	return function(){
			var arrgs = Array.prototype.join.call(arguments,',');
			if (cache[arrgs]){
				return cache[arrgs];
			};
			
			var sum =1 ;
			for(var i=0;i<arguments.length;i++){
				sum =sum *arguments[i];
			};
			return cache[arrgs]=sum ;
	};
})();
mult(1,2,3);//6
mult(1,2,3); // 6

//还可以对上面的代码进行重构，把比较独立的代码提炼出来

var mult = (function (){
	var cache = {};
	var calcute = function (){
		var sum =1 ;
		for(var i=0;i<arguments.length;i++){
				sum =sum *arguments[i];
		};
		return sum ;
	};
	return function(){
			var arrgs = Array.prototype.join.call(arguments,',');
			if (cache[arrgs]){
				return cache[arrgs];
			};
			return cache[arrgs]=calcute.apply(null,arguments) ;
	};
})();
//如果这些独立小函数在其它地方没用用到，最好使用闭包把它们封闭起来


//延续局部变量的寿命，比如以下例子
var report = function (src){
	var img = new Image();
	img.src = src;
};
report('http://www.dataguru.cn/');
//这里的img是局部变量，函数调用完成，即被销毁，这时，请求可能还没有发起，导致该次请求丢失

var report = (function (){
	var imgs = [];
	return function(src){
		var img = new Image();
		imgs.push(img);
	    img.src = src;
	}
})();

//闭包和面向对象设计

var person = function(name){
	return {
		getName:function(){
			return name ;
		}
	};
};

var man = person('bob');
man.getName();
//name变成了man这个对象的私有成员

//用闭包实现命令模式
//先来看下使用面向对象的方式怎么实现命令模式
<html>
 <body>
   <button id ='execute' >click open</button>
   <button id ='undo' >click undo</button>
   
   <script >
         var Tv = {
			 open:function(){
				alert('open TV'); 
			 },
			 close:function(){
				alert('close TV'); 
			 }
		 };
		 var openTvCommand = function(receiver){
			 this.receiver = receiver;
		 };
		 openTvCommand.prototype.execute =function(){
			 this.receiver.open();
		 };
		 openTvCommand.prototype.undo =function(){
			 this.receiver.close();
		 };
		 var setCommand =function(command){
			 document.getElementById('execute').onclick = function(){
				 command.execute();
			 };
			 document.getElementById('undo').onclick = function(){
				 command.undo();
			 };
		 };
		 setCommand(new openTvCommand(Tv));	 
   </script>
 </body>
</html>
/*
命令模式的意图是把请求封装成对象，从而分离请求的发起者和执行直接的耦合关系。
在命令被执行前，可以预先在命令对象中植入命令的接收者

面向对象的版本，预先植入的命令的接受者被当成对象的属性保存起来
而在闭包版本中，命令接收者会被封装在闭包形成的环境中
*/
var Tv = {open:function(){
				alert('open TV'); 
			 },
		  close:function(){
				alert('close TV'); 
			 }
		 };
		 

var createCommand = function(receiver){
	    var execute =function(){
		 return receiver.open();
		};
		var undo =function(){
		   return receiver.close();
		};
		
		return {
			execute:execute,
			undo:undo
		};
};

var setCommand =function(command){
 document.getElementById('execute').onclick = function(){
	 command.execute();
 };
 document.getElementById('undo').onclick = function(){
	 command.undo();
 };
};
setCommand(createCommand(Tv));	 


//闭包和内存管理
//闭包是否会引起内存泄漏？

