package com.haiyin.sprinkler.backend.fileprocessing.service.converter.impl;

import com.haiyin.sprinkler.backend.fileprocessing.dao.HeadStatus;
import com.haiyin.sprinkler.backend.fileprocessing.dao.SprinklerDAO;
import com.haiyin.sprinkler.backend.fileprocessing.dto.AllocateDTO;
import com.haiyin.sprinkler.backend.fileprocessing.service.parser.rule.DAOConverterRule;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("allocate2ConverterRule")
public class Allocate2ConverterRule implements DAOConverterRule<AllocateDTO, SprinklerDAO> {

    @Override
    public SprinklerDAO convert(AllocateDTO dto) {
        SprinklerDAO s = new SprinklerDAO();
        s.setHeadSerial(dto.getHeadSerial());
        s.setUser(dto.getUser());
        s.setUsagePurpose(dto.getUsagePurpose());
        s.setColor(dto.getColor());
        s.setPosition(dto.getPosition());
        s.setStatus(HeadStatus.IN_STOCK);
        return s;
    }

    @Override
    public String getSceneType() {
        return "ALLOCATE2";
    }
}
