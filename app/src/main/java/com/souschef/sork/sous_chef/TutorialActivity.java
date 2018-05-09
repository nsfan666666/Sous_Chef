package com.souschef.sork.sous_chef;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class TutorialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        ImageView image = (ImageView) findViewById(R.id.tutorialImage);
        switch (StartTutorial.currentTutorial) {
            case StartTutorial.RECIPE_CHOOSER:
                // TODO Load the correct image
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tut1);
                image.setImageBitmap(bitmap);
                break;
            case StartTutorial.COOKING:
                // TODO Load the correct image

                break;
        }
    }

    public void close(View view) {
        finish();
    }
}
