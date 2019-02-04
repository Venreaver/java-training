package com.epam.javatraining.queues;

import java.util.concurrent.Executor;

public class MyExecutor implements Executor {
    private MyBlockingQueue<Runnable> tasks = new MyBlockingQueue<>();
    private static final Runnable POISON_PILL = () -> {
    };

    public MyExecutor() {
        new Thread(this::processTasks).start();
    }

    @Override
    public void execute(Runnable task) {
        tasks.put(task);
    }

    public void shutDown() {
        tasks.put(POISON_PILL);
    }

    private void processTasks() {
        while (true) {
            Runnable task = tasks.take();
            if (task == POISON_PILL) {
                break;
            }
            task.run();
        }
    }
}
