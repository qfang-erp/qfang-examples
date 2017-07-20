"use strict";

(function() {

  // 第一题
  // 因为 chrome 已经不支持 showModalDialog 这个方法，所以第一题使用点击按钮弹出同一个随机数来代替弹出单例的弹框
  document.getElementById('loginBtn').onclick =  function() {
    var dialog = {};  // 利用闭包做一个缓存，每次点击获取的都是第一次返回的随机数
    return function() {
      if(!dialog.value)
        dialog.value = Math.random(10);
      alert(dialog.value)
    }
  }();

  // 第二题
  // 扩展 Array 的 reduce 方法，该方法接收一个函数作为参数，并对数组中的每个元素应用该函数
  Array.prototype.reduce = function(fun) {
    var len = this.length, seed = this[0], result = seed;
    for(var i = 0; i < len - 1; i++) {
      result = fun(result, this[i+1]);
    }
    return result;
  }

  // 对数组中的元素求和
  var sum = [1,2,3,4].reduce(function(a, b) {
    return a + b;
  });
  console.log(sum);  // 10

  // 合并数组，数组中的每个元素都有可能是数组，并且可能是多维数组
  var flatArray = [[1,2],[3,4,[5,[6]]],7].reduce(function(a, b) {
    var arr = [];
    var flat = function(obj) {
      if(Object.prototype.toString.call(obj) === "[object Array]") {
        for(var i = 0; i < obj.length; i++) {
          flat(obj[i]);
        }
      } else {
        arr.push(obj);
      }
    }
    flat(a);
    flat(b);

    return arr;
  });
  console.log(flatArray)  // 1,2,3,4,5,6,7

})();
