package com.souschef.sork.sous_chef;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import ernestoyaquello.com.verticalstepperform.VerticalStepperFormLayout;
import ernestoyaquello.com.verticalstepperform.interfaces.VerticalStepperForm;

public class CookingActivity extends AppCompatActivity implements VerticalStepperForm, Commands, SensorEventListener{

    private VerticalStepperFormLayout verticalStepperForm;
    private EditText name;

    private Speaker speaker;
    private static RecipeLite recipe;
    private int stepCompleted = 0;

    // Voice
    private VoiceUI voiceUI;

    // Sensor
    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private final static int SENSOR_SENSITIVITY = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooking);

        speaker = new Speaker(this);

        recipe = (RecipeLite) getIntent().getExtras().getSerializable(RecipeChooserActivity.PlaceholderFragment.RECIPE_LITE);

        recipe.instructions.add(0, new Instruction("Start", "Start cooking", null));

        TextView recipeTitle = (TextView) findViewById(R.id.recipe);
        recipeTitle.setText(recipe.name);

        String[] instructions = new String[recipe.instructions.size()];
        for(int i = 0; i < instructions.length; i++) {
            instructions[i] = recipe.instructions.get(i).title;
        }
        //instructions = recipe.instructions.toArray(instructions);

        int colorPrimary = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary);
        int colorPrimaryDark = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark);

        // Finding the view
        verticalStepperForm = (VerticalStepperFormLayout) findViewById(R.id.vertical_stepper_form);

        // Setting up and initializing the form
        VerticalStepperFormLayout.Builder.newInstance(verticalStepperForm, instructions, this, this)
                .primaryColor(colorPrimary)
                .primaryDarkColor(colorPrimaryDark)
                .displayBottomNavigation(true) // It is true by default, so in this case this line is not necessary
                .init();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);


        // Change text of last step title and button
        RelativeLayout relativeLayout = (RelativeLayout) verticalStepperForm.getChildAt(0);
        ScrollView scrollView = (ScrollView) relativeLayout.getChildAt(1);
        LinearLayout linearLayout = (LinearLayout) scrollView.getChildAt(0);
        LinearLayout linearLayout2 = (LinearLayout) linearLayout.getChildAt(linearLayout.getChildCount() - 1);
        RelativeLayout relativeLayout2 = (RelativeLayout) linearLayout2.getChildAt(0);

        // Title
        AppCompatTextView appCompatTextView = (AppCompatTextView) relativeLayout2.getChildAt(2);
        appCompatTextView.setText("Finish");

        LinearLayout linearLayout3  = (LinearLayout) linearLayout2.getChildAt(2);
        LinearLayout linearLayout5  = (LinearLayout) linearLayout3.getChildAt(1);

        // Button
        AppCompatButton appCompatButton = (AppCompatButton)  linearLayout5.getChildAt(0);
        appCompatButton.setText("Finish");
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);

        String command = CommandMonitor.getMonitor().getCommand().toLowerCase();
        if(command.length() > 0) {
            if(command.equals("next task")) {
                next();
            } else if(command.equals("repeat")) {
                repeat();
            } else if(command.equals("previous task")) {
                previous();
            }
        }
    }

    //Implemented method from SensorEventListener.
    @Override
    protected void onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause();
        sensorManager.unregisterListener(this);
    }


    @Override
    public View createStepContentView(int stepNumber) {
        View view = null;
        LayoutInflater inflater = LayoutInflater.from(getBaseContext());
        if(stepNumber == 0) {
            // Start layout
            view = (LinearLayout) inflater.inflate(R.layout.empty, null, false);
        } else if(recipe.instructions.get(stepNumber).isTimer()) {
            // Timer layout
            view = (LinearLayout) inflater.inflate(R.layout.cooking_instruction_timer, null, false);
            TextView description = (TextView) view.findViewById(R.id.description);
            if(description != null) {
                description.setText(recipe.instructions.get(stepNumber).description);
            }
            TextView timerText = (TextView) view.findViewById(R.id.timer_text);
            if(timerText != null) {
                timerText.setText(recipe.instructions.get(stepNumber).timer.toString());
            }

            Button startTimerButton = (Button) view.findViewById(R.id.start_timer_button);
            startTimerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button button = (Button) v;
                    if(button.getText() == "Start timer") {
                        // TODO Start timer
                        final Timer timer = new Timer();

                        //timer.scheduleAtFixedRate(timerTask, 0, 1000);

                        button.setText("Pause timer");
                    } else {
                        // TODO Pause timer
                        button.setText("Start timer");
                    }
                }
            });
        } else {
            // Regular layout
            view = (LinearLayout) inflater.inflate(R.layout.cooking_instruction_layout, null, false);
            TextView description = (TextView) view.findViewById(R.id.description);
            if(description != null) {
                description.setText(recipe.instructions.get(stepNumber).description);
            }
        }
        return view;
    }

    @Override
    public void onStepOpening(int stepNumber) {
        if(stepNumber != 0 && stepNumber < recipe.instructions.size()) {
            String instruction = recipe.instructions.get(stepNumber).description;
            if(instruction != null) {
                speaker.readText(instruction);
            }
        }

        verticalStepperForm.setStepAsCompleted(stepNumber);

        if(stepCompleted < stepNumber) {
            stepCompleted = stepNumber;
        } else {
            for(int i = stepNumber + 1; i <= stepCompleted; i++) {
                verticalStepperForm.setStepAsUncompleted(i, null);
            }
        }
    }

    @Override
    public void sendData() {
        // When pressing final button
        // Return to previous activity
        finish();
    }

    // Commands

    public void startVoice(View view) {

        // Clear any previous commands
        CommandMonitor.getMonitor().setCommand("");

        // Start voice listener
        Intent intent = new Intent(getBaseContext(), VoiceUI.class);
        startActivity(intent);
        
    }

    @Override
    public void next() {
        verticalStepperForm.goToNextStep();
    }

    @Override
    public void previous() {
        verticalStepperForm.goToPreviousStep();
    }

    @Override
    public void repeat() {
        verticalStepperForm.goToStep(verticalStepperForm.getActiveStepNumber(), true);
    }

    @Override
    public void start() {
        next();
    }

    @Override
    public void startTimer() {
        // TODO

    }

    @Override
    public void pause() {
        // TODO
    }

    public class CustomTimerTask extends TimerTask {
        int duration; // Seconds
        int elapsed;

        public CustomTimerTask(int duration) {
            this.duration = duration;
        }

        @Override
        public void run() {

        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            if (event.values[0] >= -SENSOR_SENSITIVITY && event.values[0] <= SENSOR_SENSITIVITY) {
                startVoice(null);
            }
        }


    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
