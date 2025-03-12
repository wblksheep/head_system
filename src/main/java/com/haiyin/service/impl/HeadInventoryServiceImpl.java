package com.haiyin.service.impl;

import com.github.pagehelper.PageHelper;
import com.haiyin.dto.SprinklerAllocationDTO;
import com.haiyin.dto.SprinklerMaintainDTO;
import com.haiyin.enums.SprinklerStatus;
import com.haiyin.enums.SprinklerType;
import com.haiyin.exception.ConcurrentUpdateException;
import com.haiyin.exception.SprinklerNotFoundException;
import com.haiyin.mapper.HeadInventoryMapper;
import com.haiyin.pojo.HeadInventory;
import com.haiyin.pojo.PageBean;
import com.haiyin.service.HeadInventoryService;
import com.haiyin.utils.ThreadLocalUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class HeadInventoryServiceImpl implements HeadInventoryService {

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private HeadInventoryMapper headInventoryMapper;

    @Override
    public PageBean<HeadInventory> list(Integer pageNum, Integer pageSize) {
        //创建pageBean对象
        PageBean<HeadInventory> pb = new PageBean<>();

        //开启分页查询 PageHelper
        PageHelper.startPage(pageNum, pageSize);

        //调用mapper
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        List<HeadInventory> as = headInventoryMapper.list();
        com.github.pagehelper.Page<HeadInventory> p = (com.github.pagehelper.Page<HeadInventory>) as;

        //把数据填充到PageBean对象中
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;
    }

    @Override
    public PageBean<SprinklerAllocationDTO> getDiff(Integer pageNum, Integer pageSize, MultipartFile excelFile, MultipartFile txtFile) {
        Info info = getDiffInfo(pageNum, pageSize, excelFile, txtFile);
        PageBean<SprinklerAllocationDTO> pb = makePageBeanObj(pageNum, pageSize, info.fail);
        return pb;
    }

    @Override
    public PageBean<SprinklerMaintainDTO> batchMaintain(Integer pageNum, Integer pageSize, MultipartFile excelFile) {
        return null;
    }

//    @Override
//    public PageBean<SprinklerMaintainDTO> batchMaintain(Integer pageNum, Integer pageSize, MultipartFile excelFile) {
//        try {
//
//            Info info = getMaintainInfo(pageNum, pageSize, excelFile);
//            transactionTemplate.execute(status -> {
//                try {
//                    // 3. 执行批量分配
//                    batchAllocateSprinklers(info.suc);
////                    status.setRollbackOnly();
//                }catch (Exception e) {
//                    status.setRollbackOnly();
//                    throw new RuntimeException(e);
//                }
//                return 0;
//            });
//
//            PageBean<SprinklerAllocationDTO> pb = makePageBeanObj(pageNum, pageSize, info.fail);
//            return pb;
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

//    private Info getMaintainInfo(Integer pageNum, Integer pageSize, MultipartFile excelFile) {
//
//        try {
////            List<TxtRecord> txtRecords = parseTxtFile(txtFile);
//            List<ExcelRecord> excelRecords = parseExcelFile(excelFile);
//            // 3. 匹配记录
////            Info info = matchAndPrintResults(txtRecords, excelRecords);
//
////            return info;
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

    private Info getDiffInfo(Integer pageNum, Integer pageSize, MultipartFile excelFile, MultipartFile txtFile) {

        try {
            List<TxtRecord> txtRecords = parseTxtFile(txtFile);
            List<ExcelRecord> excelRecords = parseExcelFile(excelFile);
            // 3. 匹配记录
            Info info = matchAndPrintResults(txtRecords, excelRecords);

            return info;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //flags
    @Autowired
    private AllocationProcessor allocationProcessor;

    @Transactional
    @Override
    public PageBean<SprinklerAllocationDTO> batchAllocate(Integer pageNum, Integer pageSize, MultipartFile excelFile, MultipartFile txtFile) {
        try {
            allocationProcessor.process(pageNum, pageSize, excelFile, txtFile);
            Info info = getDiffInfo(pageNum, pageSize, excelFile, txtFile);
            transactionTemplate.execute(status -> {
                try {
                    // 3. 执行批量分配
                    batchAllocateSprinklers(info.suc);
//                    status.setRollbackOnly();
                }catch (Exception e) {
                    status.setRollbackOnly();
                    throw new RuntimeException(e);
                }
                return 0;
            });

            PageBean<SprinklerAllocationDTO> pb = makePageBeanObj(pageNum, pageSize, info.fail);
            return pb;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private PageBean<SprinklerAllocationDTO> makePageBeanObj(Integer pageNum, Integer pageSize, List<SprinklerAllocationDTO> dtos) {
        PageBean<SprinklerAllocationDTO> pb = new PageBean<>();
        // 创建分页请求
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);

        // 截取当前页数据
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), dtos.size());
        List<SprinklerAllocationDTO> pageContent = dtos.subList(start, end);

        // 创建分页对象
        Page<SprinklerAllocationDTO> p = new PageImpl<>(pageContent, pageable, dtos.size());
        // 填充到 PageBean 对象中
        pb.setTotal(p.getTotalElements());
        pb.setItems(p.getContent());
        return pb;
    }

    @Transactional
    public void allocateSprinkler(SprinklerAllocationDTO sprinklerDTO) {
        // 获取当前喷头信息
        HeadInventory entity = headInventoryMapper.findByNo(sprinklerDTO.getSprinklerNo());
        if (entity == null) {
            throw new SprinklerNotFoundException("喷头不存在: " + sprinklerDTO.getSprinklerNo());
        }

        // 校验当前状态
        if (entity.getStatus() != SprinklerStatus.IN_STOCK) {
            System.out.println("喷头当前不可领用，状态为: " + entity.getStatus().getDescription());
//            throw new IllegalStateException();
        }

        // 执行更新
        int updated = headInventoryMapper.allocateSprinkler(
                sprinklerDTO.getSprinklerNo(),
                SprinklerStatus.IN_STOCK,  // 原状态
                SprinklerStatus.IN_USE,    // 新状态
                sprinklerDTO.getOwner(),
                sprinklerDTO.getMachine(),
                sprinklerDTO.getColor(),
                sprinklerDTO.getPosition(),
                sprinklerDTO.getType(),
                sprinklerDTO.getUsageDate(),
                sprinklerDTO.getHistory(),
                entity.getVersion()
        );

        if (updated == 0) {
            throw new ConcurrentUpdateException("喷头状态已被其他操作修改");
        }
    }

    @Transactional
    public void batchAllocateSprinklers(List<SprinklerAllocationDTO> dtos) {
        for (SprinklerAllocationDTO dto : dtos) {
            allocateSprinkler(dto); // 复用单条处理逻辑
        }
    }

    private Info matchAndPrintResults(List<TxtRecord> txtRecords, List<ExcelRecord> excelRecords) {
//         创建复合键的哈希映射（sValue + "|" + k）
        Info info = new Info();
        Map<String, TxtRecord> txtMap = new HashMap<>();
        for (TxtRecord txt : txtRecords) {
            String compositeKey = txt.color + "|" + txt.ph;
            txtMap.put(compositeKey, txt);
        }

        // 单层循环查找
        for (ExcelRecord excelRecord : excelRecords) {
            String lookupKey = excelRecord.color + "|" + excelRecord.ph;
            TxtRecord matched = txtMap.get(lookupKey);
            boolean ok = false;
            if (matched != null) {
                ok = matched.sValue.equals(excelRecord.sValue);
            }
            if (ok) {
                System.out.printf("匹配成功: Excel记录[color=%s, ph=%d, s=%s] - TXT记录[color=%s, ph=%d, s=%s]%n",
                        excelRecord.color, excelRecord.ph, excelRecord.sValue, matched.color, matched.ph, matched.sValue);
                info.suc.add(excelRecord.dto);
            } else {
                System.out.printf("匹配不成功: Excel记录[color=%s, ph=%d, s=%s]%n",
                        excelRecord.color, excelRecord.ph, excelRecord.sValue);
                info.fail.add(excelRecord.dto);
            }
        }
        return info;
    }

    private class Info {
        List<SprinklerAllocationDTO> suc;
        List<SprinklerAllocationDTO> fail;

        public Info() {
            suc = new ArrayList<>();
            fail = new ArrayList<>();
        }
    }

    private List<ExcelRecord> parseExcelFile(MultipartFile file) throws Exception {
        List<ExcelRecord> records = new ArrayList<>();
        try (InputStream fis = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // 跳过标题行
                SprinklerAllocationDTO dto = new SprinklerAllocationDTO();
                String sprinklerNo = row.getCell(2).getStringCellValue();
                if (sprinklerNo == null || sprinklerNo.isEmpty()) continue;
                dto.setSprinklerNo(sprinklerNo);
                dto.setOwner(row.getCell(6).getStringCellValue());
                dto.setMachine(row.getCell(7).getStringCellValue());
                Cell kCell = row.getCell(8);
                if (kCell == null) continue;
                String kStr = kCell.getStringCellValue();
                String color_position = row.getCell(8).getStringCellValue();
                Matcher matcher = Pattern.compile("(\\d+)").matcher(color_position);
                if (matcher.find()) {
                    String color = color_position.substring(0, matcher.start());
                    String position = matcher.group();
                    dto.setColor(color);
                    dto.setPosition(position);
                }
                dto.setType(SprinklerType.NEW);
                dto.setUsageDate(LocalDate.now());
                dto.setHistory(row.getCell(9).getStringCellValue());

                records.add(new ExcelRecord(dto.getColor(), Integer.parseInt(dto.getPosition()), dto.getSprinklerNo(), dto));
            }
        }
        return records;
    }

    private class ExcelRecord {
        String color;
        int ph;
        String sValue;
        SprinklerAllocationDTO dto;

        public ExcelRecord(String color, int ph, String sValue, SprinklerAllocationDTO dto) {
            this.color = color;
            this.ph = ph;
            this.sValue = sValue;
            this.dto = dto;
        }

    }

    private class TxtRecord {
        String color;
        int ph;
        String sValue;

        public TxtRecord(String color, int ph, String sValue) {
            this.color = color;
            this.ph = ph;
            this.sValue = sValue;
        }

        public String getPosition(String position) {
            Pattern pattern = Pattern.compile("(\\d+)");
            Matcher matcher = pattern.matcher(position);
            if (matcher.find()) {
                int phNumber = Integer.parseInt(matcher.group(1));
            }
            return sValue;
        }
    }


    private List<TxtRecord> parseTxtFile(MultipartFile file) throws Exception {
        List<TxtRecord> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                // 提取Color值
                String[] parts = line.split("Color_");
                parts = parts[1].split(" PH_");
                String color = parts[0];
                parts = parts[1].split(":");
                int ph = Integer.parseInt(parts[0].trim());
                String rest = "";
                if (parts.length > 1) {
                    rest = parts[1];
                }
                // 提取序列号部分
                parts = rest.split("/s");
                if (parts != null && parts.length > 1) {
                    // 提取序列号
                    parts = parts[1].split("/");
                    String sValue = parts[0];
                    records.add(new TxtRecord(color, ph, sValue));
//                    if (sSegment.startsWith("s")) {
//                        String sValue = sSegment.substring(1);
//                        records.add(new TxtRecord(color, ph, sValue));
//                    }
                }
            }
        }

        return records;
    }

//    @Override
//    public List<String> list() {
//        List<String> result = headInventoryMapper.list();
//        return result;
//    }
}
