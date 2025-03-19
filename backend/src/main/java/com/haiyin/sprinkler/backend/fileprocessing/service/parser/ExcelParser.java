package com.haiyin.sprinkler.backend.fileprocessing.service.parser;

import com.haiyin.sprinkler.backend.fileprocessing.service.factory.ExcelParseRuleFactory;
import com.haiyin.sprinkler.backend.fileprocessing.service.parser.rule.ExcelParseRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class ExcelParser {
    private final ExcelParseRuleFactory ruleFactory;

    @Autowired
    public ExcelParser(ExcelParseRuleFactory ruleFactory) {
        this.ruleFactory = ruleFactory;
    }

    public <T> List<T> parseByStream(InputStream stream, String sceneType) throws IOException {
        // 获取对应场景的规则
        ExcelParseRule<T> rule = ruleFactory.applyRule(sceneType);
        // 执行解析
        return rule.parse(stream);
    }
}
