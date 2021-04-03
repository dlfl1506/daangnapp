package com.cos.daangnapp.main.home.detail.service;

import com.cos.daangnapp.CMRespDto;
import com.cos.daangnapp.main.home.model.PostRespDto;
import com.cos.daangnapp.main.home.model.PostUpdateReqDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PostUpdateService {

    @PUT("post/{id}")
    Call<CMRespDto<PostRespDto>> updatePost(@Path("id")int id, @Body PostUpdateReqDto postUpdateReqDto);
}
