package com.haiyin.sprinkler.backend.fileprocessing.service.saver;

import com.haiyin.sprinkler.backend.fileprocessing.dao.SprinklerDAO;
import com.haiyin.sprinkler.backend.fileprocessing.service.factory.SprinklerSaverRuleFactory;
import com.haiyin.sprinkler.backend.fileprocessing.service.saver.rule.SprinklerSaverRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SprinklerSaver {
    private final SprinklerSaverRuleFactory ruleFactory;

    @Autowired
    public SprinklerSaver(SprinklerSaverRuleFactory ruleFactory) {
        this.ruleFactory = ruleFactory;
    }

    public List<Long> batchUpsert(List<SprinklerDAO> daos, String sceneType){

        // 获取对应场景的规则
        SprinklerSaverRule rule = ruleFactory.applyRule(sceneType);
        // 执行解析
        return rule.parse(daos);
    }
}
