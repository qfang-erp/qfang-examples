"use strict";

(function() {
    // 1. 这里有两个简单的正则表达式，大家分析下它们具体有什么作用？
    // /<[^>]*>|<\/[^>]*>/gm，/^\w+[@]\w+[.][\w.]+$/

    // 这个是用来匹配 html 页面中的 dom 节点标签
    var reg1 = /<[^>]*>|<\/[^>]*>/gm;
    var domA = "<a href='xxx.do'>query</a>";

    // 第一次执行时输出：["<a href='xxx.do'>"]
    // 第二次执行时输出: ["</a>"]
    // 第三次执行时输出：null
    // 第四次输出和第一次输出结果相同，后面的一直循环
    // 原因：这种写法的正则，exec 会依次向后匹配，每次只匹配一个
    reg1.exec(domA);

    // 如果需要一次将所有匹配的结果找出来可以使用
    domA.match(reg1);  // ["<a href='xxx.do'>", "</a>"]


    // 这个是用来匹配邮箱账号的正则表达式
    var reg2 = /^\w+[@]\w+[.][\w.]+$/;   // 这里为什么是[@]? 这个 [] 的作用
    var email = "walle@163.com";
    var email2 = "walle@163.com.cn"
    reg2.test(email);   // true
    reg2.test(email2);  // true

    // 习题二
    // 2.（可选）我们可以利用正则表达式做一个类似网络的爬虫的功能，当然这里只是要做个山寨新闻客户端，我们可以抓取其它新闻网站的首页上新闻的链接，
    // 根据某些关键词进行抓取相应的主题和链接，显示到我们的网页上。我们可以抓取不同新闻网页上的内容，比如网易、腾讯等，
    // 请大家设计出这个过程中需要用到的正则表达式。当然，有兴趣的可以把相应的功能设计出来！

    // 思路：只需要匹配 html 文档中所有的 a 标签即可，并且确保 a 标签存在 href 属性
    var html = '<span class="link_title"><a href="/zaifendou/article/details/18256265">Oracle10g:DBA</a></span>';
    var hrefRegx = /<a\s(href=['|"][\w|\/\.]*['|"]){1}>.*<\/a>/gmi;
    console.log(html.match(hrefRegx));

})();
