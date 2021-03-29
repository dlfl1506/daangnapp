package com.cos.daangnapp.main;

import com.cos.daangnapp.CMRespDto;
import com.cos.daangnapp.main.model.PostRespDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MainService {

    @GET("post/gu")
    Call<CMRespDto<List<PostRespDto>>> getposts(@Query("gu") String gu);
}
