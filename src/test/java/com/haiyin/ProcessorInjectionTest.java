package com.haiyin;

import com.haiyin.service.impl.AllocationProcessor;
import com.haiyin.service.interfaces.impl.AllocationFileParser;
import com.haiyin.service.interfaces.impl.InventoryStatusOperator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ProcessorInjectionTest {
    @Autowired
    private AllocationProcessor processor;

    @Test
    void shouldInjectDependencies() {
        assertNotNull(processor);
        assertTrue(processor.getFileParser() instanceof AllocationFileParser);
        assertTrue(processor.getStatusOperator() instanceof InventoryStatusOperator);
    }
}
