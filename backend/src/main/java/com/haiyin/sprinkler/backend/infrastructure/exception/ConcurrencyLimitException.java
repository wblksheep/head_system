package com.haiyin.sprinkler.backend.infrastructure.exception;

import com.haiyin.sprinkler.backend.infrastructure.exception.errorcode.ErrorCode;

// 具体异常子类示例
public class ConcurrencyLimitException extends ResourceException {
  public ConcurrencyLimitException(int max) {
    super(ErrorCode.CONCURRENCY_LIMIT_EXCEEDED,
            "超过最大并发限制: " + max);
  }
}
