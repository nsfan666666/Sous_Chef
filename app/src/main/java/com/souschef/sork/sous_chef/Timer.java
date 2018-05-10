package com.souschef.sork.sous_chef;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by Robin on 2018-05-10.
 */

public class Timer{

    private LinearLayout rootView;
    private CountDownTimer countDownTimer;
    private boolean timerRunning;
    private long totalDurationMilliseconds;
    private long timeLeftMilliseconds;

    public Timer(Context baseContext, long totalDurationMilliseconds) {
        this.totalDurationMilliseconds = totalDurationMilliseconds;
        timeLeftMilliseconds = totalDurationMilliseconds;

        LayoutInflater inflater = LayoutInflater.from(baseContext);
        rootView = (LinearLayout) inflater.inflate(R.layout.countdown_timer_layout, null, false);

        final Button start = (Button) rootView.findViewById(R.id.start_countdown_timer);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timerRunning) {
                    ((Button) v).setText("Start");
                    pause();
                } else {
                    ((Button) v).setText("Pause");
                    start();
                }
            }
        });
        Button stop = (Button) rootView.findViewById(R.id.reset_countdown_timer);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timerRunning) {
                    reset();
                    ((Button) v).setVisibility(View.INVISIBLE);
                }
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
                }

                @Override
                public void onFinish() {
                    // TODO

                }
            };
        }
    }

    public void pause() {
        countDownTimer.cancel();
    }

    public void reset() {
        countDownTimer.cancel();
        timeLeftMilliseconds = totalDurationMilliseconds;
    }

}
