package com.video.recorder;

import java.util.Date;

/**
 * Created by saikrisv on 23/06/16.
 */
public class Tasker implements Runnable {

    private String name;

    public Tasker(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override public void run() {
        try {
            System.out.println("Doing a task during : " + name + " - Time - " + new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
