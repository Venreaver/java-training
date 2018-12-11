package com.epam.javatraining.step2;

import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {
    private BlockingQueue<Integer> queue;

    public Consumer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Integer item = queue.take();
                System.out.println("CONSUMER consumed " + item);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
