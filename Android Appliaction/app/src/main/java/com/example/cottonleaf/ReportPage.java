package com.example.cottonleaf;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ReportPage extends AppCompatActivity {

    EditText reportTitleEditText;
    EditText reportDescriptionEditText;
    Button submitReportButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_page);

        reportTitleEditText = findViewById(R.id.reportTitleEditText);
        reportDescriptionEditText = findViewById(R.id.reportEditText);
        submitReportButton = findViewById(R.id.submitReportButton);

        submitReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reportTitle = reportTitleEditText.getText().toString();
                String reportDescription = reportDescriptionEditText.getText().toString();

                // Save report to Firestore
                saveReportToFirestore(reportTitle, reportDescription);
            }
        });

        Button profilebutton = findViewById(R.id.profile);
        profilebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReportPage.this, ProfilePage.class);
                startActivity(intent);
            }
        });

        Button homebutton = findViewById(R.id.home);
        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReportPage.this, HomePage.class);
                startActivity(intent);
            }
        });

        Button takephotobutton = findViewById(R.id.takephoto);
        takephotobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReportPage.this, CameraPage.class);
                startActivity(intent);
            }
        });

        Button resultsbutton = findViewById(R.id.results);
        resultsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReportPage.this, ResultsPage.class);
                startActivity(intent);
            }
        });
    }

    private void saveReportToFirestore(String reportTitle, String reportDescription) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String userId = auth.getCurrentUser().getUid();

        Map<String, Object> report = new HashMap<>();
        report.put("title", reportTitle);
        report.put("description", reportDescription);
        report.put("userId", userId);

        db.collection("reports")
                .add(report)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        // Report added successfully
                        Toast.makeText(ReportPage.this, "Report submitted successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Error adding report
                        Toast.makeText(ReportPage.this, "Failed to submit report", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
