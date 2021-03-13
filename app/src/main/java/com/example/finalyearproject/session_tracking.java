package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

public class session_tracking extends AppCompatActivity {

    private Chronometer chronometer;
    private long pauseOffset;
    Button btnBack, btnSaveSession;
    private boolean running;
    String sessionTime, id;
    TextView sessioncount;
    DatabaseHelper db;



    @Override
    public void onBackPressed()
    {
        goToMain();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_tracking);


        db = new DatabaseHelper(this);

        id = getIntent().getStringExtra("id");

        sessioncount = findViewById(R.id.txtSessionCount);

        //Save session
        btnSaveSession = findViewById(R.id.btnSaveData);
        btnSaveSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionTime = String.valueOf(chronometer.getText());
                Boolean updateSession = db.updateSession(id,sessionTime);
                goToMain();
            }
        });

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

    private void goToMain() {
        Intent m = new Intent(this,Dashboard.class);
        startActivity(m);
        finish();
    }

}