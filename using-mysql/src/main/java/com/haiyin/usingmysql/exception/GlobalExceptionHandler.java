package com.haiyin.usingmysql.exception;

import com.haiyin.usingmysql.pojo.ApiResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ApiResponse handleException(Exception e) {
        e.printStackTrace();
        return ApiResponse.error(StringUtils.hasLength(e.getMessage()) ? e.getMessage() : "操作失败");
    }
}
