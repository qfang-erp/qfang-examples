'use strict';

/**
 * @author: walle <liaozhicheng.cn@163.com>
 */

import mongoose from 'mongoose';

module.exports = function(done) {

  const debug = $.createDebug('init:mongodb');
  debug('connect mongodb');

  const conn = mongoose.createConnection($.config.get('db.mongodb'));
  $.mongodb = conn;
  $.model = {};

  done();

}
