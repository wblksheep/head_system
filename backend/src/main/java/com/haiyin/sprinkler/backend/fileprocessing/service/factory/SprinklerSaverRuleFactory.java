package com.haiyin.sprinkler.backend.fileprocessing.service.factory;

import com.haiyin.sprinkler.backend.fileprocessing.service.saver.rule.SprinklerSaverRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class SprinklerSaverRuleFactory {
    // 自动注入所有规则实现
    private final Map<String, SprinklerSaverRule> ruleMap;

    @Autowired
    public SprinklerSaverRuleFactory(List<SprinklerSaverRule> rules) {
        ruleMap = rules.stream()
                .collect(Collectors.toMap(
                        SprinklerSaverRule::getSceneType,
                        Function.identity()
                ));
    }

    /**
     * 根据场景类型获取解析规则
     */
    @SuppressWarnings("unchecked")
    public SprinklerSaverRule applyRule(String sceneType) {
        SprinklerSaverRule rule = ruleMap.get(sceneType.toUpperCase());
        if (rule == null) {
            throw new IllegalArgumentException("不支持的场景类型: " + sceneType);
        }
        return rule;
    }
}
