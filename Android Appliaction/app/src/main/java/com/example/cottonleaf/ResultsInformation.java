package com.example.cottonleaf;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
public class ResultsInformation extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_information);

        Button resultsbutton = findViewById(R.id.results);
        resultsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultsInformation.this, ResultsPage.class);
                startActivity(intent);
            }
        });

        Button profilebutton = findViewById(R.id.profile);
        profilebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultsInformation.this, ProfilePage.class);
                startActivity(intent);
            }
        });

        Button homebutton = findViewById(R.id.home);
        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultsInformation.this, HomePage.class);
                startActivity(intent);
            }
        });


        Button takephotobutton = findViewById(R.id.takephoto);
        takephotobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultsInformation.this, CameraPage.class);
                startActivity(intent);
            }
        });

    Button settingsbutton = findViewById(R.id.settings);
    settingsbutton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ResultsInformation.this, Settings.class);
            startActivity(intent);
        }
    });

        Button Backbutton = findViewById(R.id.Backbtn);
        Backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultsInformation.this, ResultsPage.class);
                startActivity(intent);
            }
        });



}
}


