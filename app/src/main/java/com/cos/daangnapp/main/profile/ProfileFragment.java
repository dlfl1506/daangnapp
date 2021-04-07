package com.cos.daangnapp.main.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.util.Log;
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

import com.bumptech.glide.Glide;
import com.cos.daangnapp.CMRespDto;
import com.cos.daangnapp.R;
import com.cos.daangnapp.login.model.UserRespDto;
import com.cos.daangnapp.main.MainActivity;
import com.cos.daangnapp.profileedit.ProfileEdit;
import com.cos.daangnapp.retrofitURL;
import com.cos.daangnapp.sale.SalesActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment  extends Fragment {

    private Context mContext;
    private static final String TAG = "ProfileFragment";
    private MainActivity activity;
    private  LinearLayout mBtnLogin, mTvSetting, mBtnLocation, mBtnChangeLocation;
    private TextView mTvNickname, mTvLocation,mTvCode;
    private  ImageView IvPhoto,IvPhotoEdit,mIvSetting, mIvChange;
    private LinearLayout btnSaleList;
    private Button mBtnMyProfile;
    private retrofitURL retrofitURL;
    private ProfileService profileService= retrofitURL.retrofit.create(ProfileService .class);
private String photo;
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
        mBtnMyProfile = view.findViewById(R.id.profile_btn_my_profile);
        mTvNickname = view.findViewById(R.id.profile_tv_nickname);
        mTvLocation = view.findViewById(R.id.profile_tv_location);
        mTvCode = view.findViewById(R.id.profile_tv_code);
        btnSaleList = view.findViewById(R.id.btn_saleList);
        mTvSetting = view.findViewById(R.id.profile_tv_setting);
        mIvSetting = view.findViewById(R.id.profile_iv_setting);
        IvPhoto = view.findViewById(R.id.profile_iv_photo);
        IvPhotoEdit = view.findViewById(R.id.profile_iv_photo_Edit);

        mIvSetting.setOnClickListener(v -> {
            moveSettingActivity();
        });
        mTvSetting.setOnClickListener(v -> {
            moveSettingActivity();
        });



        SharedPreferences pref =getActivity().getSharedPreferences("pref",Context.MODE_PRIVATE);
       int userId = pref.getInt("userId",0);
        String dong = pref.getString("dong",null);

        IvPhotoEdit.setOnClickListener(v -> {
            Intent intent = new Intent(activity, ProfileEdit.class);
            intent.putExtra("userId", userId);
            intent.putExtra("nickName",mTvNickname.getText());
            intent.putExtra("photo",photo);
            startActivity(intent);
        });

        mTvLocation.setText(dong);
        getUser(userId);
        return view;
    }

    public void moveSettingActivity() {
        Intent intent = new Intent(activity, SettingActivity.class);
        startActivity(intent);
    }

    public void getUser(int userId){
        Call<CMRespDto<UserRespDto>> call =profileService.getuser(userId);
            call.enqueue(new Callback<CMRespDto<UserRespDto>>() {
                @Override
                public void onResponse(Call<CMRespDto<UserRespDto>> call, Response<CMRespDto<UserRespDto>> response) {
                    CMRespDto<UserRespDto> cmRespDto = response.body();
                    UserRespDto user= cmRespDto.getData();
                    try {
                        mTvNickname.setText(user.getNickName());
                        mTvCode.setText(user.getId()+"");
                        Log.d(TAG, "onResponse: "+user.getPhoto());
                        if(user.getPhoto()==null){

                        }else{
                            Glide.with(activity).load(user.getPhoto()).into(IvPhoto);
                            IvPhoto.setClipToOutline(true);
                             IvPhoto.setBackground(new ShapeDrawable(new OvalShape()));
                            IvPhoto.setScaleType(ImageView.ScaleType.FIT_XY);
                  /*     Uri uri = Uri.parse(user.getPhoto());
                            IvPhoto.setImageURI(uri);
                            IvPhoto.setBackground(new ShapeDrawable(new OvalShape()));
                            IvPhoto.setClipToOutline(true);
                            IvPhoto.setScaleType(ImageView.ScaleType.FIT_XY);*/
                        }
                    } catch (Exception e) {
                        Log.d(TAG, "null");
                    }


                    mBtnMyProfile.setOnClickListener(v -> {
                        Intent intent = new Intent(activity, UserProfileActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("userId",user.getId());
                        startActivity(intent);
                    });


                    btnSaleList.setOnClickListener(v -> {
                        Intent intent = new Intent(activity, SalesActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("userId",user.getId());
                        startActivity(intent);
                    });
                }

                @Override
                public void onFailure(Call<CMRespDto<UserRespDto>> call, Throwable t) {
                    Log.d(TAG, "getUser실패");
                }
            });

    }
}
