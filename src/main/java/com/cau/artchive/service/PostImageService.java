package com.cau.artchive.service;

import com.cau.artchive.dto.PostImageUploadUrlRequest;
import com.cau.artchive.dto.PresignedUploadUrlResponse;
import com.cau.artchive.global.exception.BusinessException;
import com.cau.artchive.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostImageService {

    private final S3Presigner s3Presigner;

    private static final List<String> ALLOWED_TYPES =
            List.of("image/jpeg", "image/png");

    public PresignedUploadUrlResponse createUploadUrl(PostImageUploadUrlRequest request) {

        if (!ALLOWED_TYPES.contains(request.getContentType())) {
            throw new BusinessException(ErrorCode.INVALID_CONTENT_TYPE);
        }

        String extension = request.getContentType().equals("image/png") ? "png" : "jpg";
        String objectKey = "posts/temp/" + UUID.randomUUID() + "." + extension;

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket("artchives3")
                .key(objectKey)
                .contentType(request.getContentType())
                .build();

        PutObjectPresignRequest presignRequest =
                PutObjectPresignRequest.builder()
                        .signatureDuration(Duration.ofMinutes(5))
                        .putObjectRequest(putObjectRequest)
                        .build();

        PresignedPutObjectRequest presigned =
                s3Presigner.presignPutObject(presignRequest);

        return PresignedUploadUrlResponse.builder()
                .uploadUrl(presigned.url().toString())
                .objectKey(objectKey)
                .expiresInSec(300)
                .requiredHeaders(Map.of(
                        "Content-Type", request.getContentType()
                ))
                .build();
    }
}
