package com.haiyin.sprinkler.backend.fileprocessing.service.converter.impl;

import com.haiyin.sprinkler.backend.fileprocessing.dao.HeadStatus;
import com.haiyin.sprinkler.backend.fileprocessing.dao.SprinklerDAO;
import com.haiyin.sprinkler.backend.fileprocessing.dto.AllocateDTO;
import com.haiyin.sprinkler.backend.fileprocessing.service.converter.DAOConverterRule;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("allocateConverterRule")
public class AllocateConverterRule implements DAOConverterRule<AllocateDTO, SprinklerDAO> {

    @Override
    public SprinklerDAO convert(AllocateDTO dto) {
        SprinklerDAO s = new SprinklerDAO();
        s.setHeadSerial(dto.getHeadSerial());
        s.setUser(dto.getUser());
        s.setUsagePurpose(dto.getUsagePurpose());
        s.setUsageDate(dto.getUsageDate());
        s.setColor(dto.getColor());
        s.setPosition(dto.getPosition());
        s.setHeadHistory(dto.getHistory());
        s.setStatus(HeadStatus.IN_STOCK);
        return s;
    }

    @Override
    public String getSceneType() {
        return "ALLOCATE";
    }
}
