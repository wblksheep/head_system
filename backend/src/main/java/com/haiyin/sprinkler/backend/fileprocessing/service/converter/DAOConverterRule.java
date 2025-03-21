package com.haiyin.sprinkler.backend.fileprocessing.service.converter;

public interface DAOConverterRule<T, R> {

    R convert(T dto);

    String getSceneType();
}
