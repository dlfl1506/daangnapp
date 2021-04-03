package com.cos.daangnapp.main.home.detail.service;

import com.cos.daangnapp.CMRespDto;
import com.cos.daangnapp.main.home.model.PostRespDto;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DetailService {

    @GET("post/{id}")
    Call<CMRespDto<PostRespDto>> getpost(@Path("id")int id);

    @DELETE("post/{id}")
    Call <CMRespDto> removePost(@Path("id")int id);
}
