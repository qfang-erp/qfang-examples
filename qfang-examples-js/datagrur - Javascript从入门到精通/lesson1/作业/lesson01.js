"use strict";

(function() {

	var printSymbol = function(n) {
		for(var i = 0; i < n; i++) {
			var str = "";
			for(var j = n; j > 0; j--) {
				if(i + 1 >= j) { // ��һ���� n-1 ���ո�ͷ���ڶ����� n-2 ���ո�ͷ���Դ�����
					str += "*";
				}
				str += " ";
			}
			console.log(str + "\t\n");
		}
	}
	
	printSymbol(5);
	//printSymbol(10);

	console.log("========== �ڶ��� ===========");
	
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
	console.log("���������ַ�Ϊ��" + chars + "�����ִ�����" + maxCount);
})();