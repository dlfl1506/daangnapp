package com.cos.daangnapp.main.home.detail;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cos.daangnapp.CMRespDto;
import com.cos.daangnapp.R;
import com.cos.daangnapp.main.MainActivity;
import com.cos.daangnapp.main.home.detail.adapter.UpdateAdapter;
import com.cos.daangnapp.main.home.detail.service.PostUpdateService;
import com.cos.daangnapp.main.home.model.PostRespDto;
import com.cos.daangnapp.main.home.model.PostUpdateReqDto;
import com.cos.daangnapp.retrofitURL;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateActivity extends AppCompatActivity {

    private static final String TAG = "UpdateActivity";
    private RecyclerView rvImage;
    private ArrayList<String> mImageList= new ArrayList<>();
    private ArrayList<Uri> mUriArrayList = new ArrayList<>();
    private TextView tvCategory, tvCategoryNo;
    private EditText etTitle,etPrice,etContent;
    private UpdateAdapter updateAdapter;
    private retrofitURL retrofitURL;
    private PostUpdateService postUpdateService= retrofitURL.retrofit.create(PostUpdateService .class);
    private PostUpdateReqDto postUpdateReqDto= new PostUpdateReqDto();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        init();
        initSetting();
    }



    public void init(){
        rvImage = findViewById(R.id.update_rv);
        tvCategory = findViewById(R.id.update_tv_categories);
        tvCategoryNo = findViewById(R.id.update_tv_categoryNo);
        etTitle = findViewById(R.id.update_et_title);
        etPrice = findViewById(R.id.update_et_price);
        etContent = findViewById(R.id.update_et_content);
    }

    public void initSetting(){
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String category = intent.getStringExtra("category");
        String content = intent.getStringExtra("content");
        String price = intent.getStringExtra("price");
        ArrayList<String> list = (ArrayList<String>) intent.getSerializableExtra("mImageList");


        for(int i=0;i<list.size(); i++){
           mUriArrayList.add(Uri.parse(list.get(i)));
        }
        rvImage.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        updateAdapter = new UpdateAdapter(mUriArrayList, this);
        rvImage.setAdapter(updateAdapter);

        tvCategory.setText(category);
        etTitle.setText(title);
        etPrice.setText(price);
        etContent.setText(content);
    }

    public void writingOnClick(View view) {
        switch (view.getId()) {
            case R.id.writing_btn_back:
                onBackPressed();
                break;
            case R.id.update_btn_submit:
                Intent intent = getIntent();
                int postId = intent.getIntExtra("postId", 1);

                postUpdateReqDto.setTitle(etTitle.getText().toString());
                postUpdateReqDto.setCategory(tvCategory.getText().toString());
                postUpdateReqDto.setContent(etContent.getText().toString());
                postUpdateReqDto.setPrice(etPrice.getText().toString());

                if(etTitle.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"제목 을 입력해주세요.",Toast.LENGTH_SHORT).show();
                }else if(tvCategory.getText().toString().equals("카테고리")){
                    Toast.makeText(getApplicationContext(),"카테고리를 선택해주세요.",Toast.LENGTH_SHORT).show();
                }else if(etPrice.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"가격 을 입력해주세요.",Toast.LENGTH_SHORT).show();
                }else if(etContent.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"내용 을 입력해주세요.",Toast.LENGTH_SHORT).show();
                }else {
                    updatePost(postId,postUpdateReqDto);
                }
                break;
            case R.id.writing_btn_categories:
                showCategories();
                break;
            default:
                break;
        }
    }

    public void showCategories() {
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateActivity.this);
        final String[] versionArray = new String[] {"디지털/가전","가구/인테리어","유아동/유아도서","생활/가공식품","여성의류","여성잡화",
                "뷰티/미용","남성패션/잡화","스포츠/레저","게임/취미","도서/티켓/음반","반려동물용품","기타 중고물품"};

        builder.setItems(versionArray, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               tvCategory.setText(versionArray[which]);
               tvCategoryNo.setText((which+1)+"");
            }
        });
        builder.show();
    }


    public void updatePost(int postId,PostUpdateReqDto postUpdateReqDto){
        Call<CMRespDto<PostRespDto>> call = postUpdateService.updatePost(postId,postUpdateReqDto);
        call.enqueue(new Callback<CMRespDto<PostRespDto>>() {
            @Override
            public void onResponse(Call<CMRespDto<PostRespDto>> call, Response<CMRespDto<PostRespDto>> response) {
                Log.d(TAG, "post수정 성공 ");
                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                UpdateActivity.this.finish();
            }
            @Override
            public void onFailure(Call<CMRespDto<PostRespDto>> call, Throwable t) {
                Log.d(TAG, "post수정 실패 ");
            }
        });
    }

}