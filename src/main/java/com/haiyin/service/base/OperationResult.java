package com.haiyin.service.base;

import java.util.List;

/**
 * 业务操作结果封装类
 * @param <T> 操作成功的记录类型
 */
public class OperationResult<T> {
    // 操作成功的记录集合
    private final List<T> successRecords;

    // 操作失败的记录集合（包含异常信息）
    private final List<OperationError<T>> failedRecords;

    public OperationResult(List<T> successRecords, List<OperationError<T>> failedRecords) {
        this.successRecords = successRecords;
        this.failedRecords = failedRecords;
    }

    public List<?> getFailedRecords() {
        return null;
    }

    // 操作错误明细类
    public static class OperationError<E> {
        private final E failedRecord;   // 原始记录对象
        private final Exception cause; // 失败异常对象

        public OperationError(E failedRecord, Exception cause) {
            this.failedRecord = failedRecord;
            this.cause = cause;
        }
    }
}
