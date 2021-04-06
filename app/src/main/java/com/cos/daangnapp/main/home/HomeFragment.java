package com.cos.daangnapp.main.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cos.daangnapp.CMRespDto;
import com.cos.daangnapp.R;
import com.cos.daangnapp.location.LocationActivity;
import com.cos.daangnapp.main.MainActivity;
import com.cos.daangnapp.main.home.model.PostRespDto;
import com.cos.daangnapp.retrofitURL;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment  {
    private static final String TAG = "HomeFragment";
    private  MainActivity activity;
    private RecyclerView postList;
    private  HomeAdapter homeAdapter;
    private Spinner spinner;
    private ArrayList<PostRespDto> postRespDtos = new ArrayList<>();
    private retrofitURL retrofitURL;
    private ImageView btnSearch,IvSearch,IvFilter,IvNotice;
    private EditText etSearch;
    private HomeService homeService= retrofitURL.retrofit.create(HomeService .class);

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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        SharedPreferences pref =getActivity().getSharedPreferences("pref",Context.MODE_PRIVATE);
        String dong = pref.getString("dong",null);
        String gu = pref.getString("gu", null);

        String[] LocationList = {
                dong,"다른 동네 선택"
        };
        spinner =view.findViewById(R.id.toolbar_tv_dong);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, LocationList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position ==1) {
                    Intent intent = new Intent(activity, LocationActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    activity.finish();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        postList= view.findViewById(R.id.rv_postlist);
        LinearLayoutManager manager = new LinearLayoutManager(activity,RecyclerView.VERTICAL,false);
        postList.setLayoutManager(manager);
        getposts(gu);

    btnSearch = view.findViewById(R.id.btn_search);
    IvFilter = view.findViewById(R.id.iv_filter);
    IvNotice = view.findViewById(R.id.iv_notice);


    IvSearch = view.findViewById(R.id.iv_search);
    etSearch = view.findViewById(R.id.et_post_search);


    etSearch.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            searchPosts(gu,s.toString());
        }
    });

    IvSearch.setVisibility(View.INVISIBLE);
    etSearch .setVisibility(View.INVISIBLE);

    btnSearch.setOnClickListener(v -> {
        btnSearch.setVisibility(view.INVISIBLE);
        IvFilter.setVisibility(view.INVISIBLE);
        IvNotice.setVisibility(view.INVISIBLE);

        IvSearch.setVisibility(view.VISIBLE);
        etSearch.setVisibility(view.VISIBLE);
    });


        return view;
    }
    public void getposts(String gu){
        Call<CMRespDto<List<PostRespDto>>> call =homeService.getposts(gu);
        call.enqueue(new Callback<CMRespDto<List<PostRespDto>>>() {
            @Override
            public void onResponse(Call<CMRespDto<List<PostRespDto>>> call, Response<CMRespDto<List<PostRespDto>>> response) {
                try {
                    CMRespDto<List<PostRespDto>> cmRespDto = response.body();
                    List<PostRespDto> posts = cmRespDto.getData();
                    Log.d(TAG, "posts: " +posts);
                    homeAdapter = new HomeAdapter(posts,activity);
                    postList.setAdapter(homeAdapter);
                } catch (Exception e) {
                    Log.d(TAG, "null");
                }
            }
            @Override
            public void onFailure(Call<CMRespDto<List<PostRespDto>>> call, Throwable t) {
                Log.d(TAG, "onFailure: 게시물목록보기 실패 !!");
            }
        });
    }

    public void searchPosts(String gu,String keyword){
        Call<CMRespDto<List<PostRespDto>>> call =homeService.searchposts(gu,keyword);
        call.enqueue(new Callback<CMRespDto<List<PostRespDto>>>() {
            @Override
            public void onResponse(Call<CMRespDto<List<PostRespDto>>> call, Response<CMRespDto<List<PostRespDto>>> response) {
                CMRespDto<List<PostRespDto>> cmRespDto = response.body();
                List<PostRespDto> posts = cmRespDto.getData();
                Log.d(TAG, "posts: " +posts);
                homeAdapter = new HomeAdapter(posts,activity);
                postList.setAdapter(homeAdapter);
            }
            @Override
            public void onFailure(Call<CMRespDto<List<PostRespDto>>> call, Throwable t) {
                Log.d(TAG, "onFailure: search 실패 !!");
            }
        });
    }
}
