package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddHabit extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    EditText habitName, goalCount, countName;
    Button btnBack, btnSaveHabit;
    Switch Notify;
    int Nhour,Nminute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);
        //this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Notify = (Switch) findViewById(R.id.switchRemainder);
        Notify.setOnCheckedChangeListener(this);


        btnBack = findViewById(R.id.btnBack);
        btnSaveHabit = findViewById(R.id.btnSaveHabit);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnSaveHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),  "Save Pressed ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            // do something when check is selected
            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    AddHabit.this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            //Initializing Hour and minute
                            Nhour = hourOfDay;
                            Nminute = minute;
                            //Store hour and minute
                            String time = Nhour + ":" + Nminute;
                            //24 hour time format
                            SimpleDateFormat f24hour = new SimpleDateFormat(
                                    "HH:mm"
                            );
                            try {
                                Date date = f24hour.parse(time);
                                //12 hour time format
                                SimpleDateFormat f12hour = new SimpleDateFormat("hh:mm aa");
                                //Display Notifcation time
                                Toast.makeText(getApplicationContext(),  "Noification Set For: " + f12hour.format(date), Toast.LENGTH_SHORT).show();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    },12,0,false

            );
            timePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    // Actions on clicks outside the dialog.
                    Notify.setChecked(false);
                }
            });

            //set transparent background
            timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //Show Dialog
            timePickerDialog.show();
        } else {
            //do something when unchecked
            Toast.makeText(getApplicationContext(),  "Noification Turned Off ", Toast.LENGTH_SHORT).show();
        }
    }
}