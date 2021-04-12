package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class About extends AppCompatActivity {

    Button btnBack, btnPrivacy, btnUse ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);



        btnUse = findViewById(R.id.btn_termsOfUse);
        btnPrivacy = findViewById(R.id.btn_privacy);
        btnBack = findViewById(R.id.btn_back);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),  "User will only have access to their data.", Toast.LENGTH_SHORT).show();
            }
        });

        btnUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),  "As this app is a FYP project, commercial use is prohibited.   ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}