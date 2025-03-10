package com.haiyin.controller;

import com.haiyin.pojo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {


    @GetMapping("/autocomplete")
    public Result<List<String>> autocomplete(){
        return null;
    }
}
