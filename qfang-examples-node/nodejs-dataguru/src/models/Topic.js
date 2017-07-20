'use strict';

/**
* @author walle <liaozhicheng.cn@163.com>
*/

import mongoose from 'mongoose';

module.exports = function(done) {
  const Schema = mongoose.Schema;
  const ObjectId = Schema.ObjectId;

  const Topic = new Schema({
    author: {type: ObjectId, index: true, ref: 'User'},
    title: {type: String, trim: true},
    content: {type: String},
    tags: [{type: String, index: true}],
    createdAt: {type: Date, index: true},
    updatedAt: {type: Date, index: true},
    lastCommentAt: {type: Date, index: true},
    comments: [{
      author: {type: ObjectId, ref: 'User'},
      content: String,
      createAt: Date
    }],
    pageView: {type: Number}
  });

  $.mongodb.model('Topic', Topic);
  $.model.Topic = $.mongodb.model('Topic');

  done();
}
