package com.example.cottonleaf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfilePage extends AppCompatActivity {

    private EditText currentPasswordEditText; // New text box for current password
    private EditText newPasswordEditText; // Text box for new password
    private Button confirmButton;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        currentPasswordEditText = findViewById(R.id.emailEditText); // Repurposed for current password
        newPasswordEditText = findViewById(R.id.passwordEditText); // For new password
        confirmButton = findViewById(R.id.ConfirmButton);
        logoutButton = findViewById(R.id.logoutButton);

        // Button actions for navigation
        Button takePhotoButton = findViewById(R.id.takephoto);
        takePhotoButton.setOnClickListener(view -> {
            Intent intent = new Intent(ProfilePage.this, CameraPage.class);
            startActivity(intent);
        });

        Button settingsButton = findViewById(R.id.settings);
        settingsButton.setOnClickListener(view -> {
            Intent intent = new Intent(ProfilePage.this, Settings.class);
            startActivity(intent);
        });

        Button resultsButton = findViewById(R.id.results);
        resultsButton.setOnClickListener(view -> {
            Intent intent = new Intent(ProfilePage.this, ResultsPage.class);
            startActivity(intent);
        });

        Button homeButton = findViewById(R.id.home);
        homeButton.setOnClickListener(view -> {
            Intent intent = new Intent(ProfilePage.this, HomePage.class);
            startActivity(intent);
        });

        confirmButton.setOnClickListener(view -> {
            String currentPassword = currentPasswordEditText.getText().toString().trim();
            String newPassword = newPasswordEditText.getText().toString().trim();

            if (currentPassword.isEmpty()) {
                showToast("Please enter your current password.");
                return;
            }

            if (newPassword.isEmpty()) {
                showToast("Please enter a new password.");
                return;
            }

            // Re-authenticate and then update password
            reauthenticateAndChangePassword(currentPassword, newPassword);
        });

        logoutButton.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(ProfilePage.this, MainActivity.class));
            finish(); // Clear the activity stack
        });
    }

    private void reauthenticateAndChangePassword(String currentPassword, String newPassword) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), currentPassword);

            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        // If re-authentication is successful, update the password
                        user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    showToast("Password updated successfully.");
                                } else {
                                    showToast("Failed to update password: " + task.getException().getMessage());
                                }
                            }
                        });
                    } else {
                        showToast("Re-authentication failed: " + task.getException().getMessage());
                    }
                }
            });
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
