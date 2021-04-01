package com.cos.daangnapp.splash;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cos.daangnapp.R;
import com.cos.daangnapp.main.MainActivity;

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
                    SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
                  int userId = pref.getInt("userId",0);
                   if(userId ==0){
                       Intent intent = new Intent(SplashActivity.this, StartActivity.class);
                       intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                       startActivity(intent);
                       SplashActivity.this.finish();
                   }
                   else{
                           Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                           intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                           startActivity(intent);
                           SplashActivity.this.finish();
                           Log.d(TAG, "run: 실행됨");
                   }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();



    }




}