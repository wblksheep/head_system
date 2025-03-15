package com.haiyin.service.interfaces;

import com.haiyin.dto.SprinklerAllocationDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileParser<T> {
    List<T> parseFiles(MultipartFile... files);
}
