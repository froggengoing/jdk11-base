package com.froggengo.thread.thread;

import org.junit.jupiter.api.Test;

/**
 * @author fly
 * @create 2024-04-15-14:51
 **/
public class T4_ThreadJoin {
    @Test
    public void test11() throws InterruptedException {

        Runnable r = () -> {
            try {
                System.out.println("thread running block begin");
                Thread.sleep(5000);
                System.out.println("thread running block end");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        long begin = System.currentTimeMillis();
        Thread thread = new Thread(r);
        thread.setDaemon(true);
        thread.start();
//        上面虽然设置为守护进程，但由于这里是join所以依然会等进程执行完成
        thread.join();
        System.out.println("finished cost：" + (System.currentTimeMillis() - begin));
    }
}
