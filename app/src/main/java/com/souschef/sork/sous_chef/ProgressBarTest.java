package com.souschef.sork.sous_chef;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import ernestoyaquello.com.verticalstepperform.*;
import ernestoyaquello.com.verticalstepperform.interfaces.VerticalStepperForm;

public class ProgressBarTest extends AppCompatActivity implements VerticalStepperForm{

    private VerticalStepperFormLayout verticalStepperForm;
    private EditText name;

    private static RecipeLite recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_bar_test);

        recipe = (RecipeLite) getIntent().getExtras().getSerializable(RecipeChooserActivity.PlaceholderFragment.RECIPE_LITE);


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
        view = (LinearLayout) inflater.inflate(R.layout.instruction_layout, null, false);
        return view;
    }

    @Override
    public void onStepOpening(int stepNumber) {
        verticalStepperForm.setStepAsCompleted(stepNumber);
    }

    @Override
    public void sendData() {
        // Do nothing?
    }
}
