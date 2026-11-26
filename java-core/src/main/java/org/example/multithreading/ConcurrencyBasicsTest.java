package org.example.multithreading;

public class ConcurrencyBasicsTest {
    public static void main(String[] args) throws InterruptedException {
        var thread = new Thread(() ->
                System.out.println(
                        "Chạy trên Thread riêng: "
                                + Thread.currentThread().getName()
                )
        );
        thread.start();
        thread.join();

        var synchronizedCounter = new SynchronizedCounter();
        var lockCounter = new LockCounter();
        var workers = new Thread[10];
        var iterationsPerWorker = 10_000;

        for (var index = 0; index < workers.length; index++) {
            workers[index] = new Thread(() -> {
                for (var iteration = 0;
                     iteration < iterationsPerWorker;
                     iteration++) {
                    synchronizedCounter.increment();
                    lockCounter.increment();
                }
            });
            workers[index].start();
        }

        for (var worker : workers) {
            worker.join();
        }

        var expected = workers.length * iterationsPerWorker;
        System.out.println("Synchronized expected: " + expected);
        System.out.println("Synchronized actual:   "
                + synchronizedCounter.value());
        System.out.println("Lock expected:         " + expected);
        System.out.println("Lock actual:           " + lockCounter.value());
    }
}
