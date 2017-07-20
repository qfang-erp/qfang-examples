package com.qfang.examples.concurrent.part3;

/**
 * 关于子线程和主线程已经JVM退出时机的示例
 *
 * @author liaozhicheng.cn@163.com
 */
public class ThreadExitTest {

    public static void main(String[] args) {
        // 先输出 1，再输出 2，然后整个 JVM 退出
        // 整个 JVM 正常退出的条件是需要等待所有非守护进程都运行完成才会退出
        // 如果 t.setDaemon(true); 打开这个的注释，那么输出 1，之后整个 JVM 就好退出，不会等待 2 的输出
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 2000; i++) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("child thread exits....");  // 2
            }
        });
//        t.setDaemon(true);
        t.start();

        System.out.println("main thread exits ...");  // 1
    }

}
