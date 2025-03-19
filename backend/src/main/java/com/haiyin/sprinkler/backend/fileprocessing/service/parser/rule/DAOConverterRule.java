package com.haiyin.sprinkler.backend.fileprocessing.service.parser.rule;

import org.apache.poi.ss.formula.functions.T;

import java.util.List;

public interface DAOConverterRule<T, R> {

    R convert(T dto);

    String getSceneType();
}
