package com.haiyin.service;

import com.haiyin.dto.SprinklerAllocationDTO;
import com.haiyin.dto.SprinklerMaintainDTO;
import com.haiyin.pojo.HeadInventory;
import com.haiyin.pojo.PageBean;
import org.springframework.web.multipart.MultipartFile;

public interface HeadInventoryService {



    PageBean<SprinklerAllocationDTO> batchAllocate(Integer pageNum, Integer pageSize, MultipartFile excelFile, MultipartFile txtFile);

    PageBean<HeadInventory> list(Integer pageNum, Integer pageSize);

    PageBean<SprinklerAllocationDTO> getDiff(Integer pageNum, Integer pageSize, MultipartFile excelFile, MultipartFile txtFile);

    PageBean<SprinklerMaintainDTO> batchMaintain(Integer pageNum, Integer pageSize, MultipartFile excelFile);


//    List<String> list();
}
