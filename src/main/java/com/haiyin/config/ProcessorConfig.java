package com.haiyin.config;

import com.haiyin.service.impl.AllocationProcessor;
import com.haiyin.service.interfaces.FileParser;
import com.haiyin.service.interfaces.StatusOperator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

//@Configuration
public class ProcessorConfig {
//    @Bean
//    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//    public AllocationProcessor allocationProcessor(
//            @Qualifier("allocationFileParser") FileParser fileParser,
//            @Qualifier("inventoryStatusOperator")StatusOperator statusOperator
//            ) {
//        return new AllocationProcessor(fileParser, statusOperator);
//    }

}
