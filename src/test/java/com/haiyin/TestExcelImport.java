package com.haiyin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TestExcelImport {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testExcelImport() throws Exception {
        // 准备测试用的 Excel 文件（需提前在 src/test/resources 下放入 data.xlsx）
        FileInputStream fis = new FileInputStream("src/test/resources/data.xlsx");
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file",
                "data.xlsx",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                fis
        );

        mockMvc.perform(MockMvcRequestBuilders.multipart("/excel/import")
                        .file(multipartFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(content().string("Excel file imported successfully!"));
    }
    @Test
    public void testBracket(){
        String purchaseDateAndContract = "2022-07-28(8127633)";
        // 示例 1：英文括号
        String[] partsEnglish = purchaseDateAndContract.split("[\\(（]");
        System.out.println("日期: " + partsEnglish[0].trim()); // 输出: 2022-07-28
        System.out.println("合同编号: " + partsEnglish[1].replace(")", "").replace("）", "").trim()); // 输出: 8127633

        // 示例 2：中文括号
        purchaseDateAndContract = "2022-07-28（8127633）";
        String[] partsChinese = purchaseDateAndContract.split("[\\(（]");
        System.out.println("日期: " + partsChinese[0].trim()); // 输出: 2022-07-28
        System.out.println("合同编号: " + partsChinese[1].replace(")", "").replace("）", "").trim()); // 输出: 8127633
    }
}
