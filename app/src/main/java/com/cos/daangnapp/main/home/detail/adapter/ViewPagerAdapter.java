package com.cos.daangnapp.main.home.detail.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.cos.daangnapp.R;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {

    // LayoutInflater 서비스 사용을 위한 Context 참조 저장.
    private Context mContext;
    private ArrayList<String> photoList;
    private static final String TAG = "ViewPagerAdapter";

    public ViewPagerAdapter(Context mContext, ArrayList<String> photoList) {
        this.mContext= mContext;
        this.photoList = photoList;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.detail_image, null);
        ImageView mImageView = view.findViewById(R.id.detail_iv);
        Log.d(TAG, "instantiateItem: "+photoList);

        Glide.with(mContext).load(photoList.get(position)).into(mImageView);
        container.addView(view);

        return view ;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // 뷰페이저에서 삭제.
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        // 전체 페이지 수는 10개로 고정.
        return photoList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (View)object);
    }
}