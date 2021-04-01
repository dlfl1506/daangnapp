package com.cos.daangnapp.writing.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostSaveReqDto {
    private String title; // 제목
    private String content; // 내용
    private String price; // 가격
    private String gu;
    private String dong;
    private String category;
    private int userId;
}
