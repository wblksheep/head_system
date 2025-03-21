package com.haiyin.sprinkler.backend.fileprocessing.service.converter.impl;

import com.haiyin.sprinkler.backend.fileprocessing.dao.HeadStatus;
import com.haiyin.sprinkler.backend.fileprocessing.dao.SprinklerDAO;
import com.haiyin.sprinkler.backend.fileprocessing.dto.ImportDTO;
import com.haiyin.sprinkler.backend.fileprocessing.service.converter.DAOConverterRule;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("importConverterRule")
public class ImportConverterRule implements DAOConverterRule<ImportDTO, SprinklerDAO> {

    @Override
    public SprinklerDAO convert(ImportDTO dto) {
        SprinklerDAO s = new SprinklerDAO();
        s.setShippingDate(dto.getShippingDate());
        s.setPurchaseDate(dto.getPurchaseDate());
        s.setContractNumber(dto.getContractNumber());
        s.setHeadModel(dto.getHeadModel());
        s.setHeadSerial(dto.getHeadSerial());
        s.setWarehouseDate(dto.getWarehouseDate());
        s.setVoltage(dto.getVoltage());
        s.setJetsout(dto.getJetsout());
        s.setStatus(HeadStatus.IN_STOCK);
        s.setVersion(0);
        return s;
    }

    @Override
    public String getSceneType() {
        return "IMPORT";
    }
}
