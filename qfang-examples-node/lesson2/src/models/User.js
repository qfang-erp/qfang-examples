'use strict';

/**
 * @author: walle <liaozhicheng.cn@163.com>
 */

import mongoose from 'mongoose';

module.exports = function(done) {

  const Schema = mongoose.Schema;
  const ObjectId = Schema.ObjectId;

  const User = new Schema({
    name: {type: String, unique: true},
    password: {type: String},
    nickname: {type: String}
  });

  $.mongodb.model('User', User);
  $.model.User = $.mongodb.model('User');

  done();
}
