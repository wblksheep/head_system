package com.haiyin.sprinkler.backend.fileprocessing.service.impl;

import com.google.common.base.Preconditions;
import com.haiyin.sprinkler.backend.domain.model.SceneType;
import com.haiyin.sprinkler.backend.fileprocessing.dao.SprinklerDAO;
import com.haiyin.sprinkler.backend.fileprocessing.dto.AllocateDTO;
import com.haiyin.sprinkler.backend.fileprocessing.dto.DamageDTO;
import com.haiyin.sprinkler.backend.fileprocessing.dto.ImportDTO;
import com.haiyin.sprinkler.backend.fileprocessing.dto.MaintainDTO;
import com.haiyin.sprinkler.backend.fileprocessing.service.FileProcessorService;
import com.haiyin.sprinkler.backend.fileprocessing.service.StateMachine;
import com.haiyin.sprinkler.backend.fileprocessing.service.converter.DAOConverter;
import com.haiyin.sprinkler.backend.fileprocessing.service.exception.FileProcessingException;
import com.haiyin.sprinkler.backend.fileprocessing.service.parser.ExcelParser;
import com.haiyin.sprinkler.backend.fileprocessing.service.saver.SprinklerSaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class FileProcessorServiceImpl implements FileProcessorService {

    @Autowired
    private ExcelParser excelParser;

    @Autowired
    private DAOConverter daoConverter;

    @Autowired
    private StateMachine stateMachine;

    @Autowired
    private SprinklerSaver sprinklerSaver;

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
                case DAMAGE ->{
                    validateFileCount(files, 1,type.name());
                    return handleDamage(files[0]);
                }
                default -> throw new IllegalArgumentException("Unsupported scene type");
            }
        } catch (IOException e) {
            throw new FileProcessingException("IO error", sceneType, Arrays.toString(files));
        }
    }

    private List<?> handleDamage(MultipartFile file) throws IOException {
        String sceneType = SceneType.DAMAGE.name();
        List<DamageDTO> dtos = excelParser.parseByStream(file, sceneType);
        // 2. 并行流加速转换
        List<SprinklerDAO> daos = dtos.parallelStream()
                .map(dto -> (SprinklerDAO) daoConverter.parseByStream(sceneType).convert(dto))
                .toList();
        List<Long> daoIds = sprinklerSaver.batchUpsert(daos, sceneType);
        stateMachine.batchRequestTransition(daoIds, 3);
        return dtos;
    }

    private List<?> handleMaintain(MultipartFile file) throws IOException {
        String sceneType = SceneType.MAINTAIN.name();
        List<AllocateDTO> allocateDTOS = excelParser.parseByStream(file, "allocate2");
        // 2. 并行流加速转换
        List<SprinklerDAO> allocateDAOS = allocateDTOS.parallelStream()
                .map(dto -> (SprinklerDAO) daoConverter.parseByStream("allocate2").convert(dto))
                .toList();
        List<Long> daoIds = sprinklerSaver.batchUpsert(allocateDAOS, "allocate");
        stateMachine.batchRequestTransition(daoIds, 1);
        List<MaintainDTO> maintainDTOS = excelParser.parseByStream(file, sceneType);
        // 2. 并行流加速转换
        List<SprinklerDAO> maintainDAOS = maintainDTOS.parallelStream()
                .map(dto -> (SprinklerDAO) daoConverter.parseByStream(sceneType).convert(dto))
                .toList();
        List<Long> maintainDAOIds = sprinklerSaver.batchUpsert(maintainDAOS, sceneType);
        stateMachine.batchRequestTransition(maintainDAOIds, 2);
        return maintainDTOS;

    }

    private List<?> handleAllocate(MultipartFile file) throws IOException {
        String sceneType = SceneType.ALLOCATE.name();
        List<AllocateDTO> dtos = excelParser.parseByStream(file, sceneType);
        // 2. 并行流加速转换
        List<SprinklerDAO> daos = dtos.parallelStream()
                .map(dto -> (SprinklerDAO) daoConverter.parseByStream(sceneType).convert(dto))
                .toList();
        List<Long> ids = sprinklerSaver.batchUpsert(daos, sceneType);
        stateMachine.batchRequestTransition(ids, 1);
        return dtos;
    }

    private List<?> handleImport(MultipartFile[] files) throws IOException {
        String sceneType = SceneType.IMPORT.name();
        List<ImportDTO> dtos = excelParser.parseByStream(files[0], sceneType);
        // 2. 并行流加速转换
        List<SprinklerDAO> daos = dtos.parallelStream()
                .map(dto -> (SprinklerDAO) daoConverter.parseByStream(sceneType).convert(dto))
                .toList();
        List<Long> ids = sprinklerSaver.batchUpsert(daos, sceneType);
        return dtos;
    }

    private void validateFileCount(MultipartFile[] files, int requiredFiles, String sceneType) {
        Preconditions.checkArgument(files.length >= requiredFiles,
                "Missing required files for scene: %s", sceneType);
    }

}
