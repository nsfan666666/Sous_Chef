package com.souschef.sork.sous_chef;

import android.content.Intent;
import android.os.Handler;
import android.os.PowerManager;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Test extends AppCompatActivity {

    //to keep screen on
    protected PowerManager.WakeLock wakeLock;

    private SpeechRecognizer speechRecognizer;

    //to post changes to progress bar
    private Handler handler = new Handler();

    //ui textview
    TextView responseText;

    //intent for speechRecognition
    Intent speechIntent;

    //legal commands
    private static final String[] VALID_COMMANDS = {
            "next task",
            "repeat",
            "start timer"
    };
    private static final int VALID_COMMANDS_SIZE = VALID_COMMANDS.length;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }
}
