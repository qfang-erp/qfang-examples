'use strict';

 /**
 * @author: walle <liaozhicheng.cn@163.com>
 */

module.exports = function(set, get, has) {

  set('web.port', '3000');

  set('db.mongodb', 'mongodb://192.168.1.181:27017/nodejs-dataguru');

  set('web.serssion.secret', 'test');
}
