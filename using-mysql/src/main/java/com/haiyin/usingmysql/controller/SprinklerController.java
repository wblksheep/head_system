package com.haiyin.usingmysql.controller;

import com.haiyin.usingmysql.dto.SprinklerAllocationDTO;
import com.haiyin.usingmysql.exception.ConcurrentUpdateException;
import com.haiyin.usingmysql.pojo.ApiResponse;
import com.haiyin.usingmysql.service.SprinklerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sprinklers")
@RequiredArgsConstructor
public class SprinklerController {
    @Autowired
    private SprinklerService sprinklerService;

    @PutMapping("/allocate")
    public ResponseEntity<ApiResponse> allocateSprinkler(
            @Valid @RequestBody SprinklerAllocationDTO dto) {

        sprinklerService.allocateSprinkler(dto);
        return ResponseEntity.ok(ApiResponse.success("喷头领用成功"));
    }

    @ExceptionHandler(ConcurrentUpdateException.class)
    public ResponseEntity<ApiResponse> handleConcurrentUpdate() {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponse.error("数据版本冲突，请刷新后重试"));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiResponse> handleIllegalState(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(ex.getMessage()));
    }
}
