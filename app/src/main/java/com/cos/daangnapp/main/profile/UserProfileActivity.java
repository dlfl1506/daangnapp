package com.cos.daangnapp.main.profile;

import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cos.daangnapp.CMRespDto;
import com.cos.daangnapp.R;
import com.cos.daangnapp.login.model.UserRespDto;
import com.cos.daangnapp.retrofitURL;
import com.cos.daangnapp.sale.SalesActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileActivity extends AppCompatActivity {
    private static final String TAG = "UserProfileActivity";
    private ImageButton ivBack;
    private ImageView ivProfile;
    private TextView tvNickName,tvCreateDate,tvpostCount;
    private retrofitURL retrofitURL;
    private ProfileService profileService= retrofitURL.retrofit.create(ProfileService .class);
    private LinearLayout userprofileSaleslist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        init();
        initSetting();
    }

    public void init(){
        ivBack = findViewById(R.id.userprofile_iv_back);
        ivProfile = findViewById(R.id.userprofile_iv_url);
        tvNickName = findViewById(R.id.userprofile_tv_nickname);
        tvCreateDate = findViewById(R.id.userprofile_tv_createdAt);
        tvpostCount = findViewById(R.id.userprofile_postCount);
        userprofileSaleslist = findViewById(R.id.userprofile_saleslist);


        ivBack.setOnClickListener(v -> {
            onBackPressed();
        });

    }

    public void initSetting(){
        Intent intent = getIntent();
        int userId = intent.getIntExtra("userId",0);
        getUser(userId);
    }

    public void getUser(int userId){
        Call<CMRespDto<UserRespDto>> call =profileService.getuser(userId);
        call.enqueue(new Callback<CMRespDto<UserRespDto>>() {
            @Override
            public void onResponse(Call<CMRespDto<UserRespDto>> call, Response<CMRespDto<UserRespDto>> response) {
                CMRespDto<UserRespDto> cmRespDto = response.body();
                UserRespDto user= cmRespDto.getData();
                Log.d(TAG, "getuser : 유저정보가져오기성공 ");
                if(user.getPhoto() !=null ) {
                    Uri uri = Uri.parse(user.getPhoto());
                    ivProfile.setImageURI(uri);
                    ivProfile.setBackground(new ShapeDrawable(new OvalShape()));
                    ivProfile.setClipToOutline(true);
                    ivProfile.setScaleType(ImageView.ScaleType.FIT_XY);
                }
                tvNickName.setText(user.getNickName());
                tvCreateDate.setText(user.getCreateDate().toString());
                tvpostCount.setText(user.getPosts().size()+"");

                userprofileSaleslist.setOnClickListener(v -> {
                    Intent intent = new Intent(v.getContext(), SalesActivity.class);
                    intent.putExtra("userId", user.getId());
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    v.getContext().startActivity(intent);
                });
            }

            @Override
            public void onFailure(Call<CMRespDto<UserRespDto>> call, Throwable t) {
                Log.d(TAG, "getuser  :유저정보가져오기실패");

            }
        });
    }
}