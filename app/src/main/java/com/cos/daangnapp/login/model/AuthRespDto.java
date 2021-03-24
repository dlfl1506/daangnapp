package com.cos.daangnapp.login.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthRespDto {
    private int id;
    private String phoneNumber;
    private String authCode;
}
