package com.souschef.sork.sous_chef;

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

    private LinearLayout rootView;
    private CountDownTimer countDownTimer;
    private boolean timerRunning;
    private long totalDurationMilliseconds;
    private long timeLeftMilliseconds;

    private TextView countdownTimerTextView;
    private Button start;
    private Button reset;

    public Timer(Context baseContext, long totalDurationMilliseconds) {
        this.totalDurationMilliseconds = totalDurationMilliseconds;
        timeLeftMilliseconds = totalDurationMilliseconds;

        LayoutInflater inflater = LayoutInflater.from(baseContext);
        rootView = (LinearLayout) inflater.inflate(R.layout.countdown_timer_layout, null, false);

        countdownTimerTextView = (TextView) rootView.findViewById(R.id.countdown_timer);
        countdownTimerTextView.setText(getTimeRemaining());

        start = (Button) rootView.findViewById(R.id.start_countdown_timer);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timerRunning) {
                    pause();
                    timerRunning = false;
                    ((Button) v).setText("Start");
                } else {
                    start();
                    timerRunning = true;
                    ((Button) v).setText("Pause");
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
        if(!timerRunning) {
            int updateFrequencyMilliseconds = 1000;

            countDownTimer = new CountDownTimer(timeLeftMilliseconds, updateFrequencyMilliseconds) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timeLeftMilliseconds = millisUntilFinished;
                    countdownTimerTextView.setText(getTimeRemaining());
                }

                @Override
                public void onFinish() {
                    // TODO Make sound

                }
            };
            countDownTimer.start();
            reset.setVisibility(View.VISIBLE);
        }
    }

    public void pause() {
        countDownTimer.cancel();
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

}
