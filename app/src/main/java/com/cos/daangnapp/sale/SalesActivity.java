package com.cos.daangnapp.sale;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cos.daangnapp.CMRespDto;
import com.cos.daangnapp.R;
import com.cos.daangnapp.login.model.UserRespDto;
import com.cos.daangnapp.main.profile.ProfileService;
import com.cos.daangnapp.retrofitURL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalesActivity extends AppCompatActivity {

    private static final String TAG = "SalesActivity";
    private ImageButton ivBack;
    private RecyclerView rvSaleList;
    private retrofitURL retrofitURL;
    private ProfileService profileService= retrofitURL.retrofit.create(ProfileService .class);
    private SaleAdapter saleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);

        init();
        initSetting();

    }
    public void init(){
        ivBack = findViewById(R.id.sales_btn_back);
        rvSaleList =findViewById(R.id.rv_saleslist);

        LinearLayoutManager manager = new LinearLayoutManager(SalesActivity.this,RecyclerView.VERTICAL,false);
        rvSaleList.setLayoutManager(manager);
    }

    public void initSetting(){
        Intent intent = getIntent();
        int userId = intent.getIntExtra("userId",0);


        getUser(userId);
        ivBack.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    public void getUser(int userId){
        Call<CMRespDto<UserRespDto>> call =profileService.getuser(userId);
        call.enqueue(new Callback<CMRespDto<UserRespDto>>() {
            @Override
            public void onResponse(Call<CMRespDto<UserRespDto>> call, Response<CMRespDto<UserRespDto>> response) {
                CMRespDto<UserRespDto> cmRespDto = response.body();
                UserRespDto user= cmRespDto.getData();
                Log.d(TAG, "onResponse: 유저정보보기 성공"+user.getPosts());
                saleAdapter = new SaleAdapter(user.getPosts(),SalesActivity.this);
                rvSaleList.setAdapter(saleAdapter);
            }
            @Override
            public void onFailure(Call<CMRespDto<UserRespDto>> call, Throwable t) {
                Log.d(TAG, "onFailure: 유저정보보기실패");
            }
        });
    }
}