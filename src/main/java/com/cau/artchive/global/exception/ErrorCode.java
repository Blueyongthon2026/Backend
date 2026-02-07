package com.cau.artchive.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    DUPLICATE_USER(HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED),
    INVALID_CONTENT_TYPE(HttpStatus.BAD_REQUEST),
    INVALID_COVER_IMAGE(HttpStatus.BAD_REQUEST),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND);

    private final HttpStatus status;
}

