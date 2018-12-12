package com.epam.javatraining.step2;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class MyThreadPool {
    private final BlockingQueue<Runnable> tasks;
    private final List<MyWorker> workers;

    public MyThreadPool(int threadsCount, int tasksCount) {
        this.tasks = new ArrayBlockingQueue<>(tasksCount);
        workers = IntStream.range(1, threadsCount + 1)
                           .mapToObj(MyWorker::new)
                           .collect(toList());
        workers.forEach(Thread::start);
    }

    void execute(Runnable task) {
        tasks.offer(task);
    }

    void shutDown() {
        if (tasks.isEmpty()) {
            workers.forEach(MyWorker::shutDown);
        }
    }

    private final class MyWorker extends Thread {
        private volatile boolean isShutDowned;
        private Runnable currentTask;

        MyWorker(int num) {
            setName("Worker #" + num);
        }

        @Override
        public void run() {
            while (!isShutDowned) {
                try {
                    currentTask = tasks.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                currentTask.run();
            }
        }

        void shutDown() {
            isShutDowned = true;
        }
    }
}
