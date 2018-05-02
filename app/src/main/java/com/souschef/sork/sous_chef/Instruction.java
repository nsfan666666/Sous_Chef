package com.souschef.sork.sous_chef;

import java.io.Serializable;

/**
 * Created by Robin on 2018-05-02.
 */

public class Instruction implements Serializable{

    public String title;
    public String description;
    public Timer timer;

    public Instruction(String title, String description, Timer optionalTimer ) {
        this.title = title;
        this.description = description;
        this.timer = optionalTimer;
    }

    public boolean isTimer() {
        return timer != null;
    }

    public static class Timer implements Serializable{
        public int duration; // Seconds

        public Timer(int hours, int minutes, int seconds) {
            duration = 3600 * hours + 60 * minutes + seconds;
        }

        @Override
        public String toString() {
            int tempDuration = duration;
            int hours = tempDuration / 3600;
            tempDuration = tempDuration % 3600;
            int minutes = tempDuration / 60;
            tempDuration = tempDuration % 60;
            int seconds = tempDuration;
            return String.format("%02d:%02d:%02d",hours, minutes, seconds);
        }

    }
}


