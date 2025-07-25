package com.example.cottonleaf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.Arrays;
import java.util.List;

public class HomePage extends AppCompatActivity {

    private Button ScanningButton, HistoryButton, ProfileButton;
    private ImageButton SettingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);



        Button takephotobutton = findViewById(R.id.takephoto);
        takephotobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, CameraPage.class);
                startActivity(intent);
            }
        });

        Button settingsbutton = findViewById(R.id.settings);
        settingsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, Settings.class);
                startActivity(intent);
            }
        });

        Button resultsbutton = findViewById(R.id.results);
        resultsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, ResultsPage.class);
                startActivity(intent);
            }
        });

        Button InfoButton = findViewById(R.id.InfoButton);
        InfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInfoPage();
            }
        });




        ProfileButton = findViewById(R.id.profile);
        ProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProfilePage();
            }
        });
    }


    public void openProfilePage() {
        Intent intent = new Intent(this, ProfilePage.class);
        startActivity(intent);
    }

    public void openSettingsPage() {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    public void openHistoryPage() {
        Intent intent = new Intent(this, HistoryPage.class);
        startActivity(intent);
    }

    public void openInfoPage() {
        Intent intent = new Intent(this, InformationPage.class);
        startActivity(intent);
    }


}