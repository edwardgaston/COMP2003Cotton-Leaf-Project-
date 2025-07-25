package com.example.cottonleaf;
import android.media.ExifInterface;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.graphics.Matrix;
import java.io.IOException;


public class displayimage extends AppCompatActivity {

    private ImageView selectedPhotoImageView; // ImageView for displaying image from URI
    private ImageView cameraimageView; // ImageView for displaying image from file path

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.displayimage);

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
            }
        }
    }
}

