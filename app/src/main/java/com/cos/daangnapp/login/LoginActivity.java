package com.cos.daangnapp.login;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cos.daangnapp.R;

public class LoginActivity extends AppCompatActivity {

    private ImageButton backBtn;
    private static final String TAG = "LoginActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        backBtn.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    public void init(){
        backBtn = findViewById(R.id.login_iv_back);
    }


    @Override
    public void onBackPressed() {
      //  super.onBackPressed();
        Toast.makeText(getApplicationContext(),"가입단계 진행 중에는 뒤로 갈 수 없습니다.",Toast.LENGTH_SHORT).show();

    }
}