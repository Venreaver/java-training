package com.epam.javatraining.queues;

import java.util.stream.IntStream;

public class MyThreadPoolRun {
    public static void main(String[] args) {
        MyThreadPool executor = new MyThreadPool(5, 20);
        IntStream.range(1, 21).forEach(i -> executor.execute(() ->
                System.out.println(Thread.currentThread().getName() + " are executing task № " + i)));
        executor.shutDown();
    }
}
