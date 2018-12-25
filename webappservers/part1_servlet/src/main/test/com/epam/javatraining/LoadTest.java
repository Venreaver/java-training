package com.epam.javatraining;

import org.testng.annotations.Test;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LoadTest {
    private static final String HELLO_URL = "http://localhost:8080/hello";
    private static final int THREADS_COUNT = 10;

    @Test
    public void loadTest() {
        ExecutorService executorService = Executors.newFixedThreadPool(THREADS_COUNT);
        List<Future<Integer>> tasks = IntStream.range(0, THREADS_COUNT).mapToObj(index ->
                executorService.submit(() -> {
                    URL url = new URL(HELLO_URL);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    try {
                        Thread.sleep(10000);
                        return connection.getResponseCode();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                    return null;
                })
        ).collect(Collectors.toList());
        tasks.forEach(task -> {
            try {
                System.out.println(task.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
    }
}