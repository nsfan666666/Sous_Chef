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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import ernestoyaquello.com.verticalstepperform.VerticalStepperFormLayout;
import ernestoyaquello.com.verticalstepperform.interfaces.VerticalStepperForm;

public class CookingActivity extends AppCompatActivity implements VerticalStepperForm, Commands, SensorEventListener{

    private VerticalStepperFormLayout verticalStepperForm;
    private EditText name;

    public static Speaker speaker;
    private static RecipeLite recipe;
    private int stepCompleted = 0;

    // Voice
    private VoiceUI voiceUI;

    // Sensor
    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private final static int SENSOR_SENSITIVITY = 5;

    // Timers
    Map<Integer, com.souschef.sork.sous_chef.Timer> timers = new HashMap<>();

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

        StartTutorial.showTutorial(StartTutorial.COOKING, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);

        String command = CommandMonitor.getMonitor().getCommand().toLowerCase();
        CommandMonitor.getMonitor().setCommand("");
        if(command.length() > 0) {
            if(command.equals("next task")) {
                next();
            } else if(command.equals("repeat")) {
                repeat();
            } else if(command.equals("previous task")) {
                previous();
            } else if(command.equals("start timer")) {
                startTimer();
            } else if(command.equals("pause timer")) {
                pauseTimer();
            } else if(command.equals("reset timer")) {
                resetTimer();
            } else if(command.equals("show ingredients")) {
                showIngredients();
            } else if(command.equals("return")) {
                returnToInstruction();
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

            // Add the timer textView and start and pause buttons
            Timer timer = new Timer(this, recipe.instructions.get(stepNumber).timer.duration * 1000, recipe.instructions.get(stepNumber).timer.expirationMessage);
            timers.put(stepNumber, timer);
            ((LinearLayout) view).addView(timer.getRootView());

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
        } else if(stepNumber != 1){
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
    public void showIngredients() {
        verticalStepperForm.goToStep(1, false);
    }

    @Override
    public void returnToInstruction() {
        verticalStepperForm.goToStep(stepCompleted, false);
    }

    @Override
    public void start() {
        next();
    }

    @Override
    public void startTimer() {
        int stepNumber = verticalStepperForm.getActiveStepNumber();
        Timer timer = timers.get(stepNumber);
        if(timer != null) {
            if(!timer.timerRunning) {
                timer.start();
            } else {
                speaker.readText("The timer is already running");
            }
        }
    }

    @Override
    public void pauseTimer() {
        int stepNumber = verticalStepperForm.getActiveStepNumber();
        Timer timer = timers.get(stepNumber);
        if(timer != null) {
            if(timer.timerRunning) {
                timer.pause();
            } else {
                speaker.readText("The timer is paused");
            }
        }
    }

    @Override
    public void resetTimer() {
        int stepNumber = verticalStepperForm.getActiveStepNumber();
        Timer timer = timers.get(stepNumber);
        if(timer != null) {
            timer.reset();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            if (event.values[0] >= -SENSOR_SENSITIVITY && event.values[0] <= SENSOR_SENSITIVITY) {
                startVoice(null);
                speaker.readText("");
            }
        }


    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
