package com.haiyin.sprinkler.backend.fileprocessing.service.converter.impl;

import com.haiyin.sprinkler.backend.fileprocessing.dao.HeadStatus;
import com.haiyin.sprinkler.backend.fileprocessing.dao.SprinklerDAO;
import com.haiyin.sprinkler.backend.fileprocessing.dto.ImportDTO;
import com.haiyin.sprinkler.backend.fileprocessing.service.parser.rule.DAOConverterRule;
import com.haiyin.sprinkler.backend.fileprocessing.service.parser.rule.ExcelParseRule;
import com.haiyin.sprinkler.backend.utils.LocalDateParseUtil;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
