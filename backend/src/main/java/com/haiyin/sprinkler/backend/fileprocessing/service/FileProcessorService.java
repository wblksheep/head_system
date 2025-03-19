package com.haiyin.sprinkler.backend.fileprocessing.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileProcessorService {
    List<?> processUpload(MultipartFile[] files, String sceneType);
}
