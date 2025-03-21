package com.haiyin.sprinkler.backend.fileprocessing.service.converter.impl;

import com.haiyin.sprinkler.backend.fileprocessing.dao.HeadStatus;
import com.haiyin.sprinkler.backend.fileprocessing.dao.SprinklerDAO;
import com.haiyin.sprinkler.backend.fileprocessing.dto.DamageDTO;
import com.haiyin.sprinkler.backend.fileprocessing.service.converter.DAOConverterRule;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("damageConverterRule")
public class DamageConverterRule implements DAOConverterRule<DamageDTO, SprinklerDAO> {

    @Override
    public SprinklerDAO convert(DamageDTO dto) {
        SprinklerDAO s = new SprinklerDAO();
        s.setDamageDate(dto.getDamageDate());
        s.setHeadSerial(dto.getHeadSerial());
        s.setHeadHistory(dto.getHeadSerial());
        s.setNote(dto.getNote());
        s.setDamageType(dto.getDamageType());
        s.setRealType(dto.getRealType());
        s.setStatus(HeadStatus.UNDER_MAINTENANCE);
        s.setVersion(0);
        return s;
    }

    @Override
    public String getSceneType() {
        return "DAMAGE";
    }
}
