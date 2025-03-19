package com.haiyin.sprinkler.backend.fileprocessing.service.factory;

import com.haiyin.sprinkler.backend.fileprocessing.service.parser.rule.ExcelParseRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ExcelParseRuleFactory {
    // 自动注入所有规则实现
    private final Map<String, ExcelParseRule<?>> ruleMap;

    @Autowired
    public ExcelParseRuleFactory(List<ExcelParseRule<?>> rules) {
        // 构建 sceneType -> Rule 的映射
        ruleMap = rules.stream()
                .collect(Collectors.toMap(
                        ExcelParseRule::getSceneType,
                        Function.identity()
                ));
    }

    /**
     * 根据场景类型获取解析规则
     */
    @SuppressWarnings("unchecked")
    public <T> ExcelParseRule<T> applyRule(String sceneType) {
        ExcelParseRule<?> rule = ruleMap.get(sceneType.toUpperCase());
        if (rule == null) {
            throw new IllegalArgumentException("不支持的场景类型: " + sceneType);
        }
        return (ExcelParseRule<T>) rule;
    }
}
