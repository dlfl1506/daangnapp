package com.cos.daangnapp.writing;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cos.daangnapp.CMRespDto;
import com.cos.daangnapp.R;
import com.cos.daangnapp.main.MainActivity;
import com.cos.daangnapp.main.home.model.ImageRespDto;
import com.cos.daangnapp.retrofitURL;
import com.cos.daangnapp.writing.adapter.WritingAdapter;
import com.cos.daangnapp.writing.model.ImageSaveReqdto;
import com.cos.daangnapp.writing.model.PostSaveReqDto;
import com.cos.daangnapp.writing.model.PostSaveRespDto;
import com.cos.daangnapp.writing.service.PostService;
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

public class WritingActivity extends AppCompatActivity  {
   private  EditText mEtTitle, mEtPrice, mEtContent;
   private TextView  mTvCategories, mTvCategoryNo;
   private  final int PICK_IMAGE_MULTIPLE = 1;
    private ArrayList<Uri> mUriArrayList = new ArrayList<>();
   private  RecyclerView rvImage;
   private  WritingAdapter writingAdapter;
   private int count =0;
   private retrofitURL retrofitURL;
    private PostService postService= retrofitURL.retrofit.create(PostService .class);
    private PostSaveReqDto postSaveReqDto = new PostSaveReqDto();
    private ImageSaveReqdto imageSaveReqdto = new ImageSaveReqdto();
    private ImageButton btnBack;
    private static final String TAG = "WritingActivity";
    private Uri filePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        count =0;
        init();

    }
    public void init(){


        mEtTitle = findViewById(R.id.writing_et_title);
        mEtPrice = findViewById(R.id.writing_et_price);
        mEtContent = findViewById(R.id.writing_et_content);
        mTvCategories = findViewById(R.id.writing_tv_categories);
        mTvCategoryNo = findViewById(R.id.writing_tv_categoryNo);
        rvImage=findViewById(R.id.writing_rv);
        btnBack = findViewById(R.id.writing_btn_back);

        mEtPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                    if(s.toString().equals("0")){
                        mEtPrice.setText("무료나눔");
                    }
            }
        });
    }

    public void writingOnClick(View view) {
        switch (view.getId()) {
            case R.id.writing_btn_back:
                onBackPressed();
                break;
            case R.id.writing_btn_submit:
                SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
                String dong = pref.getString("dong", null);
                String gu = pref.getString("gu", null);
                int userId = pref.getInt("userId",0);

                postSaveReqDto.setTitle(mEtTitle.getText().toString());
                postSaveReqDto.setCategory(mTvCategories.getText().toString());
                postSaveReqDto.setPrice(mEtPrice.getText().toString());
                postSaveReqDto.setContent(mEtContent.getText().toString());
                postSaveReqDto.setDong(dong);
                postSaveReqDto.setGu(gu);

                if(mEtTitle.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"제목 을 입력해주세요.",Toast.LENGTH_SHORT).show();
                }else if(mTvCategories.getText().toString().equals("카테고리")){
                    Toast.makeText(getApplicationContext(),"카테고리를 선택해주세요.",Toast.LENGTH_SHORT).show();
                }else if(mEtPrice.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"가격 을 입력해주세요.",Toast.LENGTH_SHORT).show();
                }else if(mEtContent.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"내용 을 입력해주세요.",Toast.LENGTH_SHORT).show();
                }else {
                   submit(postSaveReqDto, userId);
                    uploadFile();
                }
                break;
            case R.id.writing_btn_categories:
                showCategories();
                break;
            case R.id.writing_btn_upload:
                uploadImage();
                break;
            default:
                break;
        }
    }

    public void submit(PostSaveReqDto postSaveReqDto,int userId) {
        Call<CMRespDto<PostSaveRespDto>> call = postService.save(postSaveReqDto,userId);
        call.enqueue(new Callback<CMRespDto<PostSaveRespDto>>() {
            @Override
            public void onResponse(Call<CMRespDto<PostSaveRespDto>> call, Response<CMRespDto<PostSaveRespDto>> response) {
                Log.d(TAG, "onResponse: save 완료");
                CMRespDto<PostSaveRespDto> cmRespDto = response.body();
                PostSaveRespDto postSaveRespDto = cmRespDto.getData();
                imageSaveReqdto.setPostId(postSaveRespDto.getId());
            }
            @Override
            public void onFailure(Call<CMRespDto<PostSaveRespDto>> call, Throwable t) {
                Log.d(TAG, "onFailure: save 실패");
            }
        });
    }

    public void ImageSave(ImageSaveReqdto imageSaveReqdto){
        Call<CMRespDto<ImageRespDto>> call = postService.Imagesave(imageSaveReqdto);
        call.enqueue(new Callback<CMRespDto<ImageRespDto>>() {
            @Override
            public void onResponse(Call<CMRespDto<ImageRespDto>> call, Response<CMRespDto<ImageRespDto>> response) {
                Log.d(TAG, "이미지저장 성공");
            }
            @Override
            public void onFailure(Call<CMRespDto<ImageRespDto>> call, Throwable t) {
                Log.d(TAG, "이미지 저장 실패");
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void uploadImage() {

    /*Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
     //   intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE);

        System.out.println("사진업로드");*/

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        //사진을 여러개 선택할수 있도록 한다
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE );
        Log.d(TAG, "uploadImage:사진여러개선택됨 !!! ");

    }


    public void showCategories() {
        AlertDialog.Builder builder = new AlertDialog.Builder(WritingActivity.this);
        final String[] versionArray = new String[] {"디지털/가전","가구/인테리어","유아동/유아도서","생활/가공식품","여성의류","여성잡화",
                "뷰티/미용","남성패션/잡화","스포츠/레저","게임/취미","도서/티켓/음반","반려동물용품","기타 중고물품"};

        builder.setItems(versionArray, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mTvCategories.setText(versionArray[which]);
                mTvCategoryNo.setText((which+1)+"");
            }
        });
        builder.show();
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
                            }
                       rvImage.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                        writingAdapter = new WritingAdapter(mUriArrayList, this);
                        rvImage.setAdapter(writingAdapter);
                    }
            } else {
                Log.d(TAG, "onActivityResult: 데이터음슴");
            }
        } catch (Exception e) {
            Log.d(TAG, "onActivityResult: 안됐땅");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    //upload the file
    private void uploadFile() {
        for(int i=0; i<mUriArrayList.size(); i++)
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
            String filename = formatter.format(now)+mUriArrayList.get(i)+".png";
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
                                storageRef.child("images/"+filename).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        //이미지 로드 성공시
                                        Log.d(TAG, "onSuccess: "+uri.toString());
                                            imageSaveReqdto.setUri(uri.toString());
                                            ImageSave(imageSaveReqdto);
                                            count++;
                                        if(count==mUriArrayList.size()){
                                            Intent intent = new Intent(WritingActivity.this, MainActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                            WritingActivity.this.finish();
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        //이미지 로드 실패시
                                        Log.d(TAG, "onFailure: 실패");
                                        Toast.makeText(WritingActivity.this, "실패", Toast.LENGTH_SHORT).show();
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

