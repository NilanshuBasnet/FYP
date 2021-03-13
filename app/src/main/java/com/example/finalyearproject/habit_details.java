package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.telephony.mbms.StreamingServiceInfo;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class habit_details extends AppCompatActivity {

    Button btnBack;
    TextView titleText, countCounter, completionRate, counterName, sessionStat, checkIns, sessionCount;
    String id,title, totalCount,countName, trackingStatus, habitId,habitStat;
    String stat = "OFF", checkin, counter, sessionCounts;
    public String CounterCount;
    ProgressBar completionTracker;
    Button btnIncrease, btnDecrease, btnDelete, btnSessionTracking, btnSaveD;
    DatabaseHelper db;


    @Override
    public void onBackPressed()
    {
        goToMain();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_details);






        habitId= getIntent().getStringExtra("id");
        titleText = findViewById(R.id.txtSessionTrack);
        countCounter = findViewById(R.id.textViewCount);
        counterName = findViewById(R.id.textViewCountName);
        sessionCount = findViewById(R.id.txtSessionCount);
        completionRate = findViewById(R.id.textViewCompletionRate);
        checkIns = findViewById(R.id.textViewCheckins);
        sessionStat = findViewById(R.id.txtSessionStat);
        completionTracker = findViewById(R.id.progressBar2);
        btnIncrease = findViewById(R.id.btnCounterIncrease);
        btnDecrease = findViewById(R.id.btnCounterDecrease);
        btnDelete = findViewById(R.id.btnDelete);
        btnSessionTracking = findViewById(R.id.btnStartSession);
        btnSaveD = findViewById(R.id.btnSaveDetails);

        db = new DatabaseHelper(this);



        getIntentData();





        CounterCount = countCounter.getText().toString();
        checkin = checkIns.getText().toString();


        //Save Details
        btnSaveD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deletePreviousData(id);
                Boolean insertHabitDetail = db.insertHabitDetailData(Integer.valueOf(id), String.valueOf(sessionCount.getText()),CounterCount);
                onBackPressed();
                Toast.makeText(habit_details.this, "Data Saved", Toast.LENGTH_SHORT).show();
            }
        });

        searchData();
        counterData();




        //back button
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        //Counter Increase
        btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String c = countCounter.getText().toString();
                int ct = Integer.parseInt(c);
                int totCoun = Integer.parseInt(totalCount);
                String c1;

                if(ct < totCoun){
                    ct = ct +1;
                    countCounter.setText(String.valueOf(ct));
                    CounterCount = String.valueOf(ct);
                    counterData();;
                }
                else{
                    Toast.makeText(habit_details.this, "Target Completed", Toast.LENGTH_SHORT).show();
                }


            }
        });

        //Counter Decrease
        btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String c = countCounter.getText().toString();
                int ct = Integer.parseInt(c);
                int totCoun = Integer.parseInt(totalCount);

                if(ct > 0){
                    ct = ct - 1;
                    countCounter.setText(String.valueOf(ct));
                    CounterCount = String.valueOf(ct);
                    counterData();
                }
                else{
                    Toast.makeText(habit_details.this, "Cannot decrease more!", Toast.LENGTH_SHORT).show();
                }


            }
        });

        //Delete Data from database
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String idTXT = habitId;
                Boolean checkDeletedata = db.deleteHabitData(habitId);
                goToMain();
                if(checkDeletedata==true)
                    Toast.makeText(habit_details.this, "Habit Deleted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(habit_details.this, "Habit Not Deleted", Toast.LENGTH_SHORT).show();

            }
        });


        //Session Tracking
        btnSessionTracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent session = new Intent(habit_details.this, session_tracking.class); //Calling new Activity
                session.putExtra("id",id);
                startActivity(session);
            }
        });






    }

    public void getIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("title") && getIntent().hasExtra("totalCount")  && getIntent().hasExtra("trackingStatus") && getIntent().hasExtra("countName")){


            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            totalCount = getIntent().getStringExtra("totalCount");
            trackingStatus = getIntent().getStringExtra("trackingStatus");
            countName = getIntent().getStringExtra("countName");

            titleText.setText(title);
            counterName.setText(countName);


        }else{
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
    }

    public void counterData(){
        counter = countCounter.getText().toString();
        int totalCountInt = Integer.parseInt(totalCount);
        int doneCountInt = Integer.parseInt(counter);
        float percentage = ((float)doneCountInt / (float)totalCountInt) * 100;
        String percentageStr = ((int) percentage) + "%";

        completionRate.setText(percentageStr);
        completionTracker.setProgress((int) percentage);

        if(Integer.parseInt(trackingStatus) == 0){
            String temp = "Off";
            sessionStat.setText(temp);
            btnSessionTracking.setVisibility(View.GONE);
            sessionCount.setVisibility(View.GONE);
        }else{
            String temp = "On";
            sessionStat.setText(temp);
        }


        if(totalCountInt == doneCountInt){
            habitStat = "Completed";
        }






    }

    private void goToMain() {
        Intent m = new Intent(this,Dashboard.class);
        m.putExtra("habitStat", habitStat);
        startActivity(m);
        finish();
    }

    //Getting Data of table 2
    public void searchData(){
        Cursor result = db.getHabitDetail(habitId);
        while (result.moveToNext()){
            sessionCount.setText(result.getString(2));
            countCounter.setText(result.getString(3));
        }

    }


}