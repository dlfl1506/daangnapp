package com.cos.daangnapp.main.home.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.cos.daangnapp.CMRespDto;
import com.cos.daangnapp.R;
import com.cos.daangnapp.main.home.detail.adapter.ViewPagerAdapter;
import com.cos.daangnapp.main.home.detail.service.DetailService;
import com.cos.daangnapp.main.home.model.PostRespDto;
import com.cos.daangnapp.retrofitURL;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

private Context mContext;
    private static final String TAG = "DetailActivity";
    private ViewPagerAdapter viewPagerAdapter;
    private ArrayList<String> mImageList;
    private int postId;
    private ViewPager photoList;
    private CircleIndicator circleIndicator;
    private ImageView profile;
    private TextView tvNickName,tvAddress,tvTitle,tvTime,tvContent,tvCategori,tvFavorite,tvViewCount,tvAnotherNick,tvPrice;
    private retrofitURL retrofitURL;
    private DetailService detailService= retrofitURL.retrofit.create(DetailService .class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        init();
        initSetting();
    }

    public  void init(){


profile = findViewById(R.id.detail_iv_profile);
tvNickName = findViewById(R.id.detail_nickname);
tvAddress = findViewById(R.id.detail_tv_address);
tvTitle = findViewById(R.id.detail_tv_title);
tvContent=findViewById(R.id.detail_tv_content);
 tvCategori=findViewById(R.id.detail_tv_categories);
tvTime = findViewById(R.id.detail_tv_time);
tvFavorite = findViewById(R.id.detail_tv_favorite);
tvViewCount = findViewById(R.id.detail_tv_viewCount);
tvAnotherNick = findViewById(R.id.detail_tv_nickname_another);
tvPrice = findViewById(R.id.detail_tv_price);

    }
    public void initSetting(){
        Intent intent = getIntent();
        postId = intent.getIntExtra("postId", 1);
        getpost(postId);
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

    public void getpost(int id){
        Call<CMRespDto<PostRespDto>> call = detailService.getpost(id);
        call.enqueue(new Callback<CMRespDto<PostRespDto>>() {
            @Override
            public void onResponse(Call<CMRespDto<PostRespDto>> call, Response<CMRespDto<PostRespDto>> response) {
                CMRespDto<PostRespDto> cmRespDto = response.body();
                PostRespDto posts = cmRespDto.getData();

                photoList = findViewById(R.id.detail_viewPager);
                circleIndicator = findViewById(R.id.detail_indicator);
                mImageList = new ArrayList();
                mImageList.add(posts.getImg());

                Log.d(TAG, "mImageList: "+mImageList);
             //   viewPagerAdapter = new ViewPagerAdapter(mContext,mImageList);
          //      photoList.setAdapter(viewPagerAdapter);
            //    viewPagerAdapter.notifyDataSetChanged();
            //   circleIndicator.setViewPager(photoList);


                tvNickName.setText(posts.getUser().getNickName());
                tvAddress.setText(posts.getDong());
                tvTitle.setText(posts.getTitle());
                tvTime.setText(posts.getCreateDate().toString());
                tvFavorite.setText(posts.getFavorite()+"");
                tvCategori.setText(posts.getCategory());
                tvContent.setText(posts.getContent());
                tvViewCount.setText(posts.getCount()+"");
                tvAnotherNick.setText(posts.getUser().getNickName());
                tvPrice.setText(posts.getPrice()+"원");


            }
            @Override
            public void onFailure(Call<CMRespDto<PostRespDto>> call, Throwable t) {

            }
        });
    }
}