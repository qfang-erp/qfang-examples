'use strict';

/**
* @author walle <liaozhicheng.cn@163.com>
*/

$.method('user.add').call({
  name: 'hello123',
  email: 'hello123@qq.com',
  password: '123456',
  nickname: '测试新增',
  about: 'this is about : hello123'
}, console.log);
