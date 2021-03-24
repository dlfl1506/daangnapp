package com.cos.daangnapp.splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.cos.daangnapp.MainActivity;
import com.cos.daangnapp.R;

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
                   if(userId == 0){
                       Intent intent = new Intent(SplashActivity.this, StartActivity.class);
                       intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                       startActivity(intent);
                       SplashActivity.this.finish();
                   }else{
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