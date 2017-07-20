'use strict';

/**
 * @author: walle <liaozhicheng.cn@163.com>
 */
import path from 'path';
import ProjectCore from 'project-core';
import createDebug from 'debug';

const $ = global.$ = new ProjectCore();

// 加载配置文件
$.init.add((done) => {
  $.config.load(path.resolve(__dirname, 'config.js'));
  const env = process.env.NODE_ENV || null;
  if(env) {
    $.config.load(path.resolve(__dirname, '../config', env + '.js'));
  }
  $.env = env;
  done();
});

// 创建 debug
$.createDebug = function(name) {
  return createDebug('my:' + name)
};
const debug = $.createDebug('server');

// 初始化mongodb
$.init.load(path.resolve(__dirname, 'init', 'mongodb.js'));
// 加载 models
$.init.load(path.resolve(__dirname, 'models'));

// 初始化 express
$.init.load(path.resolve(__dirname, 'init', 'express.js'));
// 加载 route
$.init.load(path.resolve(__dirname, 'routes'));


// 初始化
$.init((err) => {
  if(err) {
    debug(err);
    // console.error(err);
    process.exit(-1);
  } else {
    debug('inited [env=%s]', $.env);
    // console.log('inited [env=%s]', $.env);
  }

  // const item = new $.model.User({
  //   name: 'test insert',
  //   password: '123456',
  //   nickname: '测试用户'
  // });
  // item.save(console.log);
});
