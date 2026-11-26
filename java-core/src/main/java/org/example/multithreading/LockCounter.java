package org.example.multithreading;

import java.util.concurrent.locks.ReentrantLock;

public class LockCounter {
    private final ReentrantLock lock = new ReentrantLock();
    private int value;

    public void increment() {
        lock.lock();
        try {
            value++;
        } finally {
            lock.unlock();
        }
    }

    public int value() {
        lock.lock();
        try {
            return value;
        } finally {
            lock.unlock();
        }
    }
}
