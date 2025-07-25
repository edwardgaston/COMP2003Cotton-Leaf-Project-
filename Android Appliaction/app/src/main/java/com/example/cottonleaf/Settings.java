package com.example.cottonleaf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.widget.TextView;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    Spinner LanguageSpinner;
    Spinner LocationSpinner;
    List<String> Languages;
    List<String> Locations;
    ArrayAdapter<String> adapter;
    private Button HistoryButton;
    private static final int MY_PERMISSIONS_REQUEST_POST_NOTIFICATIONS = 69;
    private static final int NOTIFICATION_ID = 2;
    private static final String CHANNEL_ID = "my_channel_id";
    private TextView DetectedLeafs;
    private SharedPreferences LeafCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        LeafCount = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        if (!LeafCount.contains("leafCount")) {
            SharedPreferences.Editor editor = LeafCount.edit();
            editor.putInt("leafCount", 1045);
            editor.apply();
        }

        int leafCounter = LeafCount.getInt("leafCount", 0);


        updateLeafCountTextView(leafCounter);



        Button NotiButton = findViewById(R.id.NotiButton);
        NotiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NotificationCompat.Builder builder = new NotificationCompat.Builder(Settings.this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle("Cotton Leaf")
                        .setContentText("Notifications Have Been Enabled")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setAutoCancel(true);

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(Settings.this);
                if (ActivityCompat.checkSelfPermission(Settings.this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Settings.this, new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, MY_PERMISSIONS_REQUEST_POST_NOTIFICATIONS);
                }
                else {
                    notificationManager.notify(NOTIFICATION_ID, builder.build());
                    }

            }
        });

        LanguageSpinner = findViewById(R.id.LanguageSpinner);
        Languages = Arrays.asList("English", "Urdu");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        LanguageSpinner.setAdapter(adapter);

        LanguageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLanguage = (String) parent.getItemAtPosition(position);
                SharedPreferences Language_prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String savedLanguage = Language_prefs.getString("language", "");

                // Check if the selected language is different from the saved language
                if (!selectedLanguage.equals(savedLanguage)) {
                    if (selectedLanguage.equals("Urdu")) {
                        setLocale("ur"); // "ur" is the language code for Urdu
                    } else {
                        setLocale(""); // Default to English
                    }

                    // Update the saved language in SharedPreferences
                    Language_prefs.edit().putString("language", selectedLanguage).apply();

                    // Display toast message for language change
                    Toast.makeText(Settings.this, "Language changed to " + selectedLanguage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        LocationSpinner = findViewById(R.id.LocationSpinner);
        Locations = Arrays.asList("England", "Pakistan");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Locations);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        LocationSpinner.setAdapter(adapter);

        SharedPreferences Language_prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String savedLanguage = Language_prefs.getString("language", "");
        if (!savedLanguage.isEmpty()) {
            setLocale(savedLanguage);
            int position = Languages.indexOf(savedLanguage);
            if (position != -1) {
                LanguageSpinner.setSelection(position);
            }
        }

        Button reportbutton = findViewById(R.id.Report);
        reportbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, ReportPage.class);
                startActivity(intent);
            }
        });

        Button profilebutton = findViewById(R.id.profile);
        profilebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, ProfilePage.class);
                startActivity(intent);
            }
        });

        Button homebutton = findViewById(R.id.home);
        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, HomePage.class);
                startActivity(intent);
            }
        });

        Button takephotobutton = findViewById(R.id.takephoto);
        takephotobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, CameraPage.class);
                startActivity(intent);
            }
        });

        Button resultsbutton = findViewById(R.id.results);
        resultsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.this, ResultsPage.class);
                startActivity(intent);
            }
        });

    }
    private void updateLeafCountTextView(int leafCounter) {
        DetectedLeafs = findViewById(R.id.DiseasedLeavesDetected);
        DetectedLeafs.setText("@string/DetectedLeafs" + leafCounter);
    }
    private void setLocale(String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }
}