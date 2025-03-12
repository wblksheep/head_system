package com.haiyin.service.impl;

import com.haiyin.dto.SprinklerAllocationDTO;
import com.haiyin.pojo.PageBean;
import com.haiyin.service.base.FileParseResult;
import com.haiyin.service.base.HeadInventoryProcessor;
import com.haiyin.service.base.OperationResult;
import com.haiyin.service.base.ValidationResult;
import com.haiyin.service.interfaces.FileParser;
import com.haiyin.service.interfaces.StatusOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AllocationProcessor extends HeadInventoryProcessor<SprinklerAllocationDTO> {

    @Autowired
    public AllocationProcessor(@Qualifier("allocationFileParser") FileParser allocationParser,@Qualifier("inventoryStatusOperator") StatusOperator allocator) {
        super(allocationParser, allocator);
    }

    @Override
    protected ValidationResult<SprinklerAllocationDTO> validate(FileParseResult parseResult) {
        // 实现领用专用的校验逻辑
        return null;
    }

    @Override
    protected OperationResult<SprinklerAllocationDTO> executeOperation(List<SprinklerAllocationDTO> validRecords) {
        return null;
    }

    @Override
    protected PageBean<SprinklerAllocationDTO> buildResponse(Integer pageNum, Integer pageSize, List<?> invalidRecords, List<?> failedRecords) {
        return null;
    }

}
