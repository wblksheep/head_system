package com.haiyin.sprinkler.backend.infrastructure.exception;

import com.haiyin.sprinkler.backend.infrastructure.exception.errorcode.ErrorCode;

public class ResourceException extends RuntimeException{
    private final ErrorCode code;

    public ResourceException(ErrorCode code, String message) {
        super(message);
        this.code = code;
    }

}
