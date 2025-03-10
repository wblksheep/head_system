package com.haiyin.controller;


import com.haiyin.service.TextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/txt")
public class TextController {

    @Autowired
    private TextService textService;

    @PostMapping("/importTxt")
    public ResponseEntity<String> importTxt(@RequestParam("file") MultipartFile file) {
        try{
            textService.importText(file);
            return ResponseEntity.ok("Import complete.");
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Failed to import Text file: "+ e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
