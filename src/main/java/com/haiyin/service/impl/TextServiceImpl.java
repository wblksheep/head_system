package com.haiyin.service.impl;

import com.haiyin.mapper.HeadInventoryMapper;
import com.haiyin.pojo.HeadInventory;
import com.haiyin.enums.SprinklerStatus;
import com.haiyin.service.TextService;
import com.haiyin.utils.LocalDateParseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TextServiceImpl implements TextService {

    @Autowired
    private HeadInventoryMapper headInventoryMapper;

    @Override
    public void importText(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("文件为空，请上传有效文件！");
        }
//        try (InputStream inputStream = file.getInputStream()) {
//            String str = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
//            System.out.println(str);
//
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        List<String> lines = FileUtils.readLines(file, "UTF-8");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String line = reader.readLine();
            List<HeadInventory> headInventories = new ArrayList<>();
            while ((line = reader.readLine()) != null) {

                HeadInventory headInventory = parseLine(line);
                headInventories.add(headInventory);
            }
            // 批量插入所有数据
            batchInsert(headInventories);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 批量插入数据，自动处理重复数据
     */
    private void batchInsert(List<HeadInventory> headInventories) {
        if (headInventories == null || headInventories.isEmpty()) {
            return;
        }

        final int batchSize = 500; // 每批次大小
        List<HeadInventory> failedRecords = new ArrayList<>();

        for (int i = 0; i < headInventories.size(); i += batchSize) {
            int end = Math.min(i + batchSize, headInventories.size());
            List<HeadInventory> batch = headInventories.subList(i, end);

            try {
                headInventoryMapper.insertBatch(batch);
            } catch (DuplicateKeyException e) {
                System.err.println("Batch insert failed due to duplicate key: " + e.getMessage());
                // 分治策略处理失败批次
                handleFailedBatch(batch, failedRecords);
            }
        }

        if (!failedRecords.isEmpty()) {
            System.err.println("Failed to insert records: " + failedRecords.size());
            // 可选择将失败数据记录到日志或写入文件
        }
    }

    private void handleFailedBatch(List<HeadInventory> batch, List<HeadInventory> failedRecords) {
        if (batch.size() == 1) {
            // 如果批次只有一条数据，直接尝试单条插入
            try {
                headInventoryMapper.insert(batch.get(0));
            } catch (DuplicateKeyException e) {
                System.err.println("Skipping duplicate entry: " + batch.get(0).getHeadSerial());
                failedRecords.add(batch.get(0));
            }
            return;
        }

        // 将批次分成两部分
        int mid = batch.size() / 2;
        List<HeadInventory> firstHalf = batch.subList(0, mid);
        List<HeadInventory> secondHalf = batch.subList(mid, batch.size());

        try {
            headInventoryMapper.insertBatch(firstHalf);
        } catch (DuplicateKeyException e) {
            System.err.println("First half failed: " + e.getMessage());
            handleFailedBatch(firstHalf, failedRecords);
        }

        try {
            headInventoryMapper.insertBatch(secondHalf);
        } catch (DuplicateKeyException e) {
            System.err.println("Second half failed: " + e.getMessage());
            handleFailedBatch(secondHalf, failedRecords);
        }
    }


    private HeadInventory parseLine(String line) {
        String[] items = line.split("\\|");
        HeadInventory headInventory = new HeadInventory();
        LocalDate shippingDate = LocalDateParseUtil.parseDate(items[0]);
        LocalDate purchaseDate = LocalDateParseUtil.parseDate(items[2]);
        String contractNumber = items[4];
        String headModel = items[6];
        headModel = headModel.equals("2100325898") ? "FG,SAMBA PH G3L HF UV SAMBA,RJC Gen2" :
                (headModel.equals("2100318076") ? "FG,SAMBA PH G3L UV SAMBA 1.1" :
                        (headModel.equals("2100315216") ? "FG,SAMBA PH G3L SAMBA 1.1" :
                                "未知型号")); // 默认值
        String headSerial = items[7];
        LocalDate warehouseDate = LocalDate.now();
        Float voltage = Float.parseFloat(items[11]);
        Integer jetsout = Integer.parseInt(items[13]);
        headInventory.setShippingDate(shippingDate);
        headInventory.setPurchaseDate(purchaseDate);
        headInventory.setContractNumber(contractNumber);
        headInventory.setHeadModel(headModel);
        headInventory.setHeadSerial(headSerial);
        headInventory.setWarehouseDate(warehouseDate);
        headInventory.setVoltage(voltage);
        headInventory.setJetsout(jetsout);
        headInventory.setStatus(SprinklerStatus.IN_STOCK);
        headInventory.setVersion(0);
        return headInventory;
    }
}
