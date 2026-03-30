package com.dentacore.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.dentacore.R;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TextView logo = findViewById(R.id.tvLogo);
        TextView tagline = findViewById(R.id.tvTagline);

        AlphaAnimation fadeIn = new AlphaAnimation(0f, 1f);
        fadeIn.setDuration(1000);
        logo.startAnimation(fadeIn);

        AlphaAnimation fadeIn2 = new AlphaAnimation(0f, 1f);
        fadeIn2.setDuration(1200);
        fadeIn2.setStartOffset(400);
        tagline.startAnimation(fadeIn2);

        new Handler().postDelayed(() ->
            startActivity(new Intent(this, MainActivity.class)), 2000);
    }
}
