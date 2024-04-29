package com.froggengo.interview;

/**
 * https://www.bilibili.com/video/BV1ez421C7Fz/?p=1&spm_id_from=pageDriver
 * 这里示例完全依赖于synchronized锁竞争，实际上
 * @author fly
 * @create 2024-04-16-15:02
 **/
public class T1_PrintSerialByThreadNoWait {

    static int count = 0;
    static int maxNumber = 30;

    public static void main(String[] args) {

        Object lock = new Object();


        for (int i = 0; i < 3; i++) {
            int finalI = i;
            new Thread(String.valueOf(i)) {
                int index = finalI;

                @Override
                public void run() {
                    while (count < maxNumber) {
                        synchronized (lock) {
                            System.out.println("try to print:" + Thread.currentThread().getName());
//                            这里必须判断count大小，否则同样陷入wait中
                            if (count % 3 == index && count < maxNumber) {
                                System.out.println("thread：" + index + ",print:" + count);
                                count++;
                            }
                        }

                    }
                }
            }.start();
        }
    }
}
