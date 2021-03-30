package com.cos.daangnapp.detail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.cos.daangnapp.R;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";
    private int postId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        Intent intent = getIntent();
        postId = intent.getIntExtra("postId", 1);
        Log.d(TAG, "postId "+postId);

    }

    public  void init(){

    }

    public void initSetting(){

    }

    public void productInformationOnClick(View view) {
        switch (view.getId()) {
            case R.id.product_information_iv_back:
                onBackPressed();
                break;
            case R.id.product_information_iv_share:
                //share();
                break;
            case R.id.product_information_iv_more:
                // more();
                break;
            case R.id.product_information_btn_purchase:
          //      purchase(productNo);
                break;
            default:
                break;
        }
    }
}