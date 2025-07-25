package com.example.cottonleaf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.io.IOException;
import java.util.ArrayList;

public class ResultsPage extends AppCompatActivity {

    private Button HistoryButton;
    private ImageView selectedPhotoImageView;
    private ImageView cameraimageView;
    private TextView ConfidenceString;
    private Object result;
    ArrayList<String> imagePaths = GlobalVariables.getInstance().getImagePaths();

    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_page);

        ConfidenceString = findViewById(R.id.Confidencescore);

        selectedPhotoImageView = findViewById(R.id.selectedPhotoImageView);
        cameraimageView = findViewById(R.id.cameraimageView);



        // Get the URI of the selected image from the intent
        Intent intent = getIntent();
        if (intent != null) {
            Uri imageUri = intent.getData();
            String imagePath = intent.getStringExtra("imagePath");

            // Check if the image URI is not null, and load it into selectedPhotoImageView
            if (imageUri != null) {
                selectedPhotoImageView.setImageURI(imageUri);
            }

            // Check if the image path is not null, and load it into cameraimageView
            if (imagePath != null) {
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
                    // Set the rotated bitmap to the ImageView
                    cameraimageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imagePaths.add(imagePath);
            }
        }

        //PYTHON PART
        if (! Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
            //Python.start(new AndroidPlatform(context), new PythonInitializationOptions.Builder().setPythonHome("python3.8").build());
        }

        // REPLACE WITH THE ID OF THE TEXTVIEW
        TextView textViewResult = findViewById(R.id.textViewResult);

        Python py = Python.getInstance();
        py.getModule("random");
        PyObject module = py.getModule("LeafDetection");
        //CALLS FUNCTION AND STORES RESULT
        //PyObject myFnCallValue = module.callAttr("prediction_code");
        PyObject myFnCallValue = module.callAttr("confidence");
        result = myFnCallValue.toJava(Object.class); // Convert Python object to Java object
        // Set the result to the TextView
        //textViewResult.setText(result.toString());
        updateconfidence();
        //add logic here for image box replaced w photo that is stored in gallery of phone on result
        //page. get multiple images showing in a scroll view on history page
        // find a way to store multiple images but make sure they are only the images obtained by user

        Button testButton = findViewById(R.id.TestBtn);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultsPage.this, ResultsInformation.class);
                startActivity(intent);
            }
        });


        HistoryButton = findViewById(R.id.HistoryButton);
        HistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent historyIntent = new Intent(ResultsPage.this, HistoryPage.class);
                historyIntent.putStringArrayListExtra("imagePaths", imagePaths);
                startActivity(historyIntent);
            }
        });




        Button profilebutton = findViewById(R.id.profile);
        profilebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultsPage.this, ProfilePage.class);
                startActivity(intent);
            }
        });

        Button homebutton = findViewById(R.id.home);
        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultsPage.this, HomePage.class);
                startActivity(intent);
            }
        });

        Button takephotobutton = findViewById(R.id.takephoto);
        takephotobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultsPage.this, CameraPage.class);
                startActivity(intent);
            }
        });

        Button settingsbutton = findViewById(R.id.settings);
        settingsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultsPage.this, Settings.class);
                startActivity(intent);
            }
        });
    }


    private void updateconfidence() {
        ConfidenceString.setText("@string/Confidencescore" + result);
    }
}