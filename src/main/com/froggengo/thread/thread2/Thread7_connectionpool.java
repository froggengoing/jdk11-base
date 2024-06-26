package com.froggengo.thread.thread2;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Thread7_connectionpool {

    private LinkedList<Connection> pool = new LinkedList<Connection>();

    /**
     * 初始化
     */
    public Thread7_connectionpool(int size) {
        synchronized (pool) {
            for (int i = 0; i < size; i++) {
                pool.addLast(ConnectionDriver.creatConnection());
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread7_connectionpool pool = new Thread7_connectionpool(5);
        Runnable r = () -> {
            Connection connection = null;
            try {
                connection = pool.fetchConnection(1000);
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            pool.releaseConnection(connection);
        };
        ExecutorService executor = Executors.newFixedThreadPool(20);
        for (int i = 0; i < 1000; i++) {
            executor.execute(r);
        }

    }

    /**
     * 释放连接
     */
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

    /**
     * 指定时间内获取连接
     */
    public Connection fetchConnection(long mills) throws InterruptedException {
        synchronized (pool) {
            if (mills <= 0) {
                while (pool.isEmpty()) {
                    System.out.println("1连接池为空， " + Thread.currentThread().getName() + " 进入等待");
                    pool.wait();
                }
                System.out.println("1连接池非空， " + Thread.currentThread().getName() + " 获取连接");
                return pool.removeFirst();
            } else {
                long future = System.currentTimeMillis() + mills;
                long remain = mills;
                while (remain > 0) {
                    if (pool.isEmpty()) {
                        System.out.println("2连接池为空， " + Thread.currentThread().getName() + " 进入等待");
                        pool.wait(remain);
                    } else {
                        System.out.println("2连接池非空， " + Thread.currentThread().getName() + " 获取连接");
                        return pool.removeFirst();
                    }
                    remain = future - System.currentTimeMillis();
                }
                System.out.println("超时无法获得连接：" + Thread.currentThread().getName());
            }
        }
        return null;
    }

    private static class ConnectionDriver implements InvocationHandler {

        public static Connection creatConnection() {
            return (Connection) Proxy.newProxyInstance(ConnectionDriver.class.getClassLoader(),
                    new Class[]{Connection.class}, new ConnectionDriver());
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getName().equals("commit")) {
                Thread.currentThread().sleep(200);
            }
            ;
            return null;
        }
    }
}
