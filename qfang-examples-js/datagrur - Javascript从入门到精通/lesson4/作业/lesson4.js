"use strict";

(function() {

    // 一开始我们选择的是GOOGLE地图，它提供的show的方法在页面上展示地图
    // 后面因为某些原因，要把GOOGLE地图改成百度地图，假如百度提供的接口也是show
    // 请大家根据这个场景，设计我们的调用程序，使得如果后续需要改地图API，尽可能付出较小的代价


    // -----------------
    var BaiduMapApi = {
        show: function () {
            console.log("baidu map show")
        }
    }

    var GoogleMapApi = {
        show: function () {
            console.log("google map show")
        },

        display: function () {
            console.log("google map display")
        }
    }
    // 假设以上代码就是客户提供给我们的 API


    // 定义一个 showMap 方法
    var showMap = function (map) {
        map.show();
    }

    // 自己定义一个 MyBaiduMap 类，并将这个类原型的 show 方法指向 API 的 show
    var MyBaiduMap = function () {
    };
    MyBaiduMap.prototype.show = BaiduMapApi.show;
    showMap(new MyBaiduMap());  // 正常使用 Baidu API 方式显示地图

    // 替换 Baidu API 为 Google API
    var MyGoogleMap = function () {
    };
    MyGoogleMap.prototype.show = GoogleMapApi.show;
    showMap(new MyGoogleMap());

    // 第二题
    MyGoogleMap.prototype.show = GoogleMapApi.display;
    showMap(new MyGoogleMap());

})();
