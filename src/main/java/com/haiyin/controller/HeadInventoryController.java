package com.haiyin.controller;

import com.haiyin.dto.SprinklerAllocationDTO;
import com.haiyin.pojo.Article;
import com.haiyin.pojo.Category;
import com.haiyin.pojo.HeadInventory;
import com.haiyin.pojo.PageBean;
import com.haiyin.pojo.Result;
import com.haiyin.service.HeadInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/head")
@Transactional
public class HeadInventoryController {

    @Autowired
    private HeadInventoryService headInventoryService;

    @GetMapping
    public Result<PageBean<HeadInventory>> list(
            Integer pageNum,
            Integer pageSize
    ) {
        PageBean<HeadInventory> pb = headInventoryService.list(pageNum, pageSize);
        return Result.success(pb);
    }

    @PostMapping
    public Result<PageBean<SprinklerAllocationDTO>> batchAllocate(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("excelFile") MultipartFile excelFile, @RequestParam(value = "txtFile") MultipartFile txtFile) {
        PageBean<SprinklerAllocationDTO> pb = headInventoryService.batchAllocate(pageNum, pageSize, excelFile, txtFile);
        return Result.success(pb);
    }

    @GetMapping("/diff")
    public Result<PageBean<SprinklerAllocationDTO>> getDiff(
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam("excelFile") MultipartFile excelFile,
            @RequestParam(value = "txtFile") MultipartFile txtFile
    ) {
        PageBean<SprinklerAllocationDTO> pb = headInventoryService.getDiff(pageNum, pageSize, excelFile, txtFile);
        return Result.success(pb);
    }

//    @PostMapping("modify")
//    public Result<PageBean<SprinklerAllocationDTO>> batchAllocate(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize, @RequestParam("excelFile") MultipartFile excelFile, @RequestParam(value = "txtFile") MultipartFile txtFile) {
//        PageBean<SprinklerAllocationDTO> pb = headInventoryService.batchAllocate(pageNum, pageSize, excelFile, txtFile);
//        return Result.success(pb);
//    }


//    public Result<List<String>> list(){
//        List<String> result = headInventoryService.list();
//        return Result.success(result);
//    }
}
