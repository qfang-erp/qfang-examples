'use strict';

/**
* @author walle <liaozhicheng.cn@163.com>
*/

import mongoose from 'mongoose';

module.exports = function(done) {

  const Schema = mongoose.Schema;
  const ObjectId = Schema.ObjectId;

    const User = new Schema({
    name: {type: String, unique: true},
    email: {type: String},
    password: {type: String},
    nickname: {type: String},
    about: {type: String}
  });

  $.mongodb.model('User', User);
  $.model.User = $.mongodb.model('User');

  done();
}
