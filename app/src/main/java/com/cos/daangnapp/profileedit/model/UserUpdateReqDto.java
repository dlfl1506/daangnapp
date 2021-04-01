package com.cos.daangnapp.profileedit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserUpdateReqDto {
    private String nickName;
    private String photo;
}
