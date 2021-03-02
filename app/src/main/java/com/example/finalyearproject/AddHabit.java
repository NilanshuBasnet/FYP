package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class AddHabit extends AppCompatActivity {

    TextView txtNotify;
    EditText habitName, goalCount, countName;
    Button btnBack, btnSaveHabit;
    ToggleButton btnSun, btnMon, btnTue, btnWed, btnThu, btnFri, btnSat;
    Switch session;
    Boolean sessionTracking = false;
    Switch Notify;
    int Nhour,Nminute;
    String time, shabitName, sgoalCount, scountName, newTime;
    String sessions = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);

        //Shows Notification time set by user
        txtNotify = findViewById(R.id.textViewNotification);




        //Session Tracking Switch
        session = (Switch) findViewById(R.id.switchSession);
        session.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sessionTracking = true;
                    sessions = "1";

                }else {
                    sessionTracking = false;
                    sessions = "0";
                    Toast.makeText(getApplicationContext(),  "Session Tracking Turned Off ", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //Notification Switch
        Notify = (Switch) findViewById(R.id.switchRemainder);
        Notify.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //When Checked
                    //Time Picker
                    Calendar calendar = Calendar.getInstance();
                    int hour = calendar.get(Calendar.HOUR_OF_DAY);
                    int min = calendar.get(Calendar.MINUTE);
                    TimePickerDialog timePickerDialog = new TimePickerDialog(AddHabit.this, R.style.ThemeOverlay_AppCompat_Dialog, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            Calendar c = Calendar.getInstance();
                            c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            c.set(Calendar.MINUTE, minute);
                            c.setTimeZone(TimeZone.getDefault());
                            SimpleDateFormat format = new SimpleDateFormat("k:mm a"); //k-Hour in day (1-24) m-Minute in hour a-Am/pm marker
                            time = format.format(c.getTime());
                            txtNotify.setText("Set for " + time);
                        }
                    }, hour, min, false);
                    timePickerDialog.show();

                    timePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            // Actions on clicks outside the dialog.
                            Notify.setChecked(false);
                        }
                    });



                }else {
                    //When Unchecked
                    txtNotify.setText("");
                    Toast.makeText(getApplicationContext(),  "Notification Turned OFF ", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnBack = findViewById(R.id.btnBack);
        btnSaveHabit = findViewById(R.id.btnSaveHabit);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Save button
        btnSaveHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                habitName = findViewById(R.id.UserInputHabitName);
                goalCount = findViewById(R.id.userInputGoal);
                countName = findViewById(R.id.userInputCount);

                shabitName = habitName.getText().toString();
                sgoalCount = goalCount.getText().toString();
                scountName = countName.getText().toString();
                newTime = time.toString();

                btnSun = findViewById(R.id.toggleButtonSun);
                btnMon = findViewById(R.id.toggleButtonMon);
                btnTue = findViewById(R.id.toggleButtonTue);
                btnWed = findViewById(R.id.toggleButtonWed);
                btnThu = findViewById(R.id.toggleButtonThu);
                btnFri = findViewById(R.id.toggleButtonFri);
                btnSat = findViewById(R.id.toggleButtonSat);




                //Checking whether frequency selected or not
                Boolean frequencyValid = false;
                if(btnSun.isChecked() || btnMon.isChecked() || btnTue.isChecked() || btnWed.isChecked() || btnThu.isChecked() || btnFri.isChecked() || btnSat.isChecked()){

                    frequencyValid = true;
                }


                //Validation
                if (TextUtils.isEmpty(shabitName) || TextUtils.isEmpty(sgoalCount) || TextUtils.isEmpty(scountName) || frequencyValid == false ){

                    Toast.makeText(getApplicationContext(),  "Please fill all the required fields! ", Toast.LENGTH_SHORT).show();

                } else {
                    storeData();
                    onBackPressed();
                    Toast.makeText(getApplicationContext(),  "Habit Added ", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void storeData(){
        Habits habits = new Habits(shabitName,sgoalCount,scountName,sessions,newTime);
        HabitDatabase db = new HabitDatabase(this);
        db.addHabit(habits);
    }

}