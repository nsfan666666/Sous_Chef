package com.souschef.sork.sous_chef;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long start = System.currentTimeMillis();
        setContentView(R.layout.activity_splash);
        Intent intent = new Intent(this, RecipeChooserActivity.class);
        while(System.currentTimeMillis()<start+3000){
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        startActivity(intent);
        finish();
    }
}
