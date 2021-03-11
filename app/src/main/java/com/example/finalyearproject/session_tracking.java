package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

public class session_tracking extends AppCompatActivity {

    private Chronometer chronometer;
    private long pauseOffset;
    Button btnBack;
    private boolean running;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_tracking);

        //back button
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                pauseChronometer(v);
            }
        });


        //chronometer
        chronometer = findViewById(R.id.chronometer);
        chronometer.setTypeface(ResourcesCompat.getFont(this, R.font.cambay_bold));
    }

    public void startChronometer(View v){
        if(!running){
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running =true;
        }
    }

    public void pauseChronometer(View v){
        if(running){
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running =false;
        }
    }


    public void resetChronometer(View v){

        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
    }
}