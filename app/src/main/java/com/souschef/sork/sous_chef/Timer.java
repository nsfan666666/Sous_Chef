package com.souschef.sork.sous_chef;

import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Robin on 2018-05-10.
 */

public class Timer{

    private CookingActivity cookingActivity;

    private LinearLayout rootView;
    private CountDownTimer countDownTimer;
    public boolean timerRunning;
    private long totalDurationMilliseconds;
    private long timeLeftMilliseconds;

    private TextView countdownTimerTextView;
    private Button start;
    private Button reset;

    public Timer(CookingActivity cookingActivity, long totalDurationMilliseconds) {
        this.cookingActivity = cookingActivity;
        this.totalDurationMilliseconds = totalDurationMilliseconds;
        timeLeftMilliseconds = totalDurationMilliseconds;

        LayoutInflater inflater = LayoutInflater.from(cookingActivity.getBaseContext());
        rootView = (LinearLayout) inflater.inflate(R.layout.countdown_timer_layout, null, false);

        countdownTimerTextView = (TextView) rootView.findViewById(R.id.countdown_timer);
        countdownTimerTextView.setText(getTimeRemaining());

        start = (Button) rootView.findViewById(R.id.start_countdown_timer);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timerRunning) {
                    pause();
                } else {
                    start();
                }
            }
        });
        reset = (Button) rootView.findViewById(R.id.reset_countdown_timer);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                ((Button) v).setVisibility(View.INVISIBLE);
            }
        });
    }

    public View getRootView() {
        return rootView;
    }


    public void start() {
        timerRunning = true;
        start.setText("Pause");

        int updateFrequencyMilliseconds = 1000;

        countDownTimer = new CountDownTimer(timeLeftMilliseconds, updateFrequencyMilliseconds) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftMilliseconds = millisUntilFinished;
                countdownTimerTextView.setText(getTimeRemaining());
            }

            @Override
            public void onFinish() {
                // TODO Temporary
                cookingActivity.speaker.readText("BEEP, BEEP, BEEP, Timer is up! BEEP, BEEP, BEEP");
            }
        };
        countDownTimer.start();
        reset.setVisibility(View.VISIBLE);
    }

    public void pause() {
        countDownTimer.cancel();
        timerRunning = false;
        start.setText("Start");
    }

    public void reset() {
        timerRunning = false;
        countDownTimer.cancel();
        timeLeftMilliseconds = totalDurationMilliseconds;
        countdownTimerTextView.setText(getTimeRemaining());
        start.setText("Start");
    }

    public String getTimeRemaining() {
        long tempDuration = timeLeftMilliseconds / 1000;
        long hours = tempDuration / 3600;
        tempDuration = tempDuration % 3600;
        long minutes = tempDuration / 60;
        tempDuration = tempDuration % 60;
        long seconds = tempDuration;
        return String.format("%02d:%02d:%02d",hours, minutes, seconds);
    }

    public static class TimerPopup {

    }
}
