package com.cau.artchive.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class PresignedUploadUrlResponse {
    private String uploadUrl;
    private String objectKey;
    private int expiresInSec;
    private Map<String, String> requiredHeaders;
}

