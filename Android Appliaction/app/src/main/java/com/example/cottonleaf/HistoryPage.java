package com.example.cottonleaf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class HistoryPage extends AppCompatActivity {

    ArrayList<String> imagePaths = GlobalVariables.getInstance().getImagePaths();
    private int currentIndex = 0;
    private ImageView cameraimageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_page);

        cameraimageView = findViewById(R.id.cameraimageView);

        Intent intent = getIntent();
        if (intent != null) {
            imagePaths = intent.getStringArrayListExtra("imagePaths");
        }

        if (imagePaths != null && !imagePaths.isEmpty()) {
            displayImage(currentIndex);
        } else {
            Toast.makeText(this, "No images to display", Toast.LENGTH_SHORT).show();
        }

        Button profilebutton = findViewById(R.id.profile);
        profilebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryPage.this, ProfilePage.class);
                startActivity(intent);
            }
        });

        Button lastScanButton = findViewById(R.id.LastScan);
        lastScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentIndex > 0) {
                    currentIndex--;
                    displayImage(currentIndex);
                } else {
                    Toast.makeText(HistoryPage.this, "This is the first image", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button nextScanButton = findViewById(R.id.NextScan);
        nextScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentIndex < imagePaths.size() - 1) {
                    currentIndex++;
                    displayImage(currentIndex);
                } else {
                    Toast.makeText(HistoryPage.this, "This is the last image", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button homebutton = findViewById(R.id.home);
        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryPage.this, HomePage.class);
                startActivity(intent);
            }
        });

        Button takephotobutton = findViewById(R.id.takephoto);
        takephotobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryPage.this, CameraPage.class);
                startActivity(intent);
            }
        });

        Button settingsbutton = findViewById(R.id.settings);
        settingsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryPage.this, Settings.class);
                startActivity(intent);
            }
        });

        Button resultsbutton = findViewById(R.id.results);
        resultsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryPage.this, ResultsPage.class);
                startActivity(intent);
            }
        });
    }

    private void displayImage(int index) {
        if (imagePaths != null && !imagePaths.isEmpty()) {
            String imagePath = imagePaths.get(index);
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            // Check the orientation of the image and rotate if necessary
            try {
                ExifInterface exif = new ExifInterface(imagePath);
                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                int rotation = 0;
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        rotation = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        rotation = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        rotation = 270;
                        break;
                }
                // Rotate the bitmap
                if (rotation != 0) {
                    Matrix matrix = new Matrix();
                    matrix.postRotate(rotation);
                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            // Set the rotated bitmap to the ImageView
            cameraimageView.setImageBitmap(bitmap);
        }
    }

}
