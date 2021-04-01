package com.cos.daangnapp.main.profile;

import com.cos.daangnapp.CMRespDto;
import com.cos.daangnapp.login.model.UserRespDto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProfileService {
    @GET("user/{id}")
    Call<CMRespDto<UserRespDto>> getuser(@Path("id")int id);
}
