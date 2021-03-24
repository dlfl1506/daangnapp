package com.cos.daangnapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cos.daangnapp.splash.StartActivity;

public class MainActivity extends AppCompatActivity {

    private LinearLayout btnProfile;
    private TextView toolbar_tvDong;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        btnProfile.setOnClickListener(v -> {
            SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.clear();
            editor.commit();

            Intent intent = new Intent(MainActivity.this, StartActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            MainActivity.this.finish();
        });
    }
    public void init(){
        toolbar_tvDong = findViewById(R.id.toolbar_tv_dong);
        btnProfile = findViewById(R.id.main_btn_profile);
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        String address = pref.getString("address", null);
        toolbar_tvDong.setText(address);
    }
}