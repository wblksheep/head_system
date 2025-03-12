package com.haiyin.service.base;

import lombok.Data;

import java.util.List;

/**
 * 数据校验结果封装类
 * @param <T> 校验通过的记录类型
 */
@Data
public class ValidationResult<T> {
    // 校验通过的记录集合
    private final List<T> validRecords;

    // 校验失败的记录集合（包含失败原因）
    private final List<ValidationError<T>> invalidRecords;

    public ValidationResult(List<T> validRecords, List<ValidationError<T>> invalidRecords) {
        this.validRecords = validRecords;
        this.invalidRecords = invalidRecords;
    }



    // 校验错误明细类
    public static class ValidationError<E> {
        private final E failedRecord;  // 原始记录对象
        private final String errorMsg; // 失败原因

        public ValidationError(E failedRecord, String errorMsg) {
            this.failedRecord = failedRecord;
            this.errorMsg = errorMsg;
        }
    }
}
