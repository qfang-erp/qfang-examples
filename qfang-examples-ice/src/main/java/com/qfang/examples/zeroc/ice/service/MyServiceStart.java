package com.qfang.examples.zeroc.ice.service;


/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年7月17日
 * @since 1.0
 */
public class MyServiceStart {
	
	public static void main(String[] args) {
		int status = 0;
		Ice.Communicator ic = null;
		try {
			// 初始化 Communicator 对象，args 中可以传一些初始化参数，如连接超时，初始化客户端连接池数量等
			ic = Ice.Util.initialize(args);
			// 创建名为 MyServiceAdapter 的 ObjectAdapter，使用缺省的通讯协议（TCP/IP，端口为 20000）
			Ice.ObjectAdapter adapter = ic.createObjectAdapterWithEndpoints("MyServiceAdapter", "default -p 20000");
			// 实例化一个 MyService 服务对象（Servant）
			MyServiceImpl servant = new MyServiceImpl();
			// 将 Servant 增加到 ObjectAdapter 中，并将 Servant 关联到 ID 为 MyService 的 Ice Object
			adapter.add(servant, Ice.Util.stringToIdentity("MyService"));
			// 激活 ObjectAdapter
			adapter.activate();
			
			// 等待，并且一直保持监听
			System.out.println("service started ..");
			ic.waitForShutdown();
		} catch (Exception e) {
			e.printStackTrace();
			status = 1;
		} finally {
			if(ic != null) {
				ic.destroy();
			}
		}
		
		System.exit(status);
	}
	
}
