package com.haiyin.service.impl;

import com.haiyin.dto.SprinklerImportDTO;
import com.haiyin.enums.SprinklerStatus;
import com.haiyin.enums.SprinklerType;
import com.haiyin.mapper.HeadInventoryMapper;
import com.haiyin.pojo.HeadInventory;
import com.haiyin.service.ExcelService;
import com.haiyin.utils.LocalDateParseUtil;
import com.haiyin.utils.TransferCellTypeUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
//@Transactional
public class ExcelServiceImpl implements ExcelService {

    @Autowired
    private HeadInventoryMapper headInventoryMapper;
    @Autowired
    private TransactionTemplate transactionTemplate;

    @Transactional
    @Override
    public void importExcel(MultipartFile file) {
        List<SprinklerImportDTO> dtos = readImportExcel(file);
        // 2. 批量插入初始数据（状态为 IN_STOCK）
        List<HeadInventory> initialData = dtos.stream()
                .map(dto -> {
                    HeadInventory s = new HeadInventory();
                    s.setShippingDate(dto.getShippingDate());
                    s.setPurchaseDate(dto.getPurchaseDate());
                    s.setContractNumber(dto.getContractNumber());
                    s.setHeadModel(dto.getHeadModel());
                    s.setHeadSerial(dto.getHeadSerial());
                    s.setWarehouseDate(dto.getWarehouseDate());
                    s.setVoltage(dto.getVoltage());
                    s.setJetsout(dto.getJetsout());
                    s.setStatus(SprinklerStatus.IN_STOCK);
                    s.setVersion(0);
                    s.setType(dto.getType());
                    return s;
                })
                .collect(Collectors.toList());
//        sprinklerService.batchImportSprinklers(initialData);
        transactionTemplate.execute(status -> {
            try {
                headInventoryMapper.batchInsert(initialData);
//                status.setRollbackOnly();
            }catch (Exception e){
                e.printStackTrace();
                status.setRollbackOnly();
            }
            return null;
        });
//        headInventoryMapper.batchInsert(initialData);
//        throw new RuntimeException("运行异常，测试transactional");
    }

    public List<SprinklerImportDTO> readImportExcel(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("文件为空，请上传有效文件！");
        }
        List<SprinklerImportDTO> list = new ArrayList<>();
        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)
        ) {
            XSSFSheet sheet = (XSSFSheet) workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // 跳过标题行

                SprinklerImportDTO dto = new SprinklerImportDTO();
                if (row.getCell(0).getCellType() == CellType.NUMERIC) {
                    row.getCell(0).setCellType(CellType.STRING);
                }
                String purchaseContract = row.getCell(0).getStringCellValue();
                Matcher matcher = Pattern.compile("\\d+").matcher(purchaseContract);
//\s*（\s*\d+\s*）
                int matcher_start = 0;
                if (matcher.find(matcher_start)) {
                    LocalDate purchaseDate = LocalDateParseUtil.parseDate(matcher.group(0));

                    // 提取数字
                    dto.setPurchaseDate(purchaseDate);
                    matcher_start = matcher.end();
                }
                if (matcher.find(matcher_start)) {
                    String contractNumber = matcher.group(0);
                    dto.setContractNumber(contractNumber);
                }
                dto.setHeadModel(row.getCell(1).getStringCellValue());
                String headSerial = row.getCell(2).getStringCellValue();
                dto.setHeadSerial(headSerial);
                dto.setShippingDate(LocalDateParseUtil.parseDate(row.getCell(3).getStringCellValue()));
                dto.setWarehouseDate(LocalDateParseUtil.parseDate(row.getCell(4).getStringCellValue()));
                dto.setVoltage((float) row.getCell(10).getNumericCellValue());
                dto.setJetsout((int) row.getCell(11).getNumericCellValue());
//                dto.setOwner(row.getCell(6).getStringCellValue());
//                dto.setMachine(row.getCell(7).getStringCellValue());
//                String color_position = row.getCell(8).getStringCellValue();
//                matcher = Pattern.compile("(\\d+)").matcher(color_position);
//                if (matcher.find()) {
//                    String color = color_position.substring(0, matcher.start());
//                    String position = matcher.group();
//                    // 提取数字
//                    dto.setColor(color);
//                    dto.setPosition(position);
//                }
                dto.setType(SprinklerType.NEW);

                if (headSerial != "") {
                    list.add(dto);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("文件处理失败：" + e.getMessage(), e);
        } catch (DateTimeParseException e) {
            throw new RuntimeException("日期格式解析失败，请检查数据：" + e.getMessage(), e);
        }
        return list;
    }

    @Override
    public String exportToExcel(String purchaseDate, String contractNumber, String headModel, String headSerial, String warehouseDate, String usageDate, String user, String usagePurpose, String installationSite, String headHistory) {
//        List<HeadInventory> headInventories = headInventoryMapper.list(purchaseDate, contractNumber, headModel, headSerial, warehouseDate, usageDate, user, usagePurpose, installationSite, headHistory);
        List<HeadInventory> headInventories = new ArrayList<>();
        // 创建工作簿
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("喷头");

        // 创建表头
        String[] columns = {"购入日期", "合同编号", "喷头型号", "喷头序列号", "发货日期", "入仓日期", "被领用日期", "领用人", "领用用途", "颜色", "位置", "历史", "电压", "jetsout"};
        Row headerRow = sheet.createRow(0);

        // 创建样式
        CellStyle headerCellStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);

        // 创建支持中文的字体
        CellStyle chineseCellStyle = workbook.createCellStyle();
        Font chineseFont = workbook.createFont();
        chineseFont.setFontName("微软雅黑"); // 或 "SimSun"（宋体）
        chineseCellStyle.setFont(chineseFont);
        headerCellStyle.setFont(headerFont);

        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(chineseCellStyle);
        }
        // 创建日期单元格样式
        CellStyle dateCellStyle = workbook.createCellStyle();
        CreationHelper creationHelper = workbook.getCreationHelper();
        // 设置日期格式，例如 "yyyy-mm-dd"
        dateCellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-mm-dd"));

        // 创建单元格样式（建议在循环外复用样式对象）
        CellStyle numberStyle = workbook.createCellStyle();
        DataFormat format = workbook.createDataFormat();
        // 设置数据格式为保留三位小数（Excel格式模式）
        numberStyle.setDataFormat(format.getFormat("0.000"));
        // 填充数据
        int rowNum = 1;
        for (HeadInventory headInventory : headInventories) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(headInventory.getPurchaseDate());
            row.getCell(0).setCellStyle(dateCellStyle);
            row.createCell(1).setCellValue(headInventory.getContractNumber());
            row.createCell(2).setCellValue(headInventory.getHeadModel());
            row.createCell(3).setCellValue(headInventory.getHeadSerial());
            row.createCell(4).setCellValue(headInventory.getShippingDate());
            row.getCell(4).setCellStyle(dateCellStyle);
            row.createCell(5).setCellValue(headInventory.getWarehouseDate());
            row.getCell(5).setCellStyle(dateCellStyle);
            row.createCell(6).setCellValue(headInventory.getUsageDate());
            row.getCell(6).setCellStyle(dateCellStyle);
            row.createCell(7).setCellValue(headInventory.getUser());
            row.createCell(8).setCellValue(headInventory.getUsagePurpose());
            row.createCell(9).setCellValue(headInventory.getColor());
            row.createCell(10).setCellValue(headInventory.getPosition());
            row.createCell(11).setCellValue(headInventory.getHeadHistory());
            row.createCell(12).setCellValue(headInventory.getVoltage());
            row.getCell(12).setCellStyle(numberStyle);
            row.createCell(13).setCellValue(headInventory.getJetsout());

        }

        // 自动调整列宽
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // 创建临时文件
        File tempFile = null;
        try {
            tempFile = File.createTempFile("headInventories_" + System.currentTimeMillis() + "_", ".xlsx");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 不设置 deleteOnExit，因为我们将使用计划任务或异步删除

        // 写入到文件
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            workbook.write(fos);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            // 关闭工作簿
            try {
                workbook.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // 异步删除临时文件（可选）
        File finalTempFile = tempFile;
        CompletableFuture.runAsync(() -> {
            try {
                // 确保文件在一段时间后删除，例如 10 分钟
                Thread.sleep(10 * 60 * 1000); // 暂停10分钟
                boolean deleted = finalTempFile.delete();
                if (deleted) {
                    System.out.println("已删除临时文件: " + finalTempFile.getAbsolutePath());
                } else {
                    System.out.println("无法删除临时文件: " + finalTempFile.getAbsolutePath());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // 返回临时文件的绝对路径
        return tempFile.getAbsolutePath();
    }

    /**
     * 解析一个 Sheet 的所有行并返回解析结果
     */
    private List<HeadInventory> processSheet(Sheet sheet, int sheetIndex) {
        List<HeadInventory> headInventories = new ArrayList<>();
        for (int i = 2; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            try {
                HeadInventory headInventory = parseRow(row, sheetIndex);
                headInventories.add(headInventory);
            } catch (Exception e) {
                System.err.println("Error parsing row " + row.getRowNum() + " in sheet " + sheetIndex + ": " + e.getMessage());
            }
        }
        return headInventories;
    }

    /**
     * 解析一行数据
     */
    private HeadInventory parseRow(Row row, int sheetIndex) {
        HeadInventory headInventory = new HeadInventory();

        String purchaseDateAndContract = TransferCellTypeUtil.getCellValueAsString(row.getCell(0));
        if (StringUtils.hasText(purchaseDateAndContract)) {
            String[] parts = purchaseDateAndContract.split("[\\(（]");
            LocalDate purchaseDate = LocalDateParseUtil.parseDate(parts[0].trim());
            String contractNumber = "";
            if (parts.length > 1) {
                String contractPart = parts[1].trim();
                int endIndex = contractPart.indexOf(")") != -1 ? contractPart.indexOf(")") : contractPart.indexOf("）");
                contractNumber = (endIndex != -1) ? contractPart.substring(0, endIndex).trim() : contractPart;
            }
            headInventory.setPurchaseDate(purchaseDate);
            headInventory.setContractNumber(contractNumber);
        }

        headInventory.setHeadModel(TransferCellTypeUtil.getCellValueAsString(row.getCell(1)).trim());
        headInventory.setHeadSerial(TransferCellTypeUtil.getCellValueAsString(row.getCell(2)).trim());
        headInventory.setWarehouseDate(LocalDateParseUtil.parseDate(TransferCellTypeUtil.getCellValueAsString(row.getCell(3)).trim()));
        headInventory.setUsageDate(LocalDateParseUtil.parseDate(TransferCellTypeUtil.getCellValueAsString(row.getCell(4)).trim()));
        headInventory.setUser(TransferCellTypeUtil.getCellValueAsString(row.getCell(5)).trim());
        headInventory.setUsagePurpose(TransferCellTypeUtil.getCellValueAsString(row.getCell(6)).trim());
        headInventory.setColor(TransferCellTypeUtil.getCellValueAsString(row.getCell(7)).trim());//有问题的
        headInventory.setPosition(TransferCellTypeUtil.getCellValueAsString(row.getCell(7)).trim());//有问题的
        headInventory.setHeadHistory(TransferCellTypeUtil.getCellValueAsString(row.getCell(8)).trim());

        return headInventory;
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


    /**
     * 分治处理插入失败的批次，找到具体冲突的记录
     */
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

}
