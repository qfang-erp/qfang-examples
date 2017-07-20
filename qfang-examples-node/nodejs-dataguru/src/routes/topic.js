'use strict';

/**
* @author walle <liaozhicheng.cn@163.com>
*/

module.exports = function(done) {

  // 新增帖子
  $.router.post('/api/topic/add', $.isLogin, async function(req, res, next) {
    req.body.author = req.session.user._id;

    if('tags' in req.body) {
      req.body.tags = req.body.tags.split(",").map(v => v.trim()).filter(v => v);
    }

    const topic = await $.method('topic.add').call(req.body);

    res.json({success: true, topic: topic});
  });


  // 帖子列表（分页查询）
  $.router.post('/api/topic/list', async function(req, res, next) {
    if ('tags' in req.query) {
      req.query.tags = req.query.tags.split(',').map(v => v.trim()).filter(v => v);
    }

    let page = parseInt(req.query.page, 10);
    if (!(page > 1)) page = 1;
    req.query.limit = 10;
    req.query.skip = (page - 1) * req.query.limit;

    const list = await $.method('topic.list').call(req.query);

    const count = await $.method('topic.count').call(req.query);
    const pageSize = Math.ceil(count / req.query.limit);

    res.json({success: true, count: count, page: page, pageSize: pageSize, list: list});
  });


  // 根据ID查找帖子
  $.router.get('/api/topic/item/:topic_id', async function(req, res, next) {
    const topic = await $.method('topic.get').call({_id: req.params.topic_id});
    if(!topic)
      return next(new Error(`topic ${req.params.topic_id} does not exists.`));

    res.json({success: true, topic: topic});
  });


  // 更新帖子
  $.router.post('/api/topic/item/:topic_id', $.isLogin, $.isTopicAuthor, async function(req, res, next) {
    if('tags' in req.body)
      req.body.tags = req.body.tags.split(",").map(v => v.trim()).filter(v => v);

    req.body._id = req.params.topic_id;
    await $.method('topic.update').call(req.body);

    const topic = await $.method('topic.get').call({_id: req.params.topic_id});
    res.json({success: true, topic: topic});
  });


  // 删除帖子
  $.router.delete('/api/topic/item/:topic_id', $.isLogin, $.isTopicAuthor, async function(req, res, next) {
    const topic = await $.method('topic.delete').call({_id: req.params.topic_id});
    res.json({success: true, topic: topic});
  });


  // 添加评论
  $.router.post('/api/topic/item/:topic_id/comment/add', $.isLogin, async function(req, res, next) {
    req.body._id = req.params.topic_id;
    req.body.author = req.session.user._id;

    const comment = await $.method('topic.comment.add').call(req.body);
    res.json({success: true, comment: comment});
  });


  // 删除评论
  $.router.post('/api/topic/item/:topic_id/comment/delete', $.isLogin, async function(req, res, next) {
    req.body._id = req.params.topic_id;
    const query = {
      _id: req.params.topic_id,
      commentId: req.body.comment_id,
    };
    const comment = await $.method('topic.comment.get').call(query);
    console.log(comment)

    if (comment && comment.comments && comment.comments[0]) {
      const item = comment.comments[0];
      if (item.author.toString() === req.session.user._id.toString()) {
        await $.method('topic.comment.delete').call(query);
      } else {
        return next(new Error('no permission'));
      }
    } else {
      return next(new Error('comment does not exists'));
    }

    res.json({success: true, comment: comment.comments[0]});
  });

  done();
}
