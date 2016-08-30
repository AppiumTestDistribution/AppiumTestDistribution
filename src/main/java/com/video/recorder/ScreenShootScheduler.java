package com.video.recorder;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by saikrisv on 23/06/16.
 */
public class ScreenShootScheduler {
    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        Tasker task1 = new Tasker("Demo Task 1");

        System.out.println("The time is : " + new Date());

        ScheduledFuture<?> result = executor.scheduleAtFixedRate(task1, 2, 1, TimeUnit.SECONDS);

        try {
            TimeUnit.MILLISECONDS.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executor.shutdown();
    }
}
