package com.cos.daangnapp.writing.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ImageSaveReqdto {
    private String uri;
    private Integer postId;
}
