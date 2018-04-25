package com.souschef.sork.sous_chef;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.PowerManager;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Locale;


public class Test extends AppCompatActivity implements SensorEventListener {

    private final static String TAG = Test.class.getName();

    private final static int SENSOR_SENSITIVITY = 4;

    //to keep screen on
    protected PowerManager.WakeLock wakeLock;

    private SpeechRecognizer speechRecognizer;

    //to post changes to progress bar
    private Handler handler = new Handler();

    //ui textview
    TextView responseText;

    //intent for speechRecognition
    Intent speechIntent;

    boolean killCommanded = false;

    //The start button
    private Button startButton;
    private SensorManager sensorManager;
    private Sensor proximitySensor;


    //Text to speech
    TextToSpeech toSpeech;


    //legal commands
    private static final String[] VALID_COMMANDS = {
            "next task",
            // "repeat",
            // "start timer"
    };
    private static final int VALID_COMMANDS_SIZE = VALID_COMMANDS.length;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        responseText = findViewById(R.id.responseText);

        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startListeningButton();
            }
        });

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        toSpeech = new TextToSpeech(Test.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                   toSpeech.setLanguage(Locale.US);

            }
        });
    }

    private void readText() {
        String viewText = responseText.getText().toString();

        toSpeech.speak(viewText, TextToSpeech.QUEUE_FLUSH, null);
    }

    private void readText(String finalResponse) {
        toSpeech.speak(finalResponse, TextToSpeech.QUEUE_FLUSH, null);
    }

    // @Override
    protected void startListeningButton() {
        // super.onStart();

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        SpeechListener recognitionListener = new SpeechListener();
        speechRecognizer.setRecognitionListener((recognitionListener));
        speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        speechIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "com.souschef.sork.sous_chef");

        // Given a hint to the recognizer about what the user is going to say
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        //Specifiy how many results you want to receive. The results will be sorted where the first result is the one with higher confidence.
        speechIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 20);

        speechIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);

        //acquire the  wakelock to keep the screen on until user exits/closes the app
        final PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        this.wakeLock = powerManager.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, TAG);
        this.wakeLock.acquire();
        speechRecognizer.startListening(speechIntent);


    }

    //Implemented method from SensorEventListener.
    @Override
    protected void onResume() {
        // Register a listener for the sensor.
        super.onResume();
        sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    //Implemented method from SensorEventListener.
    @Override
    protected void onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause();
        sensorManager.unregisterListener(this);
    }


    //Implemented method from SensorEventListener. Decides what happens when sensor changes.
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            if (event.values[0] >= -SENSOR_SENSITIVITY && event.values[0] <= SENSOR_SENSITIVITY) {
                //near
                System.out.print("NEAR");
                startListeningButton();
            } else {
                //far
                // Toast.makeText(getApplicationContext(), "far", Toast.LENGTH_SHORT).show();
               // startListeningButton();
            }
        }

    }

    //Implemented method from SensorEventListener.
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private class SpeechListener implements RecognitionListener {

        @Override
        public void onReadyForSpeech(Bundle params) {
            Log.d(TAG, "on ready for speech");

        }

        @Override
        public void onBeginningOfSpeech() {
            Log.d(TAG, "Beginning of speech");
        }

        @Override
        public void onRmsChanged(float rmsdB) {
            Log.d(TAG, "RMS changed");
        }

        @Override
        public void onBufferReceived(byte[] buffer) {
            Log.d(TAG, "buffer recieved");
        }

        @Override
        public void onEndOfSpeech() {
            Log.d(TAG, "End of Speech");

        }

        @Override
        public void onError(int error) {
            //if (critical error) then exit
            if (error == SpeechRecognizer.ERROR_CLIENT) {
                Log.d(TAG, "Client error");
            } else if (error == SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS) {
                Log.d(TAG, "DUDE, you need permissions");
            } else {
                Log.d(TAG, "Other error");
                //speechRecognizer.startListening(speechIntent);
            }

        }

        @Override
        public void onResults(Bundle results) {
            Log.d(TAG, "on result");
            ArrayList<String> matches = null;
            if (results != null) {
                matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (matches != null) {
                    Log.d(TAG, "Results are: " + matches.toString());
                    final ArrayList<String> matchesStrings = matches;
                    processCommand(matchesStrings);
                }
            }

        }

        @Override
        public void onPartialResults(Bundle partialResults) {
            Log.d(TAG, "Partial results");
        }

        @Override
        public void onEvent(int eventType, Bundle params) {
            Log.d(TAG, "on Event");

        }
    }

    private void processCommand(ArrayList<String> matchesStrings) {
        String response = "I'm sorry Dave, I'm afraid I can't do that";
        int maxStrings = matchesStrings.size();
        boolean resultFound = false;
        for (int i = 0; i < VALID_COMMANDS_SIZE && !resultFound; i++) {
            for (int j = 0; j < maxStrings && !resultFound; j++) {
                if (StringUtils.getLevenshteinDistance(matchesStrings.get(j), VALID_COMMANDS[i]) < (VALID_COMMANDS[i].length() / 3)) {
                    response = getResponse(i);
                }
            }

        }

        final String finalResponse = response;
        handler.post(new Runnable() {
            @Override
            public void run() {
                responseText.setText(finalResponse);
            }
        });
        readText(finalResponse);
    }



    private String getResponse(int command) {
        String returnString = "I'm sorry, Dave. I'm afraid I can't do that";
        switch (command) {
            case 0:
                returnString = "BOOYAH";
                break;

        }
        return returnString;
    }
}
