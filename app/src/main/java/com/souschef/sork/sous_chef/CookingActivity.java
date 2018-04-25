package com.souschef.sork.sous_chef;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import ernestoyaquello.com.verticalstepperform.*;
import ernestoyaquello.com.verticalstepperform.interfaces.VerticalStepperForm;

public class CookingActivity extends AppCompatActivity implements VerticalStepperForm, Commands{

    private VerticalStepperFormLayout verticalStepperForm;
    private EditText name;

    private static Speaker speaker;
    private static RecipeLite recipe;
    private int stepCompleted = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooking);

        if(speaker == null) {
            speaker = new Speaker(this);
        }

        recipe = (RecipeLite) getIntent().getExtras().getSerializable(RecipeChooserActivity.PlaceholderFragment.RECIPE_LITE);
        recipe.instructions.add(0, "Start");
        recipe.instructionsDescription.add(0, "Start");

        TextView recipeTitle = (TextView) findViewById(R.id.recipe);
        recipeTitle.setText(recipe.name);

        String[] instructions = new String[recipe.instructions.size()];
        instructions = recipe.instructions.toArray(instructions);

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
    }

    @Override
    public View createStepContentView(int stepNumber) {
        View view = null;
        LayoutInflater inflater = LayoutInflater.from(getBaseContext());
        if(stepNumber == 0) {
            view = (LinearLayout) inflater.inflate(R.layout.empty, null, false);
        } else {
            view = (LinearLayout) inflater.inflate(R.layout.cooking_instruction_layout, null, false);
            TextView description = (TextView) view.findViewById(R.id.description);
            if(description != null) {
                description.setText(recipe.instructionsDescription.get(stepNumber));
            }
        }
        return view;
    }

    @Override
    public void onStepOpening(int stepNumber) {
        if(stepNumber != 0 && stepNumber < recipe.instructions.size()) {
            String instruction = recipe.instructionsDescription.get(stepNumber);
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
        // Do nothing?
    }

    // Commands

    @Override
    public void next() {

    }

    @Override
    public void previous() {

    }

    @Override
    public void repeat() {

    }

    @Override
    public void start() {

    }

    @Override
    public void startTimer() {

    }

    @Override
    public void pause() {

    }
}
