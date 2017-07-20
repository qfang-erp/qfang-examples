package com.qfang.examples.zeroc.ice.client;

import com.jaf.examples.demo.MyServicePrx;
import com.jaf.examples.demo.MyServicePrxHelper;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年7月17日
 * @since 1.0
 */
public class MyClient {
	
	public static void main(String[] args) {
		int status = 0;
		Ice.Communicator ic = null;
		try {
			// 初始化通讯器
			ic = Ice.Util.initialize(args);
			// 通过远程服务单元名称，网络协议，IP及端口，构造一个 Proxy 对象
			Ice.ObjectPrx base = ic.stringToProxy("MyService:default -p 20000");
			// 向下转型，将上面获取的 base 对象向下转型为 MyService 接口对象，获取 MyService 的远程代理对象
			MyServicePrx myService = MyServicePrxHelper.uncheckedCast(base);
			if(myService == null)
				throw new Error("Invalid proxy");
			
			// 调用服务方法
			String result = myService.hello();
			System.out.println(result);
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
