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
    public Instruction(String title, String description) {
        this.title = title;
        this.description = description;
        this.timer = null;
    }

    public boolean isTimer() {
        return timer != null;
    }

    public static class Timer implements Serializable{
        public int duration; // Seconds
        public String expirationMessage;

        public Timer(int hours, int minutes, int seconds, String expirationMessage) {
            duration = 3600 * hours + 60 * minutes + seconds;
            this.expirationMessage = expirationMessage;
        }
    }
}


