package com.cau.artchive.controller;

import com.cau.artchive.dto.PostImageUploadUrlRequest;
import com.cau.artchive.dto.PresignedUploadUrlResponse;
import com.cau.artchive.global.response.ApiResponse;
import com.cau.artchive.service.PostImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts/images")
@RequiredArgsConstructor
public class PostImageController {

    private final PostImageService postImageService;

    @PostMapping("/upload-url")
    public ApiResponse<PresignedUploadUrlResponse> issueUploadUrl(
            @RequestBody PostImageUploadUrlRequest request
    ) {
        return ApiResponse.success(
                postImageService.createUploadUrl(request),
                "UPLOAD_URL_CREATED"
        );
    }
}

