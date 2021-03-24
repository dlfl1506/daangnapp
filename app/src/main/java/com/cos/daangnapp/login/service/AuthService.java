package com.cos.daangnapp.login.service;

import com.cos.daangnapp.CMRespDto;
import com.cos.daangnapp.login.model.AuthReqDto;
import com.cos.daangnapp.login.model.AuthRespDto;
import com.cos.daangnapp.login.model.UserRespDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface AuthService {


    @GET("auth")
    Call<CMRespDto<AuthRespDto>> authCodeSearch(@Query("phoneNumber")String phoneNumber);

    @POST("auth")
    Call<CMRespDto<AuthRespDto>> authCodeSend(@Body AuthReqDto authReqDto);

    @DELETE("auth")
    Call<CMRespDto> deleteCode(@Query("id")int id);

    @PUT("auth")
    Call<CMRespDto<AuthRespDto>> updateAuthCode(@Query("id")int id,@Query("authCode")String authCode);

    @GET("user/phoneNumber")
    Call<CMRespDto<UserRespDto>> UserSearch(@Query("phoneNumber")String phoneNumber);


}
