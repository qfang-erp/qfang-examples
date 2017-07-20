'use strict';

/**
* @author walle <liaozhicheng.cn@163.com>
*/

module.exports = function(done) {

  // 获取当前登录用户（session 中的用户）
  $.router.get('/api/login_user', async function(req, res, next) {
    res.json({user: req.session.user, token: req.session.logout_token});
  });

  // 用户登录
  $.router.post('/api/login', async function(req, res, next) {
    if(!req.body.password) {
      return next(new Error('missing password'));
    }

    const user = await $.method('user.get').call(req.body);
    if(!user) {
      return next(new Error('user does not exists.'));
    }

    if(!$.utils.validatePassword(req.body.password, user.password)) {
      return next(new Error('invalid password'));
    }

    req.session.user = user;
    req.session.logout_token = $.utils.randomString(20);

    res.json({success: true, token: req.session.logout_token});
  });

  // 退出
  $.router.get('/api/logout', async function(req, res, next) {
    if(req.session.logout_token && req.query.token !== req.session.logout_token) {
      return next(new Error('invalid token'));
    }

    delete req.session.user;
    delete req.session.logout_token;

    res.json({success: true})
  });

  // 注册
  $.router.post('/api/signup', async function(req, res, next) {
    const user = await $.method('user.add').call(req.body);
    res.json({success: true, user: user});
  });

  done();
}
