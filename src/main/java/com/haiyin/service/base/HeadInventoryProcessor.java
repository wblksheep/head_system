package com.haiyin.service.base;

import com.haiyin.pojo.PageBean;
import com.haiyin.service.interfaces.FileParser;
import com.haiyin.service.interfaces.StatusOperator;
import lombok.Data;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Data
public abstract class HeadInventoryProcessor<T> {
    protected final FileParser fileParser;
    protected final StatusOperator statusOperator;

    public HeadInventoryProcessor(FileParser fileParser, StatusOperator statusOperator) {
        this.fileParser = fileParser;
        this.statusOperator = statusOperator;
    }

    @RefreshScope
    // 模板方法
    public final PageBean<T> process(Integer pageNum, Integer pageSize, MultipartFile... files) {
//        // 1. 解析文件
//        List<T> parseResult = fileParser.parseFiles(files);
//
//        // 2. 校验数据
//        ValidationResult<T> validationResult = validate(parseResult);
//
//        // 3. 执行操作
//        OperationResult<T> operationResult = executeOperation(validationResult.getValidRecords());
//
//        // 4. 构建响应
//        return buildResponse(pageNum, pageSize,
//                validationResult.getInvalidRecords(),
//                operationResult.getFailedRecords());
        return null;
    }
    // 调整抽象方法签名
    protected abstract ValidationResult<T> validate(FileParseResult parseResult);

    protected abstract OperationResult<T> executeOperation(List<T> validRecords);

    protected abstract PageBean<T> buildResponse(Integer pageNum, Integer pageSize,
                                                 List<?> invalidRecords, List<?> failedRecords);
}
