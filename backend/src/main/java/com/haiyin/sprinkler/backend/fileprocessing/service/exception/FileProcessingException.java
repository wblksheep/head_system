package com.haiyin.sprinkler.backend.fileprocessing.service.exception;

public class FileProcessingException extends RuntimeException {
    private String sceneType;
    private String fileName;

    public FileProcessingException(String message, String sceneType, String fileName) {
        super(message);
        this.sceneType = sceneType;
        this.fileName = fileName;
    }
}
