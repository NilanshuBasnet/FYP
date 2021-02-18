package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 5000; //SplashScreen for 5 seconds

    //Variables
    ImageView image;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        //Declaring Image
        image = findViewById(R.id.imageView);
        text = findViewById(R.id.splashText);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, Dashboard.class); //Calling new Activity
                startActivity(intent);
                finish();  //prevent returning to splash screen when back pressed
            }
        }, SPLASH_SCREEN);
    }
}