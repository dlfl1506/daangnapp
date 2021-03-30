package com.cos.daangnapp.writing.model;

import com.cos.daangnapp.login.model.UserRespDto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class PostSaveRespDto {
    private int id;
    private String title; // 제목
    private String content; // 내용
    private String price; // 가격
    private String img; // 이미지 사진
    private int favorite;
    private  int count;
    private UserRespDto userRespDto;
    private String gu;
    private String dong;
    private String category;
    private Timestamp createDate;
}
