package com.haiyin.sprinkler.backend.fileprocessing.service.converter.impl;

import com.haiyin.sprinkler.backend.fileprocessing.dao.HeadStatus;
import com.haiyin.sprinkler.backend.fileprocessing.dao.SprinklerDAO;
import com.haiyin.sprinkler.backend.fileprocessing.dto.MaintainDTO;
import com.haiyin.sprinkler.backend.fileprocessing.service.converter.DAOConverterRule;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("maintainConverterRule")
public class MaintainConverterRule implements DAOConverterRule<MaintainDTO, SprinklerDAO> {

    @Override
    public SprinklerDAO convert(MaintainDTO dto) {
        SprinklerDAO s = new SprinklerDAO();
        s.setHeadSerial(dto.getHeadSerial());
        s.setUser(dto.getUser());
        s.setUsagePurpose(dto.getUsagePurpose());
        s.setColor(dto.getColor());
        s.setPosition(dto.getPosition());
        s.setStatus(HeadStatus.IN_USE);
        return s;
    }

    @Override
    public String getSceneType() {
        return "MAINTAIN";
    }
}
