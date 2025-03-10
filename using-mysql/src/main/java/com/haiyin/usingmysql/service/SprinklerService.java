package com.haiyin.usingmysql.service;

import com.haiyin.usingmysql.dto.SprinklerAllocationDTO;
import com.haiyin.usingmysql.dto.SprinklerImportDTO;
import com.haiyin.usingmysql.dto.SprinklerStatus;
import com.haiyin.usingmysql.exception.ConcurrentUpdateException;
import com.haiyin.usingmysql.exception.SprinklerNotFoundException;
import com.haiyin.usingmysql.mapper.SprinklerMapper;
import com.haiyin.usingmysql.pojo.HeadInventory;
import com.haiyin.usingmysql.pojo.Sprinkler;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
//@RequiredArgsConstructor
public class SprinklerService {
    @Autowired
    private SprinklerMapper sprinklerMapper;

    @Transactional
    public void allocateSprinkler(SprinklerAllocationDTO sprinklerDTO) {
        // 获取当前喷头信息
        Sprinkler entity = sprinklerMapper.findByNo(sprinklerDTO.getSprinklerNo());
        if (entity == null) {
            throw new SprinklerNotFoundException("喷头不存在: " + sprinklerDTO.getSprinklerNo());
        }

        // 校验当前状态
        if (entity.getStatus() != SprinklerStatus.IN_STOCK) {
            throw new IllegalStateException("喷头当前不可领用，状态为: " + entity.getStatus().getDescription());
        }

        // 执行更新
        int updated = sprinklerMapper.allocateSprinkler(
                sprinklerDTO.getSprinklerNo(),
                SprinklerStatus.IN_STOCK,  // 原状态
                SprinklerStatus.IN_USE,    // 新状态
                sprinklerDTO.getOwner(),
                sprinklerDTO.getMachine(),
                sprinklerDTO.getColor(),
                sprinklerDTO.getPosition(),
                sprinklerDTO.getType(),
                sprinklerDTO.getUsageDate(),
                sprinklerDTO.getHistory(),
                entity.getVersion()
        );

        if (updated == 0) {
            throw new ConcurrentUpdateException("喷头状态已被其他操作修改");
        }
    }

    @Transactional
    public void batchAllocateSprinklers(List<SprinklerAllocationDTO> dtos) {
        for (SprinklerAllocationDTO dto : dtos) {
            allocateSprinkler(dto); // 复用单条处理逻辑
        }
    }

    @Transactional
    public void importSprinkler(SprinklerAllocationDTO sprinklerDTO) {
        // 获取当前喷头信息
        Sprinkler entity = sprinklerMapper.findByNo(sprinklerDTO.getSprinklerNo());
        if (entity == null) {
            throw new SprinklerNotFoundException("喷头不存在: " + sprinklerDTO.getSprinklerNo());
        }

        // 校验当前状态
        if (entity.getStatus() != SprinklerStatus.IN_STOCK) {
            throw new IllegalStateException("喷头当前不可领用，状态为: " + entity.getStatus().getDescription());
        }

        // 执行更新
        int updated = sprinklerMapper.allocateSprinkler(
                sprinklerDTO.getSprinklerNo(),
                SprinklerStatus.IN_STOCK,  // 原状态
                SprinklerStatus.IN_USE,    // 新状态
                sprinklerDTO.getOwner(),
                sprinklerDTO.getMachine(),
                sprinklerDTO.getColor(),
                sprinklerDTO.getPosition(),
                sprinklerDTO.getType(),
                sprinklerDTO.getUsageDate(),
                sprinklerDTO.getHistory(),
                entity.getVersion()
        );

        if (updated == 0) {
            throw new ConcurrentUpdateException("喷头状态已被其他操作修改");
        }
    }

//    public void batchImportSprinklers(List<SprinklerImportDTO> dtos) {
//        for (SprinklerImportDTO dto : dtos) {
//            allocateSprinkler(dto); // 复用单条处理逻辑
//        }
//    }
}
