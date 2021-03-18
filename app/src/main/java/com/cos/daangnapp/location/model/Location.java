package com.cos.daangnapp.location.model;

import lombok.Data;

@Data
public class Location {
    private String sdm;   // 시도명
    private String sggm;  // 시군구명
    private String ghhjd; // 관할행정동
    private double entX;  // x좌표
    private double entY;  // y좌표


}
