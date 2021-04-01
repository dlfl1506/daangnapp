package com.cos.daangnapp.main.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cos.daangnapp.R;
import com.cos.daangnapp.main.MainActivity;

public class ProfileFragment  extends Fragment {

    private MainActivity activity;
    private  LinearLayout mBtnLogin, mTvSetting, mBtnLocation, mBtnChangeLocation;
    private TextView mTvNickname, mTvLocation,mTvCode;
    private  ImageView mIvSetting, mIvChange;
    private Button mBtnMyProfile;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mBtnLogin = view.findViewById(R.id.profile_btn_login);

        mTvNickname = view.findViewById(R.id.profile_tv_nickname);
        mTvLocation = view.findViewById(R.id.profile_tv_location);
        mTvCode = view.findViewById(R.id.profile_tv_code);

        mTvSetting = view.findViewById(R.id.profile_tv_setting);
        mIvSetting = view.findViewById(R.id.profile_iv_setting);

        mIvSetting.setOnClickListener(v -> {
            moveSettingActivity();
        });
        mTvSetting.setOnClickListener(v -> {
            moveSettingActivity();
        });

        SharedPreferences pref =getActivity().getSharedPreferences("pref",Context.MODE_PRIVATE);
       int code = pref.getInt("userId",0);
        String dong = pref.getString("dong",null);
        String nickname = pref.getString("nickName", null);

        mTvNickname.setText(nickname);
        mTvLocation.setText(dong);
        mTvCode.setText(code+"");

        return view;
    }

    public void moveSettingActivity() {
        Intent intent = new Intent(activity, SettingActivity.class);
        startActivity(intent);
    }
}
