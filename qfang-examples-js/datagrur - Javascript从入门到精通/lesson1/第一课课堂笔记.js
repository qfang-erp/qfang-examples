/*
1.空白
空白可能表现为被格式化的字符或者注释的形式。
空白通常没有任何意义
但有时必须用来分隔字符序列
比如

var与x之间的空格是不能移除的，否则就会合并成一个符号
*/

var x = 1;

//2.	注释
//Javascript提供两种注释形式：
//一种是用/*  */包围的块注释
//另一种是以为//开头的行注释
//注释的必要性：提高程序的可读性，没有注释的代码是糟糕的，有注释但错误的注释更糟糕
//最佳实践： 避免使用/* */注释，尽量用//注释代替它
//示例

/*
      var x = /a*/.match(s);
*/

//  var x = /a*/.match(s);


/*
3.	标识符
标识符是由一个字母开头，其后可选择性地加上一个或多个字母、数字或者下划线,_ $
标识符被用于语句、变量、参数、属性名、运算符、和标记
保留字*/
abstract  
boolean  break  byte 
case catch char  class  const continue  
debugger  default  delete  do double  
else  enum  export  extends 
false  final  finally  float  for  function  
goto  
if 
implements   import  in  instanceof  int  interface  
long  
native  new  null
package  private  protected  public 
return 
short  static  super  switch  synchronized
this  throw  throws  transient  true  try  typeof
var  volatile  void 
while  with


/*
注意1：JavaScript不允许使用保留字来命名变量和参数；
       而且不允许在对象字面量（直接量）或者用点运算符提取对象属性时，使用保留字作为对象的属性名
注意2： JavaScript区分大小写
*/
var myjava = 'hi';
var MYJAVA = 'world';



/*
4.	可选的分号
JavaScript使用分号将语句分隔开；如果语句各占一行，通常可以忽略语句间的分号

注意：JavaScript并不是在所有换行处都自动填补分号，只有在缺少分号无法正确解析代码的时候，才会填补分号
*/

return; 
true;
return true; 
var f = function(){
	return
	true
};

/*
5.	变量
变量的概念基本上和初中代数的方程变量是一致的，只是在计算机程序中，变量不仅可以是数字，还可以是任意数据类型。
在Javascript中使用一个变量之前，需要先声明，变量使用var关键字来声明
可以通过var来关键字来声明多个变量
可以将变量的初始化和变量声明合在一起
如果未在var声明语句中给变量指定初始值，默认初始值是undefined
*/

var 
var i;
var sum;
var i,j,k;
var a = 3;
typeof of a ;
a = 'a';
typeof of a ;

/*

6.	数字
JavaScript不区分整数和浮点数，统一用Number表示,在内部被表示为64位的浮点数，和JAVA的double类型一样，
但不同的是，它没有分离出整数类型，所以1和1.0的值相同
以下都是合法的Number类型
*/
123
2.333
-999
2.3e3
4.5e-3 // 4.5*10^(-3)
NaN
Infinity
/*
计算机由于使用二进制，所以，有时候用十六进制表示整数比较方便，十六进制用0x前缀和0-9，a-f表示，
例如：0xff00，0xa5b4c3d2，等等，它们和十进制表示的数值完全一样。
指数表示法：数值+e+数值，比如1e2表示 1*10^2
负数可以用运算符“—”加数字构成
NaN是一个数值，表示一个不能产生正常结果的运算结果。它不等于任何值，包括它自己。可使用isNaN(number)来检测NaN
可使用Math对象作用于数字，得到相应的结果
*/

var  x= 1;
var  y=1.0;
x==y; //true
typeof x == typeof y; //true
typeof NaN; // number
NaN==NaN; //false
'NaN'==NaN; //false
isNaN('NaN'); //true
Math.abs(-1);
3%2  // 
1+1 ;
2-3
3*3
/*

7.	字符串 
字符串直接量可以被包含在一对单引号或双引号中，可能包括0个或多个字符
\ 是转义字符
JavaScript采用的是UNICODE字符集，16位的，所有字符都是16位的
*/


'hello wold'
'hello world'
''
""
'T'
"T"

/*
转义字符\用来把那些正常情况下不被允许的字符插入到字符串中，比如
*/

'\\'  // \
'\'\''  // ''
'\”\”' // ""
'\n' //换行
'\t' //制表符

/*
\u 约定用来指定数字字符编码，比如
*/

'A'==='\u0041'

// 字符串有一个length属性

'hello'.length // 5 

/*
 字符串是不可变的，一旦字符串被创建，就永远无法改变它
 可以通过“+”来连接字符串，生成一个新的字符串
 两个字符串包含着完全相同的字符且字符顺序相同，则认为这两个字符串相同
*/

'hello' + ' world' ==='hello world' // true

//字符串有一些方法
"helloworld".charAt(1);// e
"helloworld".charCodeAt(1)   //101
"hello".concat(' ','world')  // hello world
"helloworld".indexOf('o',1) // 4
"helloworld".lastIndexOf('world',10) //5
"helloworld".match(/l/)  //["l"]
"helloworld".match(/l/g) // ["l", "l", "l"]
"helloworld".replace('w','g') ; //hellogorld
"helloworld".search(/l/)  // 2
"helloworld".search(/l?o/) //3
"helloworld".slice(2,5) // llo
"hello,world".split(',',2) ;  //["hello", "world"]
"hello,world".split(',',1) ;  //["hello"]
"helloworld".substring(2,5) //llo
/*
  数组 
  JavaScript的Array可以包含任意数据类型，并通过索引来访问每个元素。
  要取得Array的长度，直接访问length属性：
*/
var arr = [1, 2, 'test',['o',true]];
arr.length; // 4

//Array可以通过索引把对应的元素修改为新的值，因此，对Array的索引进行赋值会直接修改这个Array：
var arr = ['A', 'B', 'C'];
arr[1] = 99;
arr; // arr现在变为['A', 99, 'C']

//请注意，如果通过索引赋值时，索引超过了范围，同样会引起Array大小的变化
var arr = [1, 2, 3];
arr[5] = 'x';
arr; //[1, 2, 3, undefined, undefined, 'x']

//大多数其他编程语言不允许直接改变数组的大小，越界访问索引会报错。
//然而，JavaScript的Array却不会有任何错误。在编写代码时，不建议直接修改Array的大小，访问索引时要确保索引不会越界。
//数组的若干方法
var arr = 'helloworld'.split('');
arr.indexOf('h'); //0
arr.slice(2,5); //["l", "l", "o"]
arr.push(' who'); //["h", "e", "l", "l", "o", "w", "o", "r", "l", "d", " who"]
arr.pop();//" who" ["h", "e", "l", "l", "o", "w", "o", "r", "l", "d"]
arr.unshift('t','g');  //["t", "g", "h", "e", "l", "l", "o", "w", "o", "r", "l", "d"]
arr.shift(); //"t"   ["g", "h", "e", "l", "l", "o", "w", "o", "r", "l", "d"]
arr.sort();  //["d", "e", "g", "h", "l", "l", "l", "o", "o", "r", "w"]


/*
8.	表达式
什么是表达式？表达式是JavaScript中一个短语，解释器会将其计算出一个结果。
最简单的表达式是字面量值，比如字符串或数字、变量、内置的值，比如true、false、null、undefined、NaN、Infinity
以new 开头的调用表达式（对象创建表达式）
以delete开头的属性提取表达式

包在圆括号或中括号或花括号中的表达式（数组和对象初始化表达式）
以一个前置运算符作为前导的表达式
或者表达式后面跟着：
（1）	一个中置运算符与另一个表达式，比如算术表达式、逻辑表达式、赋值表达式
（2）	三元运算符?后面跟着一个表达式，然后接一个：再然后接第3个表达式
（3）	一个函数调用
（4）	一个属性提取表达式
*/
var obj = new Object();  
[1,2,3]
var obj = {'name':'ma','age':30,'getName':function(){return this.name;}};
obj.getName();
obj.x
++x
!x
x+y    
var t = x==y?'hello': 'world';
delete  obj.x;
function(){var x = 1 ;return x;};

/*
运算符
运算符可以将简单表达式组合成复杂表达式
运算符计算具有优先级

.  [ ] ( )	             提取属性与调用函数
delete new typeof + - !	  一元运算符
*  /  %	                  乘法、除法、求余
+  - 	                  加法/连接、减法
>=  <=   >  < 	          不等式运算符
===  !==	               等式运算符
&&	                      逻辑与
||	                      逻辑非
? :               	       三元运算符

排在越上面的运算符优先级越高，结合性最强
排在越下面的运算符优先级越低
圆括号可以用来改变正常情况下的优先级

*/
var list = [1,23,34,45,32];
var x=1;
list[x+2]
list[x]+2
1+2*2
(1+2)*2

/*
一元运算符中，typeof 运算符产生的值有numer,string,boolean,undifned,function,object
值得注意的是如果运算数是一个数组或null，那么结果是object

*/

/*
9.	语句
语句是JavaScript整句或者命令，以分号作为分隔符
一个编译单元包含一组可执行的语句
语句通常按照从上到下的顺序被执行，可通过条件语句、循环语句、强制跳转语句和函数来改变执行序列
代码块是包在一对花括号中的一组语句，代码块不会创建新的作用域。
当var语句被用在函数内部时，定义的是这个函数的私有变量
条件语句，使用if，else关键字

*/
if(expression){
	
}else{
	
}

/*
表达式的值为真时，执行跟在其后的代码块，否则，执行可选的else分支
什么样的值为假？false、null、undefined、空字符串’’、数字0、数字NaN
除了这些外其它所有的值都当作真包括true,字符串’false’，以及所有的对象
switch 语句执行一个多路分支
*/
switch(x){
	case 1: y=2;
	break;
	case 2:y=3;
	break;
	default:y++;
}
/*
switch把其表达式的值和指定的case条件进行匹配，表达式可能产生一个数字或者字符串。
当找到一个精确的匹配时，执行匹配的case从句中的语句。
如果没有找到任何匹配，则可执行可选的default语句
case从句后面可以跟上一个强制跳转语句，比如使用break跳出switch语句

*/

/*
循环语句有几种实现方式，最简单的是while语句
当表达式为真的时候，执行代码块中的语句
*/
while(expression){
	statements
}

/*
另一种结构更复杂的循环语句，是for语句
for语句有两种形式
第一种是for(初始化从句;条件从句;增量从句){statements}
*/
var sum = 0;
for(var i=0;i<10;i++){
	sum+=i;
}

/*
另外一种形式（for  in语句）会枚举一个对象的所有属性名，
在每次循环中,object的下一个属性名字串被赋值给variable
*/
var obj = {'name':'bob','age':20,'address':'shenzhen'};
for(v in obj){
	console.log(v); // 'name', 'age','address'
}

/*
还有一种循环语句，do{statements} while(expression)
do语句就像while语句，唯一的区别就是它在代码块执行之后而不是之前检测表达式的值。
意味着代码块至少要执行一次

*/

var i = 0;
do{
	 i++;
}while(i<100);

/*
跟很多语言一样，JavaScript也有异常处理机制
异常捕获语句： try{ } catch(name){ }
也可以主动抛出异常 ： throw expression;
throw语句抛出一个异常，如果throw语句子一个try代码块中，那么控制流会跳转到catch从句中。
如果throw语句在函数中，则该函数调用被放弃，控制流跳转到调用该函数的try语句的catch从句中


*/
try{ 
    var i =2*a;
}
catch(e){
   console.log(e.name+e.message)
}
//throw语句的表达式通常是一个对象字面量，包含一个name属性和message属性
try{
   var obj={'name':'exception','message':' hi'};
   throw obj;
}catch(ee){
	console.log(ee.name+ee.message)
}

/*
强制跳转语句： return expression 
return语句会导致从函数中提前返回。
可以指定要被返回的值，如果没有指定返回表达式，那么返回值是undefined。

*/
var f = function(){
	return;
	console.log('oh')
};
f();--undefined


/*
break语句：break;break label;
break语句会使程序退出一个循环语句或switch语句，
可以指定一个可选的标签，那么退出的是带该标签的语句

*/
testlable:for(var i=0;i<10;i++){  
  for (var j=0;j<10;j++){
	    if(j==2) break testlable; //单独一个break;
	}
}


/*
switch、while、for和do语句允许有一个可选的前置标签，配合break语句使用
值得注意的是，JavaScript不允许在break关键字和标签之间换行，也不允许return关键字和表达式之间换行
*/


/*
赋值语句：
一个expression语句可以给一个或多个变量或成员赋值，或者调用一个方法，或者从对象中删除一个属性。
运算符=被用于赋值，不要把它和恒等运算符===混淆起来
运算符+=可以用于加法运算或连接字符串

*/

var x ;
x = 2;

/*
函数：function name(parameter1,parameter2,..){statements}
函数定义可以有一个可选的名字，用于递归地调用自己，可以指定一个参数列表，这些参数就跟变量一样，在调用的时候，由传递的实际参数初始化
函数的主体包括变量定义和语句

*/

function add(x,y){
	return x+y;
}