package com.cos.daangnapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Log.d(TAG, "onCreate: 실행됨");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                    Intent intent = new Intent(SplashActivity.this, StartActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    SplashActivity.this.finish();
                    Log.d(TAG, "run: 실행됨");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }
}