package com.haiyin.service.interfaces;

import com.haiyin.service.base.FileParseResult;
import org.springframework.web.multipart.MultipartFile;

public interface FileParser {
    FileParseResult parseFiles(MultipartFile... files);
}
