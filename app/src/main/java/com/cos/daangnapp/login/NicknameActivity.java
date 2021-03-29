package com.cos.daangnapp.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cos.daangnapp.CMRespDto;
import com.cos.daangnapp.main.MainActivity;
import com.cos.daangnapp.R;
import com.cos.daangnapp.login.model.UserRespDto;
import com.cos.daangnapp.login.model.UserSaveReqDto;
import com.cos.daangnapp.login.service.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NicknameActivity extends AppCompatActivity {

    private static final String TAG = "NicknameActivity";
    private TextView phoneNumber;
    private EditText etNickName;
    private Button btnStart;
    private com.cos.daangnapp.retrofitURL retrofitURL;
    private UserService userService= retrofitURL.retrofit.create(UserService .class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nickname);

        init();
        btnStart.setOnClickListener(v -> {
            if(etNickName.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(),"닉네임을 입력해주세요.",Toast.LENGTH_SHORT).show();
            }else {
                NickNameCheck(etNickName.getText().toString());
            }
        });
    }
    public void init(){
        phoneNumber = findViewById(R.id.nickname_tv_phone_number);
        etNickName= findViewById(R.id.nickname_et_nickname);
        btnStart = findViewById(R.id.nickname_btn_start);
        Intent intent = getIntent();
        String phone_Number = intent.getStringExtra("phoneNumber");
        phoneNumber.setText(phone_Number);
    }

    public void NickNameCheck(String nickName){
        Call<CMRespDto<UserRespDto>> call = userService.NickNameSearch(nickName);
        call.enqueue(new Callback<CMRespDto<UserRespDto>>() {
            @Override
            public void onResponse(Call<CMRespDto<UserRespDto>> call, Response<CMRespDto<UserRespDto>> response) {
                UserSaveReqDto userSaveReqDto = new UserSaveReqDto(phoneNumber.getText().toString(),etNickName.getText().toString());
                CMRespDto<UserRespDto> cmRespDto = response.body();
                UserRespDto userRespDto = cmRespDto.getData();
                if(userRespDto == null){
                    save(userSaveReqDto);
                    Intent intent = new Intent(NicknameActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    NicknameActivity.this.finish();
                }else {
                    Toast.makeText(getApplicationContext(),"닉네임이 중복됩니다.\n다른 닉네임을 입력해주세요 !!",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<CMRespDto<UserRespDto>> call, Throwable t) {
                Log.d(TAG, "onFailure: 중복체크 실패 !!");
            }
        });
    }

    public void save(UserSaveReqDto userSaveReqDto){
        Call<CMRespDto<UserRespDto>> call = userService.save(userSaveReqDto);
        call.enqueue(new Callback<CMRespDto<UserRespDto>>() {
            @Override
            public void onResponse(Call<CMRespDto<UserRespDto>> call, Response<CMRespDto<UserRespDto>> response) {
                CMRespDto<UserRespDto> cmRespDto = response.body();
                UserRespDto userRespDto = cmRespDto.getData();
                Log.d(TAG, "onResponse: save성공");
                SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("userId", userRespDto.getId());
                editor.commit();
            }
            @Override
            public void onFailure(Call<CMRespDto<UserRespDto>> call, Throwable t) {
                Log.d(TAG, "onFailure: save 실패");
            }
        });
    }
}