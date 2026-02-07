package com.cau.artchive.dto;

import lombok.Getter;

@Getter
public class PostImageUploadUrlRequest {
    private String contentType; // image/jpeg
    private String usage;       // POST_COVER
}