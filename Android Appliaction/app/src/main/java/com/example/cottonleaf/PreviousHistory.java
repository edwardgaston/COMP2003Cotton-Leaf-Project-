package com.example.cottonleaf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PreviousHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_history);

        Button profilebutton = findViewById(R.id.profile);
        profilebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.cottonleaf.PreviousHistory.this, ProfilePage.class);
                startActivity(intent);
            }
        });




        Button LastScan = findViewById(R.id.LastScan);
        LastScan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PreviousHistory.this, NextHistory.class);
                startActivity(intent);
            }
        });

        Button NextScan = findViewById(R.id.NextScan);
        NextScan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PreviousHistory.this, HistoryPage.class);
                startActivity(intent);
            }
        });


        Button homebutton = findViewById(R.id.home);
        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PreviousHistory.this, HomePage.class);
                startActivity(intent);
            }
        });

        Button takephotobutton = findViewById(R.id.takephoto);
        takephotobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PreviousHistory.this, CameraPage.class);
                startActivity(intent);
            }
        });

        Button settingsbutton = findViewById(R.id.settings);
        settingsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PreviousHistory.this, Settings.class);
                startActivity(intent);
            }
        });

        Button resultsbutton = findViewById(R.id.results);
        resultsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PreviousHistory.this, ResultsPage.class);
                startActivity(intent);
            }
        });


    }
}

