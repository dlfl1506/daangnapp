package com.cos.daangnapp.writing.service;

import com.cos.daangnapp.CMRespDto;
import com.cos.daangnapp.writing.model.PostSaveReqDto;
import com.cos.daangnapp.writing.model.PostSaveRespDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PostService {

    @POST("post")
    Call<CMRespDto<PostSaveRespDto>> save(@Body PostSaveReqDto postSaveReqDto , @Query("userId") int userId);
}
