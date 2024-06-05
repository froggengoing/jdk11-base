package com.froggengo.thread.queue;

import org.junit.jupiter.api.Test;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author fly
 * @create 2024-06-05-14:15
 **/
public class T4_DelayQueue {
    @Test
    public void test9() throws InterruptedException {
        DelayQueue<DelayClass> queue = new DelayQueue<DelayClass>();
        queue.add(new DelayClass(2, 1000));
        queue.add(new DelayClass(1, 5000));
        long begin = System.currentTimeMillis();
        DelayClass e1 = queue.take();
        System.out.println(System.currentTimeMillis() - begin + ":" + e1.id);
        DelayClass e2 = queue.take();
        System.out.println(System.currentTimeMillis() - begin + ":" + e2.id);
    }

    static class DelayClass implements Delayed {

        public int id;

        int delay;

        long beginMs;

        public DelayClass(int id, int delay) {
            this.id = id;
            this.delay = delay;
            this.beginMs = System.currentTimeMillis();
        }

        @Override
        public long getDelay(TimeUnit unit) {
            long diff = this.beginMs + this.delay - System.currentTimeMillis();
            return unit.convert(diff, TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            if (this.delay < ((DelayClass) o).delay) {
                return -1;
            }
            if (this.delay > ((DelayClass) o).delay) {
                return 1;
            }
            return 0;
        }


        @Override
        public String toString() {
            return "DelayClass{" +
                    "id=" + id +
                    ", delay=" + delay +
                    ", beginMs=" + beginMs +
                    '}';
        }
    }
}
