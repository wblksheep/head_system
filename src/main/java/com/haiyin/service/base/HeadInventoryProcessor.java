package com.haiyin.service.base;

import com.haiyin.service.interfaces.FileParser;

public abstract class HeadInventoryProcessor<T> {
    protected final FileParser fileParser;
    protected final StatusOperator statusOperator;
}
