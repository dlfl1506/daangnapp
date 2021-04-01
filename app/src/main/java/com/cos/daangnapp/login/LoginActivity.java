package com.cos.daangnapp.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cos.daangnapp.CMRespDto;
import com.cos.daangnapp.main.MainActivity;
import com.cos.daangnapp.R;
import com.cos.daangnapp.login.model.AuthReqDto;
import com.cos.daangnapp.login.model.AuthRespDto;
import com.cos.daangnapp.login.model.UserRespDto;
import com.cos.daangnapp.login.service.AuthService;
import com.cos.daangnapp.retrofitURL;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity  {
    private TextView tvphoneNumber;
    private EditText etAuthCode;
    private Button authcodeResend,btnLogin;
    private ImageButton backBtn;
    private static final String TAG = "LoginActivity";
    private retrofitURL retrofitURL;
    private AuthService authService= retrofitURL.retrofit.create(AuthService .class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        init();

        backBtn.setOnClickListener(v -> {
            onBackPressed();
        });

        authcodeResend.setOnClickListener(v -> {
            AuthCodeSearch(tvphoneNumber.getText().toString());
        });


        btnLogin.setOnClickListener(v -> {
            CodeVerify(tvphoneNumber.getText().toString(),etAuthCode.getText().toString());
        });
    }


    public void init(){
        backBtn = findViewById(R.id.login_iv_back);

        tvphoneNumber = findViewById(R.id.tv_phoneNumber);
        authcodeResend =findViewById(R.id.btn_authcode_resend);
        etAuthCode = findViewById(R.id.et_authcode);
        btnLogin = findViewById(R.id.btn_login);
        Intent intent = getIntent();
        String phoneNumber = intent.getStringExtra("phoneNumber");
        tvphoneNumber.setText(phoneNumber);

        etAuthCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()==4){
                    btnLogin.setEnabled(true);
                }else{
                    btnLogin.setEnabled(false);
                }
            }
        });

    }


    @Override
    public void onBackPressed() {
      //  super.onBackPressed();
        Toast.makeText(getApplicationContext(),"가입단계 진행 중에는 뒤로 갈 수 없습니다.",Toast.LENGTH_SHORT).show();
    }

    public static String numberGen(int len, int dupCd) {
        Random rand = new Random();
        String numStr = ""; // 난수가 저장될 변수

        for (int i = 0; i < len; i++) {

            // 0~9 까지 난수 생성
            String ran = Integer.toString(rand.nextInt(10));

            if (dupCd == 1) {
                // 중복 허용시 numStr에 append
                numStr += ran;
            } else if (dupCd == 2) {
                // 중복을 허용하지 않을시 중복된 값이 있는지 검사한다
                if (!numStr.contains(ran)) {
                    // 중복된 값이 없으면 numStr에 append
                    numStr += ran;
                } else {
                    // 생성된 난수가 중복되면 루틴을 다시 실행한다
                    i -= 1;
                }
            }
        }
        return numStr;
    }


    public void CodeVerify(String phoneNumber,String authCode){
        Call<CMRespDto<AuthRespDto>> call = authService.authCodeSearch(phoneNumber);
        call.enqueue(new Callback<CMRespDto<AuthRespDto>>() {
            @Override
            public void onResponse(Call<CMRespDto<AuthRespDto>> call, Response<CMRespDto<AuthRespDto>> response) {
                CMRespDto<AuthRespDto> cmRespDto = response.body();
                AuthRespDto authRespDto = cmRespDto.getData();
                if(authRespDto == null){
                    Toast.makeText(getApplicationContext(), "인증코드가 만료되었습니다.\n인증코드를 다시 받으세요.", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (authRespDto.getAuthCode().equals(authCode)) {
                        Login(authRespDto.getPhoneNumber());
                    } else if (authCode.equals("")) {
                        Toast.makeText(getApplicationContext(), "인증코드를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "인증코드가 맞지않습니다.", Toast.LENGTH_SHORT).show();
                    }
                }

            }
            @Override
            public void onFailure(Call<CMRespDto<AuthRespDto>> call, Throwable t) {
                Log.d(TAG, "onFailure: 실패!!!");
            }
        });
    }

    public void AuthCodeSearch(String phoneNumber){
        Call<CMRespDto<AuthRespDto>> call = authService.authCodeSearch(phoneNumber);
        call.enqueue(new Callback<CMRespDto<AuthRespDto>>() {
            @Override
            public void onResponse(Call<CMRespDto<AuthRespDto>> call, Response<CMRespDto<AuthRespDto>> response) {
                AuthReqDto authReqDto = new AuthReqDto(tvphoneNumber.getText().toString(),numberGen(4,2));

                CMRespDto<AuthRespDto> cmRespDto = response.body();
                AuthRespDto authRespDto = cmRespDto.getData();
                if(authRespDto == null){
                    AuthCodeSend(authReqDto);
                }else{
                    AuthCodeUpdate(authRespDto.getId(),numberGen(4,2));
                }
            }
            @Override
            public void onFailure(Call<CMRespDto<AuthRespDto>> call, Throwable t) {
                Log.d(TAG, "onFailure: 인증코드 확인실패 !!");
            }
        });
    }

    public void AuthCodeSend(AuthReqDto authReqDto){
        Call<CMRespDto<AuthRespDto>> call = authService.authCodeSend(authReqDto);
        call.enqueue(new Callback<CMRespDto<AuthRespDto>>() {
            @Override
            public void onResponse(Call<CMRespDto<AuthRespDto>> call, Response<CMRespDto<AuthRespDto>> response) {
          CMRespDto<AuthRespDto> cmRespDto = response.body();
              AuthRespDto authRespDto = cmRespDto.getData();
             AuthCodeDelete(authRespDto.getId());
                Toast.makeText(getApplicationContext(),"인증코드가 전송되었습니다. 유효시간 40초",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<CMRespDto<AuthRespDto>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"인증코드 전송 실패 !!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void AuthCodeUpdate(int id,String authCode){
        Call<CMRespDto<AuthRespDto>> call = authService.updateAuthCode(id,authCode);
        call.enqueue(new Callback<CMRespDto<AuthRespDto>>() {
            @Override
            public void onResponse(Call<CMRespDto<AuthRespDto>> call, Response<CMRespDto<AuthRespDto>> response) {
                CMRespDto<AuthRespDto> cmRespDto = response.body();
                AuthRespDto authRespDto = cmRespDto.getData();
                AuthCodeDelete(authRespDto.getId());
                Toast.makeText(getApplicationContext(),"인증코드가 전송되었습니다. 유효시간 40초",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<CMRespDto<AuthRespDto>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"인증코드 전송 실패 !!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    
    public void AuthCodeDelete(int id){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(40000);
                    Call<CMRespDto> call = authService.deleteCode(id);
                    call.enqueue(new Callback<CMRespDto>() {
                        @Override
                        public void onResponse(Call<CMRespDto> call, Response<CMRespDto> response) {
                            Log.d(TAG, "onResponse: 삭제성공");
                        }
                        @Override
                        public void onFailure(Call<CMRespDto> call, Throwable t) {
                            Log.d(TAG, "onFailure: 삭제실패");
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
            }

    public void Login(String phoneNumber){
        Call<CMRespDto<UserRespDto>> call = authService.UserSearch(phoneNumber);
        call.enqueue(new Callback<CMRespDto<UserRespDto>>() {
            @Override
            public void onResponse(Call<CMRespDto<UserRespDto>> call, Response<CMRespDto<UserRespDto>> response) {
                CMRespDto<UserRespDto> cmRespDto = response.body();
                UserRespDto userRespDto = cmRespDto.getData();
                if(userRespDto == null){
                    Toast.makeText(getApplicationContext(),"인증코드가 일치합니다. \n닉네임을 입력해주세요",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, NicknameActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("phoneNumber",tvphoneNumber.getText().toString());
                    startActivity(intent);
                    LoginActivity.this.finish();
                }else {
                    SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putInt("userId", userRespDto.getId());
                    editor.putString("nickName",userRespDto.getNickName());
                    editor.commit();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    LoginActivity.this.finish();
                }
            }
            @Override
            public void onFailure(Call<CMRespDto<UserRespDto>> call, Throwable t) {
                Log.d(TAG, "onFailure: 로그인실패");
            }
        });
    }

}