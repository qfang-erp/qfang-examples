'use strict';

/**
* @author walle <liaozhicheng.cn@163.com>
*/

module.exports = function(done) {

  // 判断用户是否登录
  $.isLogin = function(req, res, next) {
    if(!req.session.user || !req.session.user._id) {
      return next(new Error('还未登录'))
    }
    next();
  };

  // 校验当前用户是否为帖子的作者
  $.isTopicAuthor = async function(req, res, next) {
    const topic = await $.method('topic.get').call({_id: req.params.topic_id});
    if(!topic) return next(new Error(`topic ${req.params.topic_id} does not exists.`));

    req.topic = topic;
    if(topic.author._id.toString() === req.session.user._id.toString())
      return next();

    next(new Error('no permission'));
  };

  done();

}
