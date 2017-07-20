'use strict';

/**
* @author walle <liaozhicheng.cn@163.com>
*/

import validator from 'validator';

module.exports = function(done) {

  const rule = {
    _id: {required: true, validate: (v) => validator.isMongoId(String(v))},
    author: {required: true, validate: (v) => validator.isMongoId(String(v))},
    authorNoRequired: {validate: (v) => validator.isMongoId(String(v))},
    title: {required: true},
    content: {required: true},
    tags: {validator: (v) => Array.isArray(v)},
    skip: {valdate: (v) => v >= 0},
    limit: {valdate: (v) => v > 0},
  };

  // 添加帖子
  $.method('topic.add').check({
    author: {required: true, validate: (v) => validator.isMongoId(String(v))},
    title: {required: true},
    content: {required: true},
    tags: {validator: (v) => Array.isArray(v)}
  });
  $.method('topic.add').register(async function(params) {
    const topic = new $.model.Topic(params);
    topic.createAt = new Date();
    return topic.save();
  });

  // 获取帖子
  $.method('topic.get').check({
    _id: {required: true, validate: (v) => validator.isMongoId(String(v))}
  });
  $.method('topic.get').register(async function(params) {
    return $.model.Topic.findOne({_id: params._id}).populate({
      path: 'author',
      model: 'User',
      select: 'nickname about'
    }).populate({
      path: 'comments.author',
      model: 'User',
      select: 'nickname about'
    });
  });

  //  获取帖子列表
  $.method('topic.list').check({
    author: {validate: (v) => validator.isMongoId(String(v))},
    tags: {validator: (v) => Array.isArray(v)},
    skip: {valdate: (v) => v >= 0},
    limit: {valdate: (v) => v > 0}
  });
  $.method('topic.list').register(async function(params) {
    const query = {};
    if (params.author) query.author = params.author;
    if (params.tags) query.tags = {$all: params.tags};

    const list = $.model.Topic.find(query, {
      author: 1,
      title: 1,
      tags: 1,
      createdAt: 1,
      updatedAt: 1,
      lastCommentedAt: 1,
      pageView: 1,
    }).populate({
      path: 'author',
      model: 'User',
      select: 'nickname about'
    }).populate({
      path: 'comments.author',
      model: 'User',
      select: 'nickname about'
    });
    if (params.skip) list.skip(Number(params.skip));
    if (params.limit) list.limit(Number(params.limit));

    return list;
  });

  // 获取帖子总数量
  $.method('topic.count').check({
    author: {validate: (v) => validator.isMongoId(String(v))},
    tags: {validator: (v) => Array.isArray(v)},
  });
  $.method('topic.count').register(async function(params) {
    const query = {};
    if(params.author) query.authro = params.author;
    if(params.tags) query.tags = {$all: params.tags};

    return $.model.Topic.count(query);
  });

  // 更新帖子
  $.method('topic.update').check({
    _id: {required: true, validate: (v) => validator.isMongoId(String(v))},
    tags: {validate: (v) => Array.isArray(v)}
  });
  $.method('topic.update').register(async function(params) {
    const update = {};
    if(params.title) update.title = params.title;
    if(params.content) update.content = params.content;
    if(params.tags) update.tags = params.tags;
    update.updateAt = new Date();

    return $.model.Topic.update({_id: params._id}, {$set: update});
  });

  // 删除帖子
  $.method('topic.delete').check({
    _id: {required: true, validate: (v) => validator.isMongoId(String(v))},
  });
  $.method('topic.delete').register(async function (params) {
    return $.model.Topic.remove({_id: params._id});
  });

  // 添加评论
  $.method('topic.comment.add').check({
    _id: {required: true, validate: (v) => validator.isMongoId(String (v))},
    author: {required: true, validate: (v) => validator.isMongoId(String (v))},
    content: {required: true}
  });
  $.method('topic.comment.add').register(async function(params) {
    const topicId = params._id;
    const topic = await $.method('topic.get').call({_id: topicId});
    if(!topic)
      throw new Error(`topic can not find, topic_id : ${topicId}`);

    const comment = {
      author: params.author,
      content: params.content,
      createAt: new Date()
    }

    const ret = $.model.Topic.update({_id: topicId}, {
      $push: {
        comments: comment
      }
    });
    return ret;
  });

  $.method('topic.comment.get').check({

  })

  // 获取评论
  $.method('topic.comment.get').check({
    _id: {required: true, validate: (v) => validator.isMongoId(String (v))},
    commentId: {required: true, validate: (v) => validator.isMongoId(String (v))}
  });
  $.method('topic.comment.get').register(async function (params) {
    console.log(params)
    return $.model.Topic.findOne({
      _id: params._id,
      'comments._id': params.commentId
    }, {
      'comments.$': 1,
    }).populate({
      path: 'author',
      model: 'User',
      select: 'nickname about',
    });
  });

  // 删除评论
  $.method('topic.comment.delete').check({
    _id: {required: true, validate: (v) => validator.isMongoId(String (v))},
    commentId: {required: true, validate: (v) => validator.isMongoId(String (v))}
  })
  $.method('topic.comment.delete').register(async function(params) {
    const topicId = params._id;
    const commentId = params.commentId;

    return $.model.Topic.update({_id: topicId}, {
      $pull: {
        comments : {_id: commentId}
      }
    })
  });

  done();
}
