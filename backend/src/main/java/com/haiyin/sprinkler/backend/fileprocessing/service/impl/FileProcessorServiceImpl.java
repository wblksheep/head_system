package com.haiyin.sprinkler.backend.fileprocessing.service.impl;

import com.google.common.base.Preconditions;
import com.haiyin.sprinkler.backend.domain.model.SceneType;
import com.haiyin.sprinkler.backend.fileprocessing.dao.SprinklerDAO;
import com.haiyin.sprinkler.backend.fileprocessing.dto.AllocateDTO;
import com.haiyin.sprinkler.backend.fileprocessing.dto.ImportDTO;
import com.haiyin.sprinkler.backend.fileprocessing.dto.MaintainDTO;
import com.haiyin.sprinkler.backend.fileprocessing.service.FileProcessorService;
import com.haiyin.sprinkler.backend.fileprocessing.service.SprinklerService;
import com.haiyin.sprinkler.backend.fileprocessing.service.StateMachine;
import com.haiyin.sprinkler.backend.fileprocessing.service.converter.DAOConverter;
import com.haiyin.sprinkler.backend.fileprocessing.service.exception.FileProcessingException;
import com.haiyin.sprinkler.backend.fileprocessing.service.parser.ExcelParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

@Service
public class FileProcessorServiceImpl implements FileProcessorService {

    @Autowired
    private ExcelParser excelParser;

    @Autowired
    private DAOConverter daoConverter;


    @Autowired
    private StateMachine stateMachine;

    @Autowired
    private SprinklerService sprinklerService;

//    @Transactional
    @Override
    public List<?> processUpload(MultipartFile[] files, String sceneType) {
        try {
            SceneType type = SceneType.valueOf(sceneType.toUpperCase());
            switch (type) {
                case IMPORT -> {
                    validateFileCount(files, 2, type.name());
                    return handleImport(files);
                }
                case ALLOCATE -> {
                    validateFileCount(files, 1, type.name());
                    return handleAllocate(files[0]);
                }
                case MAINTAIN -> {
                    validateFileCount(files, 1,type.name());
                    return handleMaintain(files[0]);
                }
                default -> throw new IllegalArgumentException("Unsupported scene type");
            }
        } catch (IOException e) {
            throw new FileProcessingException("IO error", sceneType, Arrays.toString(files));
        }
    }

    private List<?> handleMaintain(MultipartFile file) throws IOException {
        String sceneType = SceneType.MAINTAIN.name();
        try (InputStream excelStream = file.getInputStream()) {

            List<Long> ids = processBatch(file, "allocate2", AllocateDTO.class);
            stateMachine.batchRequestTransition(ids);
            List<MaintainDTO> maintainDTOs = excelParser.parseByStream(excelStream, "maintain");
            List<Long> daoIds = processBatch(file, sceneType, MaintainDTO.class);
            stateMachine.batchRequestTransition(daoIds);
//                    throw new RuntimeException();
            return maintainDTOs;
        }
    }

    private List<?> handleAllocate(MultipartFile file) throws IOException {
        String sceneType = SceneType.ALLOCATE.name();
        try (InputStream excelStream = file.getInputStream()) {
            List<AllocateDTO> dtos = excelParser.parseByStream(excelStream, sceneType);
            List<Long> ids = processBatch(file, sceneType, AllocateDTO.class);
            stateMachine.batchRequestTransition(ids);
            return dtos;
        }
    }

    private List<?> handleImport(MultipartFile[] files) throws IOException {
        String sceneType = SceneType.IMPORT.name();
        try (InputStream excelStream = files[0].getInputStream();
             InputStream txtStream = files[1].getInputStream()) {
            List<ImportDTO> dtos = excelParser.parseByStream(excelStream, sceneType);
            Function f = (dto)->(SprinklerDAO)daoConverter.parseByStream(sceneType).convert(dto);
            List<SprinklerDAO> daos = dtos.stream().map(f).toList();
            sprinklerService.batchSave(daos);
            return dtos;
        }
    }

    private void validateFileCount(MultipartFile[] files, int requiredFiles, String sceneType) {
        Preconditions.checkArgument(files.length >= requiredFiles,
                "Missing required files for scene: %s", sceneType);
    }

    private <T> List<Long> processBatch(MultipartFile file, String sceneType, Class<T> dtoType) throws IOException {
        try (InputStream stream = file.getInputStream()) {
            List<T> dtos = excelParser.parseByStream(stream, sceneType);
            return sprinklerService.batchUpsert(dtos.parallelStream()
                            .map(dto -> (SprinklerDAO) daoConverter.parseByStream(sceneType).convert(dto))
                            .toList());
        }
    }


}
