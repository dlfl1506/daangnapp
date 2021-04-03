package com.cos.daangnapp.main.home.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostUpdateReqDto {
    private String title; // 제목
    private String content; // 내용
    private String price; // 가격 // 가격
    private String category;
}
