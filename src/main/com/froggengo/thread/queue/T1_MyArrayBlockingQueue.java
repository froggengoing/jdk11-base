package com.froggengo.thread.queue;

import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author fly
 * @create 2024-06-04-15:32
 **/
public class T1_MyArrayBlockingQueue extends AbstractQueue<Integer> implements BlockingQueue<Integer> {
    final Object[] items;

    final ReentrantLock lock;

    final Condition notEmpty;

    final Condition notFull;

    int count;
    int takeIndex;
    int putIndex;

    public T1_MyArrayBlockingQueue(int capacity, boolean fair) {
        items = new Object[capacity];
        lock = new ReentrantLock(fair);
        notEmpty = lock.newCondition();
        notFull = lock.newCondition();
    }

    @Override
    public Iterator<Integer> iterator() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void put(Integer value) throws InterruptedException {
        lock.lock();
        try {
            //if (count == items.length) {
            while (count == items.length) {
                System.out.println(Thread.currentThread().getName() + ":生产线程阻塞");
                notFull.await();
                System.out.println(Thread.currentThread().getName() + ":生产线程唤醒");
            }
            System.out.println(Thread.currentThread().getName() + "生产：" + value);
            items[putIndex] = value;
            if (++putIndex == items.length) {
                putIndex = 0;
            }
            ++count;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Integer take() throws InterruptedException {
        lock.lock();
        try {
            //if (count == 0) {
            while (count == 0) {
                System.out.println(Thread.currentThread().getName() + ":消费线程阻塞");
                notEmpty.await();
                System.out.println(Thread.currentThread().getName() + ":消费线程被唤醒");
            }
            Integer value = (Integer) items[takeIndex];
            System.out.println(Thread.currentThread().getName() + "消费：" + value);
            items[takeIndex] = null;
            if (++takeIndex == items.length) {
                takeIndex = 0;
            }

            --count;
            notFull.signal();
            return value;
        } finally {
            lock.unlock();
        }
    }


    @Override
    public boolean offer(Integer integer, long timeout, TimeUnit unit) throws InterruptedException {
        return false;
    }


    @Override
    public Integer poll(long timeout, TimeUnit unit) throws InterruptedException {
        return null;
    }

    @Override
    public int remainingCapacity() {
        return 0;
    }

    @Override
    public int drainTo(Collection<? super Integer> c) {
        return 0;
    }

    @Override
    public int drainTo(Collection<? super Integer> c, int maxElements) {
        return 0;
    }

    @Override
    public boolean offer(Integer integer) {
        return false;
    }

    @Override
    public Integer poll() {
        return null;
    }

    @Override
    public Integer peek() {
        return null;
    }
}
