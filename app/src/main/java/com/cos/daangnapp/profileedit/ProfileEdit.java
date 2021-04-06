package com.cos.daangnapp.profileedit;

import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cos.daangnapp.CMRespDto;
import com.cos.daangnapp.R;
import com.cos.daangnapp.login.model.UserRespDto;
import com.cos.daangnapp.main.MainActivity;
import com.cos.daangnapp.profileedit.model.UserUpdateReqDto;
import com.cos.daangnapp.retrofitURL;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileEdit extends AppCompatActivity {

    private static final String TAG = "ProfileEdit";
    private ImageButton ib_back;
    private ImageView profileImage;
    private TextView tvSubmit;
    private EditText etNickname;
    final int PICK_IMAGE_MULTIPLE = 1;
    private ArrayList<Uri> mUriArrayList = new ArrayList<>();
    private Button btnRemove;
    private retrofitURL retrofitURL;
    private ProfileEditService profileEditService= retrofitURL.retrofit.create(ProfileEditService .class);
    private  UserUpdateReqDto userUpdateReqDto = new UserUpdateReqDto();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        Intent intent = getIntent();
        int userId = intent.getIntExtra("userId",0);
        String photo = intent.getStringExtra("photo");
        String nickName= intent.getStringExtra("nickName");

        ib_back = findViewById(R.id.profile_btn_back);
        profileImage = findViewById(R.id.profile_iv_profile_image);
        tvSubmit = findViewById(R.id.profile_btn_submit);
        etNickname =findViewById(R.id.et_nickName);
        btnRemove = findViewById(R.id.btn_profile_remove);

        etNickname.setText(nickName);

        if(photo !=null) {
            Uri uri = Uri.parse(photo);
            profileImage.setImageURI(uri);
            profileImage.setBackground(new ShapeDrawable(new OvalShape()));
            profileImage.setClipToOutline(true);
            profileImage.setScaleType(ImageView.ScaleType.FIT_XY);
        }


        ib_back.setOnClickListener(v -> {
            onBackPressed();
        });

        profileImage.setOnClickListener(v -> {
            uploadImage();
        });

        userUpdateReqDto.setPhoto(photo);

        btnRemove.setOnClickListener(v -> {
            userUpdateReqDto.setPhoto(null);
            profileImage.setImageURI(null);
            profileImage.setBackground(new ShapeDrawable(new OvalShape()));
            profileImage.setClipToOutline(true);
            profileImage.setScaleType(ImageView.ScaleType.FIT_XY);
        });



        tvSubmit.setOnClickListener(v -> {
            userUpdateReqDto.setNickName(etNickname.getText().toString());
            userUpdate(userId,userUpdateReqDto);
            Log.d(TAG, "onCreate: " +userUpdateReqDto);

            SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("photo", userUpdateReqDto.getPhoto());
            editor.commit();

            Intent intentt = new Intent(ProfileEdit.this, MainActivity.class);
            intentt.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentt);
            ProfileEdit.this.finish();
        });
    }
    public void uploadImage() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        //intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE);

        System.out.println("사진업로드");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK && data != null) {
                if (data.getData() != null) {
                    Uri uri;
                    //mUriArrayList.add(uri);

                    Intent intent = getIntent();
                    String photo = intent.getStringExtra("photo");
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {
                            ClipData.Item item = mClipData.getItemAt(i);
                            uri = item.getUri();
                            System.out.println("uri : " + uri);
                            mUriArrayList.add(uri);

                            userUpdateReqDto.setPhoto(uri.toString());
                                profileImage.setImageURI(uri);
                                profileImage.setBackground(new ShapeDrawable(new OvalShape()));
                                profileImage.setClipToOutline(true);
                                profileImage.setScaleType(ImageView.ScaleType.FIT_XY);

                        }
                    }
                }
            } else {

            }
        } catch (Exception e) {

        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void userUpdate(int id,UserUpdateReqDto userUpdateReqDto){
        Call<CMRespDto<UserRespDto>> call = profileEditService.update(id,userUpdateReqDto);
        call.enqueue(new Callback<CMRespDto<UserRespDto>>() {
            @Override
            public void onResponse(Call<CMRespDto<UserRespDto>> call, Response<CMRespDto<UserRespDto>> response) {
                CMRespDto<UserRespDto> cmRespDto = response.body();
                UserRespDto userRespDto = cmRespDto.getData();
                Log.d(TAG, "onResponse: 업데이트성공"+userRespDto);
            }
            @Override
            public void onFailure(Call<CMRespDto<UserRespDto>> call, Throwable t) {
                Log.d(TAG, "onFailure: 업데이트실패");
            }
        });
    }
}