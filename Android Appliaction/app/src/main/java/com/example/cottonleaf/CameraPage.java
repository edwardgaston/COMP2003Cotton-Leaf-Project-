package com.example.cottonleaf;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.AspectRatio;
import androidx.camera.core.Camera;
import java.io.File;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class CameraPage extends AppCompatActivity {
    private Button ProfileButton;
    private ArrayList<String> imagePaths = new ArrayList<>();
    ImageButton capture, toggleflash, flipcamera;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Button choosePhotoButton;
    private PreviewView previewView;
    int cameraFacing = CameraSelector.LENS_FACING_BACK;
    private final ActivityResultLauncher<String> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
        @Override
        public void onActivityResult(Boolean result) {
            if (result) {
                startCamera(cameraFacing);
            }
        }
    });

    private final ActivityResultLauncher<String> photoLibraryPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
        @Override
        public void onActivityResult(Boolean isGranted) {
            if (isGranted) {
                openGallery();
            } else {
                Toast.makeText(CameraPage.this, "Permission denied. Cannot access the photo library.", Toast.LENGTH_SHORT).show();
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_page);



        Button homebutton = findViewById(R.id.home);
        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CameraPage.this, HomePage.class);
                startActivity(intent);
            }
        });

        Button settingsbutton = findViewById(R.id.settings);
        settingsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CameraPage.this, Settings.class);
                startActivity(intent);
            }
        });

        Button resultsbutton = findViewById(R.id.results);
        resultsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CameraPage.this, ResultsPage.class);
                startActivity(intent);
            }
        });


        ProfileButton = findViewById(R.id.profile);
        ProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CameraPage.this, ProfilePage.class);
                startActivity(intent);
            }
        });


        choosePhotoButton = findViewById(R.id.choosePhotoButton);
        choosePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });



        previewView = findViewById(R.id.cameraPreview);
        capture = findViewById(R.id.capture);
        toggleflash = findViewById(R.id.toggleflash);
        flipcamera = findViewById(R.id.flipcamera);




        // Request camera permission
        if (ContextCompat.checkSelfPermission(CameraPage.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            activityResultLauncher.launch(Manifest.permission.CAMERA);
        } else {
            startCamera(cameraFacing);
        }

        // Set onClickListener for choosePhotoButton
        choosePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Request permission to access the photo library
                if (ContextCompat.checkSelfPermission(CameraPage.this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED){
                    photoLibraryPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES);
                } else {
                    openGallery();
                }
            }
        });

        flipcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cameraFacing == CameraSelector.LENS_FACING_BACK){
                    cameraFacing = CameraSelector.LENS_FACING_FRONT;
                } else {
                    cameraFacing = CameraSelector.LENS_FACING_BACK;
                }
                startCamera(cameraFacing);
            }
        });

    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    // Declare context and uri variables for getPathFromUri method
    private Context context;
    private Uri uri;

    FileUtils fileUtils = new FileUtils();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Get the URI of the selected image
            uri = data.getData();

            // Convert URI to file path
            String imagePath = fileUtils.getPathFromUri(this, uri);

            // Check if conversion was successful
            if (imagePath != null) {
                // Add the file path to imagePaths ArrayList
                imagePaths.add(imagePath);

                // Intent to send the selected image to ResultsPage
                Intent resultsIntent = new Intent(CameraPage.this, ResultsPage.class);
                resultsIntent.putExtra("imagePath", imagePath);
                resultsIntent.putStringArrayListExtra("imagePaths", new ArrayList<>(imagePaths)); // Pass updated imagePaths
                startActivity(resultsIntent);
            } else {
                // Handle error if file path conversion failed
                Toast.makeText(this, "Failed to retrieve image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class FileUtils {
        // Method to get the file path from URI
        public String getPathFromUri(Context context, Uri uri) {
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
            if (cursor != null) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                String filePath = cursor.getString(columnIndex);
                cursor.close();
                return filePath;
            } else {
                return null;
            }
        }
    }







    public void startCamera(int cameraFacing) {
        int aspectRatio = aspectRatio(previewView.getWidth(), previewView.getHeight());
        ListenableFuture<ProcessCameraProvider> listenableFuture = ProcessCameraProvider.getInstance(this);
        listenableFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = (ProcessCameraProvider) listenableFuture.get();
                Preview preview = new Preview.Builder().setTargetAspectRatio(aspectRatio).build();

                ImageCapture imageCapture = new ImageCapture.Builder()
                        .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                        .setTargetRotation(getWindowManager().getDefaultDisplay().getRotation())
                        .build();

                CameraSelector cameraSelector = new CameraSelector.Builder().requireLensFacing(cameraFacing).build();
                cameraProvider.unbindAll();
                Camera camera = cameraProvider.bindToLifecycle(this,cameraSelector,preview,imageCapture);
                capture.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        takePicture(imageCapture);
                    }
                });
                toggleflash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setFlashIcon(camera);
                    }
                });

                preview.setSurfaceProvider(previewView.getSurfaceProvider());
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(this));
    }


    public void takePicture(ImageCapture imageCapture) {
        File file = new File(getFilesDir(), "captured_image.jpg");

        ImageCapture.OutputFileOptions outputFileOptions = new ImageCapture.OutputFileOptions.Builder(file).build();

        imageCapture.takePicture(outputFileOptions, Executors.newCachedThreadPool(), new ImageCapture.OnImageSavedCallback() {
            @Override
            public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                imagePaths.add(file.getAbsolutePath());

                // Intent to send the latest image to ResultsPage
                Intent resultsIntent = new Intent(CameraPage.this, ResultsPage.class);
                resultsIntent.putExtra("imagePath", file.getAbsolutePath());
                resultsIntent.putStringArrayListExtra("imagePaths", new ArrayList<>(imagePaths)); // Pass updated imagePaths
                startActivity(resultsIntent);
            }

            @Override
            public void onError(@NonNull ImageCaptureException exception) {
                Toast.makeText(CameraPage.this, "Failed to save image: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setFlashIcon(Camera camera) {
        if (camera.getCameraInfo().hasFlashUnit()) {
            if (camera.getCameraInfo().getTorchState().getValue() == 0){
                camera.getCameraControl().enableTorch(true);
                toggleflash.setImageResource(R.drawable.baseline_flash_off_24);
            } else {
                camera.getCameraControl().enableTorch(false);
                toggleflash.setImageResource(R.drawable.baseline_flash_on_24);
            }
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(CameraPage.this, "Flash is not available currently", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private int aspectRatio(int width, int height){
        double previewRatio = (double) Math.max(width, height) / Math.min(width, height);
        if (Math.abs(previewRatio - 4.0 / 3.0) <= Math.abs(previewRatio - 16.0 / 9.0)) {
            return AspectRatio.RATIO_4_3;
        }
        return  AspectRatio.RATIO_16_9;
    }
}