package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class Dashboard extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;

    FloatingActionButton btnAddNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        btnAddNew = findViewById(R.id.btn_addnew);

        navigationView.setItemIconTintList(null);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);



        //For menu drawer
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawer(GravityCompat.START);
                switch (item.getItemId()){

                    case R.id.my_habits:
                        Toast.makeText(Dashboard.this, " My Habits Clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.tutorial:
                        Toast.makeText(Dashboard.this, " Tutorial Clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.about:
                        Toast.makeText(Dashboard.this, " About Clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.support:
                        Intent support = new Intent(Dashboard.this, SupportInfo.class); //Calling new Activity
                        startActivity(support);
                        break;
                }

                return true;
            }
        });


        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addnew = new Intent(Dashboard.this, AddHabit.class); //Calling new Activity
                startActivity(addnew);
            }
        });
    }

    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){

            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

}