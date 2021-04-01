package com.cos.daangnapp.writing.model;

import com.cos.daangnapp.login.model.UserRespDto;
import com.cos.daangnapp.main.home.model.ImageRespDto;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

@Data
public class PostSaveRespDto {
    private int id;
    private String title; // 제목
    private String content; // 내용
    private String price; // 가격
    private int favorite;
    private List<ImageRespDto> images;
    private  int count;
    private UserRespDto user;
    private String gu;
    private String dong;
    private String category;
    private Timestamp createDate;
}
