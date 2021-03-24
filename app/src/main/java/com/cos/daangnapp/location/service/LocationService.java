package com.cos.daangnapp.location.service;

import com.cos.daangnapp.CMRespDto;
import com.cos.daangnapp.location.model.LocationReqDto;
import com.cos.daangnapp.location.model.LocationRespDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LocationService {

    @POST("location")
    Call<CMRespDto<List<LocationRespDto>>> getLocations(@Body LocationReqDto locationReqDto);

    @GET("location")
    Call<CMRespDto<List<LocationRespDto>>> getLocations(@Query("address") String address );

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.219.104:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
