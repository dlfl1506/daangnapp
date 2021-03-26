package com.cos.daangnapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.cos.daangnapp.splash.StartActivity;
import com.cos.daangnapp.writing.WritingActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import info.androidhive.fontawesome.FontDrawable;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {
    private LinearLayout test;
    private Animation fab_open, fab_close;
    private LinearLayout btnProfile;
    private Boolean isFabOpen = false;
    private TextView toolbar_tvDong;
    private FloatingActionButton fabAdd, fabJoongo, fabDongne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        initSetting();

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
        fabAdd=findViewById(R.id.fab_add);
        fabJoongo=findViewById(R.id.fab_joongo);
        fabDongne=findViewById(R.id.fab_dongne);
        toolbar_tvDong = findViewById(R.id.toolbar_tv_dong);
        btnProfile = findViewById(R.id.main_btn_profile);

        test = findViewById(R.id.id_test);
    }

    public void initSetting(){
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);

        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        String address = pref.getString("address", null);

        toolbar_tvDong.setText(address);
        fabAdd.setOnClickListener(this);
        fabJoongo.setOnClickListener(this);
        fabDongne.setOnClickListener(this);

        FontDrawable plus = new FontDrawable(this, R.string.fa_plus_solid, true, false);

        plus.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        fabAdd.setImageDrawable(plus);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fab_add:
                anim();
                break;
            case R.id.fab_joongo:
                anim();
                Intent intent = new Intent(MainActivity.this, WritingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                MainActivity.this.finish();
                break;
            case R.id.fab_dongne:
                anim();
                Toast.makeText(this, "동네홍보 글쓰기", Toast.LENGTH_SHORT).show();
                break;
    }
}
    public void anim() {
        if (isFabOpen) {
            FontDrawable plus = new FontDrawable(this, R.string.fa_plus_solid, true, false);
            plus.setTextColor(ContextCompat.getColor(this, android.R.color.white));
            fabAdd.setImageDrawable(plus);
            fabJoongo.startAnimation(fab_close);
            fabDongne.startAnimation(fab_close);
            fabJoongo.setClickable(false);
            fabDongne.setClickable(false);
            isFabOpen = false;
        } else {
            FontDrawable dongne = new FontDrawable(this, R.string.fa_home_solid, true, false);
            FontDrawable joongo = new FontDrawable(this, R.string.fa_pen_solid, true, false);
            dongne.setTextColor(ContextCompat.getColor(this, android.R.color.white));
            joongo.setTextColor(ContextCompat.getColor(this, android.R.color.white));

            fabAdd.setImageResource(R.drawable.ic_baseline_close_24);
            fabJoongo.setImageDrawable(joongo);
            fabDongne.setImageDrawable(dongne);
            fabJoongo.startAnimation(fab_open);
            fabDongne.startAnimation(fab_open);
            fabJoongo.setClickable(true);
            fabDongne.setClickable(true);
            isFabOpen = true;
        }
    }



}