package com.souschef.sork.sous_chef;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class TutorialActivity extends AppCompatActivity implements SensorEventListener {

    // Sensor
    private SensorManager sensorManager;
    private Sensor proximitySensor;

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

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            if (event.values[0] >= -CookingActivity.SENSOR_SENSITIVITY && event.values[0] <= CookingActivity.SENSOR_SENSITIVITY) {
                finish();
            }
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void close(View view) {
        finish();
    }
}
