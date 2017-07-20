"use strict";

(function() {

	var printSymbol = function(n) {
		for(var i = 0; i < n; i++) {
			var str = "";
			for(var j = n; j > 0; j--) {
				if(i + 1 >= j) { // 第一行有 n-1 个空格开头，第二行有 n-2 个空格开头，以此类推
					str += "*";
				}
				str += " ";
			}
			console.log(str + "\t\n");
		}
	}
	
	printSymbol(5);
	//printSymbol(10);

	console.log("========== 第二题 ===========");
	
	String.prototype.charsNum = function() {
		var t = this, charNum = {};
		for(var i = 0; i < t.length; i++) {
			var c = t.charAt(i);
			charNum[c] = (charNum[c] || 0) + 1;
		}
		return charNum;
	}
	
	var charsNum = "helloworldhello".charsNum();
	console.log(charsNum)
	
	var maxCount = 0, chars = "";
	for(var c in charsNum) {
		if(charsNum[c] > maxCount) {
			maxCount = charsNum[c];
			chars = c;
		}
	}
	console.log("出现最多的字符为：" + chars + "，出现次数：" + maxCount);
})();