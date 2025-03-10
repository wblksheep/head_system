package com.haiyin.controller;

import com.haiyin.dto.SprinklerImportDTO;
import com.haiyin.pojo.Result;
import com.haiyin.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/excel")
//@Transactional
public class ExcelController {
    @Autowired
    private ExcelService excelService;


    @PostMapping("/import")
    public ResponseEntity<String> importExcel(@RequestParam("file") MultipartFile file){
        try {
            excelService.importExcel(file);
            return ResponseEntity.ok("Excel file imported successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to import Excel file: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/export")
    public Result<String> exportExcel(@RequestParam(required = false) String purchaseDate, @RequestParam(required = false) String contractNumber, @RequestParam(required = false) String headModel, @RequestParam(required = false) String headSerial, @RequestParam(required = false) String warehouseDate, @RequestParam(required = false) String usageDate, @RequestParam(required = false) String user, @RequestParam(required = false) String usagePurpose, @RequestParam(required = false) String installationSite, @RequestParam(required = false) String headHistory) {
        String filePath = excelService.exportToExcel(purchaseDate, contractNumber, headModel, headSerial, warehouseDate, usageDate, user, usagePurpose, installationSite, headHistory);
        return Result.success(filePath);
    }
}
