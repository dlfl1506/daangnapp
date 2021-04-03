package com.cos.daangnapp.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.cos.daangnapp.R;
import com.cos.daangnapp.main.home.HomeFragment;
import com.cos.daangnapp.main.profile.ProfileFragment;
import com.cos.daangnapp.writing.WritingActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import info.androidhive.fontawesome.FontDrawable;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private Animation fab_open, fab_close;
    private Boolean isFabOpen = false;
    private FloatingActionButton fabAdd, fabJoongo, fabDongne;
    private HomeFragment mHomeFragment;
    private ProfileFragment mProfileFragment;
    String[] permission_list = {
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        initSetting();
        checkPermission();

        mHomeFragment = new HomeFragment();
        mProfileFragment = new ProfileFragment();
        moveHome();
    }


    public void fragmentOnClick(View view) {
        switch (view.getId()) {
            case R.id.main_btn_home:
                moveHome();
                fabAdd.setVisibility(View.VISIBLE);
                break;
            case R.id.main_btn_category:
            //    moveCategory();
                break;
            case R.id.main_btn_writing:
            //    moveWriting();
                break;
            case R.id.main_btn_chat:
          //      moveChat();
                break;
            case R.id.main_btn_profile:
                 moveProfile();
                fabAdd.setVisibility(View.INVISIBLE);
                break;
            default:
                break;
        }
    }
    public void moveHome() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, mHomeFragment).commit();
    }
    public void moveProfile() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container,mProfileFragment).commit();
    }

    public void checkPermission(){
        //현재 안드로이드 버전이 6.0미만이면 메서드를 종료한다.
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return;

        for(String permission : permission_list){
            //권한 허용 여부를 확인한다.
            int chk = checkCallingOrSelfPermission(permission);

            if(chk == PackageManager.PERMISSION_DENIED){
                //권한 허용을여부를 확인하는 창을 띄운다
                requestPermissions(permission_list,0);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==0)
        {
            for(int i=0; i<grantResults.length; i++)
            {
                //허용됬다면
                if(grantResults[i]== PackageManager.PERMISSION_GRANTED){
                }
                else {
                    Toast.makeText(getApplicationContext(),"앱권한설정하세요",Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }
    }


    public void init(){
        fabAdd=findViewById(R.id.fab_add);
        fabJoongo=findViewById(R.id.fab_joongo);
        fabDongne=findViewById(R.id.fab_dongne);
    }

    public void initSetting(){
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);

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