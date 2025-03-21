package com.haiyin.sprinkler.backend.fileprocessing.service.converter;

import com.haiyin.sprinkler.backend.fileprocessing.service.factory.DAOConverterRuleFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DAOConverter {
    private final DAOConverterRuleFactory ruleFactory;

    @Autowired
    public DAOConverter(DAOConverterRuleFactory ruleFactory){
        this.ruleFactory = ruleFactory;
    }

    public <T, R> DAOConverterRule<T, R> parseByStream(String sceneType) {
        // 获取对应场景的规则
        DAOConverterRule<T, R> rule = ruleFactory.applyRule(sceneType);
        // 执行解析
        return rule;
    }
}
