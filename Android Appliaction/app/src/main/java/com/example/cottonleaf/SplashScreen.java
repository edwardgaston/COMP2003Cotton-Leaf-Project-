package com.example.cottonleaf;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    Animation bottomAnimation;
    ImageView logoImage;
    TextView Text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        bottomAnimation = AnimationUtils.loadAnimation(this,R.anim.bottomsplash_animation);
        logoImage = findViewById(R.id.logo);
        logoImage.setAnimation(bottomAnimation);
        Text = findViewById(R.id.GreenGuardian);
        Text.setAnimation(bottomAnimation);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                openLogin();

            }
        }, 4000);
    }
    public void openLogin() {
        Intent intent = new Intent(this, MainActivity.class);

        Pair<View, String> pairLogo = Pair.create((View) logoImage, "centerImage");

        Pair[] pairs = new Pair[]{pairLogo};

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, pairs);
        startActivity(intent, options.toBundle());
    }
}