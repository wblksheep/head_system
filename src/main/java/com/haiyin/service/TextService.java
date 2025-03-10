package com.haiyin.service;

import org.springframework.web.multipart.MultipartFile;

public interface TextService {
    void importText(MultipartFile file);
}
