package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class habit_details extends AppCompatActivity {

    Button btnBack;
    TextView titleText;
    String title, totalCount, trackingStatus;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_details);

        titleText = findViewById(R.id.textTitle);
        getIntentData();





        //back button
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    void getIntentData(){
        if(getIntent().hasExtra("title") && getIntent().hasExtra("totalCount")  && getIntent().hasExtra("trackingStatus")){


            title = getIntent().getStringExtra("title");
            totalCount = getIntent().getStringExtra("totalCount");
            trackingStatus = getIntent().getStringExtra("trackingStatus");

            titleText.setText(title);

        }else{
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
    }
}