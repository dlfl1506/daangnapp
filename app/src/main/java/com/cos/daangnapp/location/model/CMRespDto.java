package com.cos.daangnapp.location.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CMRespDto<T> {
    private int code;
    private T data;
}