package com.souschef.sork.sous_chef;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.v7.app.AppCompatActivity;

import java.util.Locale;


public class Speaker extends AppCompatActivity{

    TextToSpeech toSpeech;

    public Speaker(Context context){
        toSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                toSpeech.setLanguage(Locale.US);
            }
        });

    }
    public void readText(String s){
        toSpeech.speak(s, TextToSpeech.QUEUE_FLUSH, null);
    }

    public boolean isSpeaking(){
        return toSpeech.isSpeaking();
    }



}