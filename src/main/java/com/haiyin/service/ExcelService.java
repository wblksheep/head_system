package com.haiyin.service;

import org.springframework.web.multipart.MultipartFile;

public interface ExcelService {
    void importExcel(MultipartFile file);

    String exportToExcel(String purchaseDate, String contractNumber, String headModel, String headSerial, String warehouseDate, String usageDate, String user, String usagePurpose, String installationSite, String headHistory);
}
