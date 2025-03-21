package com.haiyin.sprinkler.backend.fileprocessing.service.impl;

import com.haiyin.sprinkler.backend.fileprocessing.dao.SprinklerDAO;
import com.haiyin.sprinkler.backend.fileprocessing.dto.AllocateDTO;
import com.haiyin.sprinkler.backend.fileprocessing.dto.ImportDTO;
import com.haiyin.sprinkler.backend.fileprocessing.dto.MaintainDTO;
import com.haiyin.sprinkler.backend.fileprocessing.service.FileProcessorService;
import com.haiyin.sprinkler.backend.fileprocessing.service.SprinklerService;
import com.haiyin.sprinkler.backend.fileprocessing.service.StateMachine;
import com.haiyin.sprinkler.backend.fileprocessing.service.converter.DAOConverter;
import com.haiyin.sprinkler.backend.fileprocessing.service.parser.ExcelParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
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
            //处理导入逻辑
            if ("IMPORT".equalsIgnoreCase(sceneType)) {
                try (InputStream excelStream = files[0].getInputStream();
                     InputStream txtStream = files[1].getInputStream()
                ){
                    List<ImportDTO> dtos = excelParser.parseByStream(excelStream, sceneType);
                    Function f = (dto)->(SprinklerDAO)daoConverter.parseByStream(sceneType).convert(dto);
                    List<SprinklerDAO> daos = dtos.stream().map(f).toList();
                    sprinklerService.batchSave(daos);
//                    List<Long> daoIds = new ArrayList<>();
//                    daos.forEach(dao -> {
//                        daoIds.add(dao.getId());
//                    });
//                    stateMachine.batchRequestTransition(daoIds);
//                    throw new RuntimeException();
                    return dtos;
//                    return daos;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                // 处理领用逻辑
            } else if ("Allocate".equalsIgnoreCase(sceneType)) {
                try (InputStream excelStream = files[0].getInputStream()){
                    // 1. 使用Try-with-resources自动关闭流
                    List<AllocateDTO> dtos = excelParser.parseByStream(excelStream, sceneType);
//                    Function f = (dto)->(SprinklerDAO)daoConverter.parseByStream(sceneType).convert(dto);
//                    List<SprinklerDAO> daos = dtos.stream().map(f).toList();
                    // 2. 并行流加速转换
                    List<SprinklerDAO> daos = dtos.parallelStream()
                            .map(dto -> (SprinklerDAO) daoConverter.parseByStream(sceneType).convert(dto))
                            .toList();
                    // 3. 批量写入并获取带ID的实体
//                    List<Integer> persistedDaos = sprinklerService.batchUpsert(daos);
                    List<Long> daoIds = sprinklerService.batchUpsert(daos);
//                    // 4. 直接映射ID集合
//                    List<Long> daoIds = persistedDaos.stream()
//                            .map(SprinklerDAO::getId)
//                            .filter(Objects::nonNull)
//                            .toList();
                    // 5. 状态机转换
                    stateMachine.batchRequestTransition(daoIds);
//                    throw new RuntimeException();
                    return dtos;
                    // 处理维护逻辑
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            } else if ("Maintain".equalsIgnoreCase(sceneType)) {
                try (InputStream excelStream = files[0].getInputStream()
                ){

                    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                    byte[] data = new byte[1024];
                    int nRead;
                    while ((nRead = excelStream.read(data, 0, data.length)) != -1) {
                        buffer.write(data, 0, nRead);
                    }

                    // 将ByteArrayOutputStream转换为ByteArrayInputStream，以便多次读取
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer.toByteArray());

                    // 1. 使用Try-with-resources自动关闭流
                    List<AllocateDTO> allocateDTOs = excelParser.parseByStream(byteArrayInputStream, "allocate2");
//                    Function f = (dto)->(SprinklerDAO)daoConverter.parseByStream(sceneType).convert(dto);
//                    List<SprinklerDAO> daos = dtos.stream().map(f).toList();
                    // 2. 并行流加速转换
                    List<SprinklerDAO> daos = allocateDTOs.parallelStream()
                            .map(dto -> (SprinklerDAO) daoConverter.parseByStream("allocate2").convert(dto))
                            .toList();
                    // 3. 批量写入并获取带ID的实体
//                    List<Integer> persistedDaos = sprinklerService.batchUpsert(daos);
//                    List<Long> daoIds = sprinklerService.batchUpsert(daos);
                    sprinklerService.batchSave(daos);
                    // 4. 直接映射ID集合
                    List<Long> daoIds = daos.stream()
                            .map(SprinklerDAO::getId)
                            .filter(Objects::nonNull)
                            .toList();
                    // 5. 状态机转换
                    stateMachine.batchRequestTransition(daoIds);
//                    throw new RuntimeException();
                    byteArrayInputStream.reset();

                    List<MaintainDTO> maintainDTOs = excelParser.parseByStream(byteArrayInputStream, "maintain");
                    // 2. 并行流加速转换
                    daos = maintainDTOs.parallelStream()
                            .map(dto -> (SprinklerDAO) daoConverter.parseByStream(sceneType).convert(dto))
                            .toList();
                    // 3. 批量写入并获取带ID的实体
                    daoIds = sprinklerService.batchUpsert(daos);
                    // 5. 状态机转换
                    stateMachine.batchRequestTransition(daoIds);
//                    throw new RuntimeException();
                    return maintainDTOs;
                    // 处理维护逻辑
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        return List.of();
    }

}
