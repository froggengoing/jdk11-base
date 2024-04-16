package com.froggengo.thread.thread2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author fly
 * @create 2024-04-16-10:54
 **/
public class Thread7_connectionpool1 {

    private LinkedList<Connection> pool = new LinkedList<Connection>();


    public Thread7_connectionpool1(int size) {
        for (int i = 0; i < size; i++) {
            pool.push(ConnectionDriver.createConnection());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread7_connectionpool1 connectionpool = new Thread7_connectionpool1(5);
        Runnable r = () -> {
            Connection connection = null;
            try {
                connection = connectionpool.fetch(1000);
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            connectionpool.releaseConnection(connection);
        };
        ExecutorService executor = Executors.newFixedThreadPool(20);
        for (int i = 0; i < 1000; i++) {
            executor.execute(r);
        }

    }

    public boolean releaseConnection(Connection connection) {
        if (connection != null) {
            synchronized (pool) {
                pool.addLast(connection);
                pool.notifyAll();
                System.out.println("释放连接， " + Thread.currentThread().getName());
                return true;
            }
        }
        return false;
    }

    public Connection fetch(long waitMills) throws InterruptedException {
        synchronized (pool) {
            if (waitMills <= 0) {
                while (pool.isEmpty()) {
                    System.out.println("1连接池为空， " + Thread.currentThread().getName() + " 进入等待");
                    pool.wait();
                }
                System.out.println("1连接池非空， " + Thread.currentThread().getName() + " 获取连接");
                return pool.removeFirst();

            } else {
                long beginMs = System.currentTimeMillis();
                long delta = 0;
                while (delta < waitMills) {
                    if (!pool.isEmpty()) {
                        System.out.println("2连接池非空， " + Thread.currentThread().getName() + " 获取连接");
                        return pool.removeFirst();
                    } else {
                        System.out.println("2连接池为空， " + Thread.currentThread().getName() + " 进入等待");
                        pool.wait(waitMills - delta);
                        delta = System.currentTimeMillis() - beginMs;
                    }
                }
//                    这里说明一定时间后也无法获取连接
                System.out.println("超时无法获得连接：" + Thread.currentThread().getName());
                return null;
            }
        }
    }

    static class ConnectionDriver implements InvocationHandler {


        public static Connection createConnection() {
            return (Connection) Proxy.newProxyInstance(ConnectionDriver.class.getClassLoader(), new Class[]{Connection.class}, new ConnectionDriver());
        }


        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getName().equals("commit")) {
                Thread.currentThread().sleep(200);
            }
            else if(method.getName().equals("toString")){
                return "connection";
            }
            return null;
        }
    }
}
