package com.cos.daangnapp.login.service;

import com.cos.daangnapp.CMRespDto;
import com.cos.daangnapp.login.model.UserRespDto;
import com.cos.daangnapp.login.model.UserSaveReqDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserService {

    @GET("user/nickName")
    Call<CMRespDto<UserRespDto>> NickNameSearch(@Query("nickName")String nickName);

    @POST("user")
    Call<CMRespDto<UserRespDto>> save(@Body UserSaveReqDto userSaveReqDto);
}
