package com.froggengo.thread.queue;

import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author fly
 * @create 2024-06-04-18:11
 **/
public class T2_MyLinkedBlockingQueue extends AbstractQueue<Integer> implements BlockingQueue<Integer> {
    private final int capacity;
    /**
     * 因为读写分离，所以当前元素个数，要使用AtomicInteger
     */
    private final AtomicInteger count = new AtomicInteger();
    private final ReentrantLock takeLock = new ReentrantLock();
    private final Condition notEmpty = takeLock.newCondition();
    private final ReentrantLock putLock = new ReentrantLock();
    private final Condition notFull = putLock.newCondition();
    /**
     * Tail of linked list.
     * Invariant: last.next == null
     */

    transient T2_MyLinkedBlockingQueue.Node<Integer> head;
    private transient T2_MyLinkedBlockingQueue.Node<Integer> last;

    public T2_MyLinkedBlockingQueue(int capacity) {
        this.capacity = capacity;
        head = last = new Node<>(null);
    }

    @Override
    public void put(Integer value) throws InterruptedException {
        this.putLock.lockInterruptibly();
        final int c;
        try {
            while (this.count.get() >= this.capacity) {
                System.out.println(Thread.currentThread().getName() + ":生产线程阻塞");
                notFull.await();
                System.out.println(Thread.currentThread().getName() + ":生产线程唤醒");
            }
            c = this.count.getAndIncrement();
            // 插入
            // bug
            //this.last.next = new Node<>(value);
            //修复后
            this.last.next = new Node<>(value);
            this.last = last.next;

            if (c + 1 < this.capacity) this.notFull.signal();
        } finally {
            this.putLock.unlock();
        }
        // 通知生产线程
        // 这里必须持有 takeLock 才能 notEmpty.signal()
        if (c == 0) {
            takeLock.lock();
            try {
                this.notEmpty.signal();
            } finally {
                this.takeLock.unlock();
            }
        }
    }

    @Override
    public Integer take() throws InterruptedException {
        this.takeLock.lockInterruptibly();
        final int c;
        final Integer value;
        try {
            while (this.count.get() <= 0) {
                System.out.println(Thread.currentThread().getName() + ":消费线程阻塞");
                notEmpty.await();
                System.out.println(Thread.currentThread().getName() + ":消费线程被唤醒");
            }

            Node<Integer> first = head.next;
            value = first.item;
            first.item = null;//第一个元素置空
            // bug
            //head = first.next;
            // 修改为
            head = first;

            System.out.println(Thread.currentThread().getName() + "消费：" + value);
            c = this.count.getAndDecrement();

            if (c - 1 > 0) {
                notEmpty.signal();
            }
        } finally {
            this.takeLock.unlock();
        }
        // 这里必须持有putLock才能notFull.signal()
        if (c == capacity) {
            this.putLock.lock();
            try {
                notFull.signal();
            } finally {
                putLock.unlock();
            }

        }

        return value;
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

    static class Node<Integer> {
        Integer item;

        /**
         * One of:
         * - the real successor Node
         * - this Node, meaning the successor is head.next
         * - null, meaning there is no successor (this is the last node)
         */
        T2_MyLinkedBlockingQueue.Node<Integer> next;

        Node(Integer x) {item = x;}
    }
}
