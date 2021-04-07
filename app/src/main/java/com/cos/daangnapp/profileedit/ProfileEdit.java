package com.cos.daangnapp.profileedit;

import android.app.ProgressDialog;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cos.daangnapp.CMRespDto;
import com.cos.daangnapp.R;
import com.cos.daangnapp.login.model.UserRespDto;
import com.cos.daangnapp.main.MainActivity;
import com.cos.daangnapp.profileedit.model.UserUpdateReqDto;
import com.cos.daangnapp.retrofitURL;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
    private Intent intent;
    private retrofitURL retrofitURL;
    private ProfileEditService profileEditService= retrofitURL.retrofit.create(ProfileEditService .class);
    private  UserUpdateReqDto userUpdateReqDto = new UserUpdateReqDto();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        intent = getIntent();
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
            uploadFile();
            SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("photo", userUpdateReqDto.getPhoto());
            editor.commit();


        });
    }
    public void uploadImage() {

     /*   Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        //intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE);
        System.out.println("사진업로드");*/

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        //사진을 여러개 선택할수 있도록 한다
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE );
        Log.d(TAG, "uploadImage:사진여러개선택됨 !!! ");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d(TAG, "onActivityResult: "+requestCode);
        try {
            if (requestCode ==  PICK_IMAGE_MULTIPLE) {
                Uri uri;
                //mUriArrayList.add(uri);
                if (data.getClipData() != null) {
                    ClipData mClipData = data.getClipData();
                    for (int i = 0; i < mClipData.getItemCount(); i++) {
                        ClipData.Item item = mClipData.getItemAt(i);
                        uri = item.getUri();
                        mUriArrayList.add(uri);
                        profileImage.setImageURI(uri);
                        profileImage.setBackground(new ShapeDrawable(new OvalShape()));
                        profileImage.setClipToOutline(true);
                        profileImage.setScaleType(ImageView.ScaleType.FIT_XY);
                    }
                }
            } else {
                Log.d(TAG, "onActivityResult: 데이터음슴");
            }
        } catch (Exception e) {
            Log.d(TAG, "onActivityResult: 안됐땅");
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

    private void uploadFile() {
        for (int i = 0; i < mUriArrayList.size(); i++)
            //업로드할 파일이 있으면 수행
            if (mUriArrayList != null) {
                //업로드 진행 Dialog 보이기
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("업로드중...");
                progressDialog.show();
                //storage
                FirebaseStorage storage = FirebaseStorage.getInstance();
                //Unique한 파일명을 만들자.
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMHH_mmss");
                Date now = new Date();
                String filename = formatter.format(now) + mUriArrayList.get(i) + ".png";
                //storage 주소와 폴더 파일명을 지정해 준다.
                StorageReference storageRef = storage.getReferenceFromUrl("gs://daangnappchat.appspot.com").child("images/" + filename);
                //올라가거라...
                storageRef.putFile(mUriArrayList.get(i))
                        //성공시
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                progressDialog.dismiss(); //업로드 진행 Dialog 상자 닫기
                                //       Toast.makeText(getApplicationContext(), "업로드 완료!", Toast.LENGTH_SHORT).show();
                                FirebaseStorage storage = FirebaseStorage.getInstance("gs://daangnappchat.appspot.com");
                                StorageReference storageRef = storage.getReference();
                                storageRef.child("images/" + filename).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        //이미지 로드 성공시
                                        int userId = intent.getIntExtra("userId", 0);
                                        Log.d(TAG, "onSuccess: "+ userId);
                                        userUpdateReqDto.setPhoto(uri.toString());
                                        userUpdateReqDto.setNickName(etNickname.getText().toString());
                                        userUpdate(userId, userUpdateReqDto);
                                        Log.d(TAG, "onSuccess: " + userUpdateReqDto);
                                        Intent intentt = new Intent(ProfileEdit.this, MainActivity.class);
                                        intentt.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intentt);
                                        ProfileEdit.this.finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        //이미지 로드 실패시
                                        Log.d(TAG, "onFailure: 실패");
                                        Toast.makeText(ProfileEdit.this, "실패", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        })
                        //실패시
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "업로드 실패!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        //진행중
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                @SuppressWarnings("VisibleForTests") //이걸 넣어 줘야 아랫줄에 에러가 사라진다. 넌 누구냐?
                                        double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                //dialog에 진행률을 퍼센트로 출력해 준다
                                progressDialog.setMessage("Uploaded " + ((int) progress) + "% ...");
                            }
                        });

            } else {
                Toast.makeText(getApplicationContext(), "파일을 먼저 선택하세요.", Toast.LENGTH_SHORT).show();
            }
    }
}