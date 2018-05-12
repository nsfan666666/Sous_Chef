package com.souschef.sork.sous_chef;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

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

    private String expirationMessage;

    // Popup
    private volatile static TimerPopupActivity timerPopupActivity;
    private static Monitor monitor;

    public Timer(CookingActivity cookingActivity, long totalDurationMilliseconds, String expirationMessage) {
        this.cookingActivity = cookingActivity;
        this.totalDurationMilliseconds = totalDurationMilliseconds;
        timeLeftMilliseconds = totalDurationMilliseconds;
        this.expirationMessage = expirationMessage;

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

                if(monitor == null) {
                    monitor = new Monitor();
                }

                if(timerPopupActivity == null) {
                    Intent intent = new Intent(cookingActivity.getBaseContext(), TimerPopupActivity.class);
                    cookingActivity.startActivity(intent);
                    monitor.popups.add(expirationMessage);
                } else {
                    if(timerPopupActivity.visible()) {
                        monitor.popups.add(expirationMessage);
                    } else {
                        Intent intent = new Intent(cookingActivity.getBaseContext(), TimerPopupActivity.class);
                        cookingActivity.startActivity(intent);
                        monitor.popups.add(expirationMessage);
                    }
                }


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

    public static class TimerPopupActivity extends AppCompatActivity {
        private boolean visible = false;
        private Speaker speaker;
        private LinearLayout timerPopupContainer;
        private Waiter waiter;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_timer_popup);
            timerPopupActivity = this;
            speaker = new Speaker(this);
            timerPopupContainer = findViewById(R.id.timerPopupContainer);
        }

        public boolean visible() {
            return visible;
        }

        @Override
        protected void onPause() {
            super.onPause();
            visible = false;
            waiter.interrupt();
        }

        @Override
        protected void onResume() {
            super.onResume();
            visible = true;
            waiter = new Waiter(this);
            waiter.start();
        }

        public void addPopup(String message) {
            runOnUiThread(new AddPopup(message, getBaseContext(), timerPopupContainer));
        }

        private static class AddPopup implements Runnable {
            private String message;
            private Context context;
            private LinearLayout timerPopupContainer;

            public AddPopup(String message, Context context, LinearLayout timerPopupContainer) {
                this.message = message;
                this.context = context;
                this.timerPopupContainer = timerPopupContainer;
            }

            @Override
            public void run() {
                LayoutInflater inflater = LayoutInflater.from(context);
                View view = (LinearLayout) inflater.inflate(R.layout.timer_popup_list_item, null, false);
                TextView popupTimerInfo = (TextView) view.findViewById(R.id.popup_timer_info);
                popupTimerInfo.setText(message);
                timerPopupContainer.addView(view);
            }
        }

        private static class Waiter extends Thread {
            private TimerPopupActivity timerPopupActivity;

            public Waiter(TimerPopupActivity timerPopupActivity) {
                this.timerPopupActivity = timerPopupActivity;
            }

            public void run() {
                while (true) {
                    try {
                        Thread.sleep(100);
                        //
                        if(monitor.popups.size() > 0) {
                            timerPopupActivity.addPopup(monitor.popups.remove(0));
                        }
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }
        }
    }

    private static class Monitor extends Observable{
        private volatile ArrayList<String> popups;

        public Monitor() {
            popups = new ArrayList<>();
        }
    }
}
