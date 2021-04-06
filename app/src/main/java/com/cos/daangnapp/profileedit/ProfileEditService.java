package com.cos.daangnapp.profileedit;

import com.cos.daangnapp.CMRespDto;
import com.cos.daangnapp.login.model.UserRespDto;
import com.cos.daangnapp.profileedit.model.UserUpdateReqDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProfileEditService {


    @PUT("user/{id}")
    Call<CMRespDto<UserRespDto>> update(@Path("id")int id, @Body UserUpdateReqDto userUpdateReqDto);

}
