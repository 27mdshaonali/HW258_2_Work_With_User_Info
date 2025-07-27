package com.binarybirds.hw258_2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION = 3000; // 3 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Find the ImageView
        ImageView logo = findViewById(R.id.logoImage);

        // Load animation
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_in); // or slide_in

        // Start animation
        logo.startAnimation(animation);

        // Move to next activity after delay
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, Sign_In_Activity.class));
            finish();
        }, SPLASH_DURATION);
    }
}
