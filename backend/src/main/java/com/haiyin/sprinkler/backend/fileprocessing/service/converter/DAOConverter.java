package com.haiyin.sprinkler.backend.fileprocessing.service.converter;

import com.haiyin.sprinkler.backend.fileprocessing.service.factory.DAOConverterRuleFactory;
import com.haiyin.sprinkler.backend.fileprocessing.service.parser.rule.DAOConverterRule;
import com.haiyin.sprinkler.backend.fileprocessing.service.parser.rule.ExcelParseRule;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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
