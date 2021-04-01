package com.cos.daangnapp.main.home.model;

import com.cos.daangnapp.login.model.UserRespDto;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

@Data
public class PostRespDto {
    private Integer id; // id
    private String title; // 제목
    private String content; // 내용
    private String price; // 가격
    private List<ImageRespDto> images;
    private int favorite; // 관심수
    private int count; //조회수
    private UserRespDto user;
    private String category;
    private String gu;
    private String dong;
    private Timestamp createDate;
}
