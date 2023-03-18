package com.pablo.trabajofingrado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.window.SplashScreen;

public class inicial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        ImageView backgroundImage = findViewById(R.id.SplashScreenImage);
        Animation slideAnimation = AnimationUtils.loadAnimation(this, R.anim.animacion);
        backgroundImage.startAnimation(slideAnimation);
        final Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(inicial.this,MainActivity.class));
                finish();
            }
        },3000);
    }
}