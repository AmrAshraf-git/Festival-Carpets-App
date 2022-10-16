package com.example.festivalcarpet;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button continueUser;
    private Button continueGuest;
    private SharedPreferences sharedPreferences;
    private boolean isDarkModeOn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialization();


        sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn", false);
        if (isDarkModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }


        continueUser.setOnClickListener(v -> {
            Intent intent=new Intent(MainActivity.this, EntryPageActivity.class);
            startActivity(intent);
        });

        continueGuest.setOnClickListener(v -> {
            Intent intent=new Intent(MainActivity.this, NavControllerActivity.class);
            startActivity(intent);
        });
    }



    public void initialization() {
        continueUser =findViewById(R.id.main_btn_continue);
        continueGuest =findViewById(R.id.main_btn_guest);
    }
}