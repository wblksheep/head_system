package com.haiyin.sprinkler.backend.fileprocessing.service.factory;


import com.haiyin.sprinkler.backend.fileprocessing.service.parser.rule.DAOConverterRule;
import com.haiyin.sprinkler.backend.fileprocessing.service.parser.rule.ExcelParseRule;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class DAOConverterRuleFactory {
    // 自动注入所有规则实现
    private final Map<String, DAOConverterRule<?, ?>> ruleMap;

    @Autowired
    public DAOConverterRuleFactory(List<DAOConverterRule<?, ?>> rules) {
        ruleMap = rules.stream()
                .collect(Collectors.toMap(
                   DAOConverterRule::getSceneType,
                   Function.identity()
                ));
    }

    /**
     * 根据场景类型获取解析规则
     */
    @SuppressWarnings("unchecked")
    public <T, R> DAOConverterRule<T, R> applyRule(String sceneType) {
        DAOConverterRule<?, ?> rule = ruleMap.get(sceneType.toUpperCase());
        if (rule == null) {
            throw new IllegalArgumentException("不支持的场景类型: " + sceneType);
        }
        return (DAOConverterRule<T, R>) rule;
    }
}
