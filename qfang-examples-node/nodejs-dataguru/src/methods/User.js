'use strict';

/**
* @author walle <liaozhicheng.cn@163.com>
*/

import validator from 'validator';

module.exports = function(done) {

  const checkRule = {
    name: {required: true, validate: (v) => validator.isLength(v, {min: 4, max: 20}) && /^[a-zA-Z]/.test(v)},
    email: {required: true, validate: (v) => validator.isEmail(v)},
    password: {required: true, validate: (v) => validator.isLength(v, {min:6, max: 20})},
  }

  $.method('user.add').check(checkRule);

  $.method('user.add').register(async function(params) {
    params.name = params.name.toLowerCase();

    {
      const user = await $.method('user.get').call({name: params.name});
      if(user)
        throw new Error(`user ${params.name} already exists.`);
    }

    params.password = $.utils.encryptPassword(params.password.toString());
    const user = new $.model.User(params);
    return user.save();
  });

  $.method('user.get').register(async function(params) {
    console.log(params);

    const query = {};
    if(params._id) {
      query._id = params._id;
    } else if(params.name) {
      query.name = params.name;
    } else if(params.email) {
      query.email = params.email;
    } else {
      throw new Error('missing query parameter...');
    }

    return $.model.User.findOne(query);
  });

  $.method('user.update').check(checkRule);
  $.method('user.update').register(async function(params) {
    const user = await $.method('user.get').call(params);
    if(!user) {
      throw new Error('user does note exists.');
    }

    const update = {};
    if(params.name && user.name !== params.name) update.name = params.name;
    if(params.email && user.email !== params.email) update.email = params.email;
    if(params.password && user.password !== params.password) update.password = $.utils.encryptPassword(params.password);
    if(params.nickname && user.nickname !== params.nickname) update.nickname = params.nickname;
    if(params.about && user.about !== params.about) update.about = params.about;

    return $.model.User.update({_id: user._id}, {$set: update});
  });

  done();
}
