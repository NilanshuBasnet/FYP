package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.telephony.mbms.StreamingServiceInfo;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class habit_details extends AppCompatActivity {

    Button btnBack;
    int totalCountInt, doneCountInt;
    TextView titleText, countCounter, completionRate, counterName, sessionStat, checkIns, sessionCount;
    String id,title, totalCount,countName, trackingStatus, habitId,habitStat, temp;
    String stat = "OFF", checkin, counter, date;
    String Status = "Not Achieved";
    String Note = "No Note";
    public String CounterCount;
    ProgressBar completionTracker;
    Button btnIncrease, btnDecrease, btnDelete, btnSessionTracking, btnSaveD, btnNoteSave;
    DatabaseHelper db;
    Dialog dialog;
    CalendarView calendarView;
    String dateFormat, statData, checkinData,noteData ;

    @Override
    public void onBackPressed()
    {
        goToMain();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_details);

        dialog = new Dialog(this);






        habitId= getIntent().getStringExtra("id");
        titleText = findViewById(R.id.txtSessionTrack);
        countCounter = findViewById(R.id.textViewCount);
        counterName = findViewById(R.id.textViewCountName);
        sessionCount = findViewById(R.id.txtSessionCount);
        completionRate = findViewById(R.id.textViewCompletionRate);
        checkIns = findViewById(R.id.textViewCheckins);
        sessionStat = findViewById(R.id.txtSessionStat);
        completionTracker = findViewById(R.id.progressBar2);



        calendarView = findViewById(R.id.calendarView);

        btnIncrease = findViewById(R.id.btnCounterIncrease);
        btnDecrease = findViewById(R.id.btnCounterDecrease);
        btnDelete = findViewById(R.id.btnDelete);
        btnSessionTracking = findViewById(R.id.btnStartSession);
        btnSaveD = findViewById(R.id.btnSaveDetails);
        btnNoteSave = findViewById(R.id.btnNoteSave);


        db = new DatabaseHelper(this);

        getIntentData();

        Cursor reslt = db.countCheckin(habitId);
        while (reslt.moveToNext()){
            checkinData= reslt.getString(0);

        }
        checkIns.setText(String.valueOf(checkinData));


        CounterCount = countCounter.getText().toString();
        checkin = checkIns.getText().toString();


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String monthName = null;
                if(month == 0){
                    monthName = "January";
                } else if(month == 1){
                    monthName = "February";
                } else if(month == 2){
                    monthName = "March";
                } else if(month == 3){
                    monthName = "April";
                } else if(month == 4){
                    monthName = "May";
                } else if(month == 5){
                    monthName = "June";
                } else if(month == 6){
                    monthName = "July";
                } else if(month == 7){
                    monthName = "August";
                } else if(month == 8){
                    monthName = "September";
                } else if(month == 9){
                    monthName = "October";
                } else if(month == 10){
                    monthName = "November";
                } else if(month == 11){
                    monthName = "December";
                }
                
                date = dayOfMonth + " "+ monthName + " " + year;

                statData = "No Data";
                noteData="Empty";


                int monthCorrection = month + 1;
                temp = dayOfMonth + String.valueOf(monthCorrection) + year ;
                searchCalData();

                openDialog();


            }
        });

        dateFormat = new SimpleDateFormat("dMyyyy", Locale.getDefault()).format(new Date());

        //Save Details
        btnSaveD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(totalCountInt == doneCountInt){
                    Status = "Achieved";
                }

                db.deletePreviousData(id);
                db.CalDeletePreviousData(id,dateFormat);
                Boolean insertHabitDetail = db.insertHabitDetailData(Integer.valueOf(id), String.valueOf(sessionCount.getText()),CounterCount);
                Boolean CalInsertHabitDetail = db.insertCalendarHabitDetail(Integer.valueOf(id),dateFormat, Status, Note);
                goToMain();
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
                goToMain();
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
                finish();
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
        totalCountInt = Integer.parseInt(totalCount);
        doneCountInt = Integer.parseInt(counter);
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

    //Getting Data of table 3
    public void searchCalData(){
        Cursor result = db.getCalendarHabitDetail(habitId,temp);
        while (result.moveToNext()){
            statData= result.getString(3);
            noteData = result.getString(4);
        }

    }




    //Dialog for Calendar View
    private void openDialog(){
        dialog.setContentView(R.layout.calendar_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView title = dialog.findViewById(R.id.txtDate);
        TextView statusAns = dialog.findViewById(R.id.txtStatusAnswer);
        TextView noteAns = dialog.findViewById(R.id.txtNoteAns);
        title.setText(date);
        statusAns.setText(String.valueOf(statData));
        noteAns.setText(String.valueOf(noteData));



        Button btnSaveNote = dialog.findViewById(R.id.btnNoteSave);
        btnSaveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }


}