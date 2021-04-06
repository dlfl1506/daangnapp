package com.cos.daangnapp.main.home;

import com.cos.daangnapp.CMRespDto;
import com.cos.daangnapp.main.home.model.PostRespDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HomeService {

    @GET("post/gu")
    Call<CMRespDto<List<PostRespDto>>> getposts(@Query("gu") String gu);

    @GET("post/gu/keyword")
    Call<CMRespDto<List<PostRespDto>>> searchposts(@Query("gu") String gu,@Query("keyword") String keyword);
}
