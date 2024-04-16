package com.froggengo.interview;

/**
 * @author fly
 * @create 2024-04-16-15:02
 **/
public class T1_PrintSerialByThread {

    static int count = 0;
    static int maxNumber = 30;

    public static void main(String[] args) {

        Object lock = new Object();


        for (int i = 0; i < 3; i++) {
            int finalI = i;
            new Thread() {
                int index = finalI;

                @Override
                public void run() {
                    while (true) {
                        synchronized (lock) {
//                            这里必须判断count大小，否则同样陷入wait中
                            while (count % 3 != index && count < maxNumber) {
                                System.out.println("序号不属于当前线程");
                                try {
                                    lock.wait();
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            lock.notifyAll();
//                            必须在循环内判断，否则可能超过限制
                            if (count >= maxNumber) {
                                break;
                            }
                            count++;
                            System.out.println("thread：" + index + ",print:" + count);


                        }

                    }
                }
            }.start();
        }
    }
}
