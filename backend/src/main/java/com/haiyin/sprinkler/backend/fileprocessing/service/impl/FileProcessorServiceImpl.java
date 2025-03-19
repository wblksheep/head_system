package com.haiyin.sprinkler.backend.fileprocessing.service.impl;

import com.haiyin.sprinkler.backend.domain.model.BatchContext;
import com.haiyin.sprinkler.backend.fileprocessing.dao.SprinklerDAO;
import com.haiyin.sprinkler.backend.fileprocessing.dto.AllocateDTO;
import com.haiyin.sprinkler.backend.fileprocessing.dto.ImportDTO;
import com.haiyin.sprinkler.backend.fileprocessing.dto.MaintainDTO;
import com.haiyin.sprinkler.backend.fileprocessing.service.FileProcessorService;
import com.haiyin.sprinkler.backend.fileprocessing.service.converter.DAOConverter;
import com.haiyin.sprinkler.backend.fileprocessing.service.parser.ExcelParser;
import com.haiyin.sprinkler.backend.infrastructure.resource.manager.SemaphoreResourceManager;
import com.haiyin.sprinkler.backend.infrastructure.resource.model.Session;
import com.haiyin.sprinkler.backend.infrastructure.resource.spi.ResourceManager;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.haiyin.sprinkler.backend.domain.factory.BatchContextFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

@Service
public class FileProcessorServiceImpl implements FileProcessorService {

    @Autowired
    private ExcelParser excelParser;

    @Autowired
    private DAOConverter daoConverter;

//    @Autowired

    @Override
    public List<?> processUpload(MultipartFile[] files, String sceneType) {
            if ("IMPORT".equals(sceneType)) {
                try {
                    InputStream excelstream = files[0].getInputStream();
                    List<ImportDTO> dtos = excelParser.parseByStream(excelstream, sceneType);
                    Function f = (dto)->(SprinklerDAO)daoConverter.parseByStream(sceneType).convert(dto);
                    List<SprinklerDAO> daos = dtos.stream().map(f).toList();
                    return dtos;
//                    return daos;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                // 处理领用逻辑
            } else if ("MAINTAIN".equals(sceneType)) {
//                List<MaintainDTO> dtos = excelParser.parseByStream(stream, sceneType);
                // 处理维护逻辑
            }
            return List.of();
    }

}
