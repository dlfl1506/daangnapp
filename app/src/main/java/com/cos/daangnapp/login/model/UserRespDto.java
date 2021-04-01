package com.cos.daangnapp.login.model;

import com.cos.daangnapp.main.home.model.PostRespDto;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

@Data
public class UserRespDto {
    private int id;
    private String phoneNumber;
    private String nickName;
    private String photo;
    private List<PostRespDto> posts;
    private Timestamp createDate;
}
