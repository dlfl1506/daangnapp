package com.cos.daangnapp.login.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class UserRespDto {
    private int id;
    private String phoneNumber;
    private String nickName;
    private String photo;
    private Timestamp createDate;
}
