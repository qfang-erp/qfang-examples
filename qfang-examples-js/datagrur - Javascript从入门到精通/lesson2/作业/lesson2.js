"use strict";

(function() {

  function testArgs() {
    console.log(typeof arguments);  // object

    console.log(Array.prototype.slice.apply(arguments, [0, 2]));  // ["c", "b"]
    console.log(Array.prototype.sort.apply(arguments));  // ["a", "b", "c"]
  }

  testArgs('c', 'b', 'a')

})();
