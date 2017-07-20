'use strict';

 /**
 * @author: walle <liaozhicheng.cn@163.com>
 */

import path from 'path';
import ProjectCore from 'project-core';
import debug from 'debug';

const $ = global.$ = new ProjectCore();

// 创建 debug
$.debug = function(name) {
  return debug('nodejsDataguru:' + name);
}

// 初始化 model 模块
$.model = {};

// 加载配置文件
$.init.add((done) => {
  $.config.load(path.resolve(__dirname, 'config.js'));
  const env = process.env.NODE_ENV || null;
  if(env) {
    try {
      $.config.load(path.resolve(__dirname, '../config', env.trim() + ".js"));
    } catch(err) {
      console.log(err.stack);
    }
  }
  $.env = env;
  done();
});



const serverDebug = $.debug('server');

// 初始化 mongodb
$.init.load(path.resolve(__dirname, 'init', 'mongodb.js'));
// 加载 models
$.init.load(path.resolve(__dirname, 'models'));

// 加载 methods
$.init.load(path.resolve(__dirname, 'methods'));

// 初始化 express
$.init.load(path.resolve(__dirname, 'init', 'express.js'));
// 初始化中间件
$.init.load(path.resolve(__dirname, 'middlewares'));
// 加载 route 模块
$.init.load(path.resolve(__dirname, 'routes'));

// 初始化
$.init((err) => {
  if(err) {
    serverDebug(err);
    process.exit(-1);
  } else {
    serverDebug('inited [evn=%s]', $.env);
  }

});
