package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class Dashboard extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    RecyclerView recyclerView;
    Adapter adapter;
    List<Habits> habits;
    ImageView errorImg;
    TextView noData;

    FloatingActionButton btnAddNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        HabitDatabase db = new HabitDatabase(this);
        habits = db.getHabits();
        recyclerView = findViewById(R.id.listOfHabits);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this,habits );
        recyclerView.setAdapter(adapter);

        errorImg = findViewById(R.id.imageViewError);
        noData = findViewById(R.id.textViewNoData);



        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        btnAddNew = findViewById(R.id.btn_addnew);

        navigationView.setItemIconTintList(null);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);


        //Empty Dashboard
        if (adapter.getItemCount() != 0){
            errorImg.setVisibility(View.INVISIBLE);
            noData.setVisibility(View.INVISIBLE);
        }



        //For menu drawer
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        navigationView.getMenu().getItem(0).setChecked(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item ) {
                drawerLayout.closeDrawer(GravityCompat.START);
                switch (item.getItemId()){

                    case R.id.my_habits:
                        break;
                    case R.id.tutorial:
                        Intent help = new Intent(Dashboard.this, Help.class); //Calling new Activity
                        startActivity(help);
                        break;
                    case R.id.about:
                        Intent about = new Intent(Dashboard.this, About.class); //Calling new Activity
                        startActivity(about);
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
                finish();
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