package com.cos.daangnapp.main.model;

import android.net.Uri;

import com.cos.daangnapp.login.model.UserRespDto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class PostRespDto {
    private Integer id; // id
    private String title; // 제목
    private String content; // 내용
    private String price; // 가격
    private Uri img; // 이미지 사진
    private int favorite; // 관심수
    private int count; //조회수
    private UserRespDto user;
    private String category;
    private String gu;
    private String dong;
    private Timestamp createDate;
}
