'use strict';

/**
* @author walle <liaozhicheng.cn@163.com>
*/

import mongoose from 'mongoose';

module.exports = function(done) {

  const debug = $.debug('mongodb');
  debug('init mongodb connnection');

  const conn = mongoose.createConnection($.config.get('db.mongodb'));
  $.mongodb = conn;

  done();
}
