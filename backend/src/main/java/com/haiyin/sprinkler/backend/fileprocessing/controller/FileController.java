package com.haiyin.sprinkler.backend.fileprocessing.controller;

import com.haiyin.sprinkler.backend.fileprocessing.service.FileProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FileController {

    @Autowired
    FileProcessorService fileProcessorService;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(
            @RequestParam("files") MultipartFile[] files,
            @RequestParam("sceneType") String sceneType) {
        List<?> dtos = fileProcessorService.processUpload(files, sceneType);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }
}
