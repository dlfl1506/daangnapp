package com.cos.daangnapp.splash;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.cos.daangnapp.R;
import com.cos.daangnapp.location.LocationActivity;

public class StartActivity extends AppCompatActivity {

    private Button btn_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        btn_start = findViewById(R.id.start_btn_set_location);

        btn_start.setOnClickListener(v -> {
            Intent intent = new Intent(StartActivity.this, LocationActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}