package com.haiyin.sprinkler.backend.fileprocessing.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllocateDTO {
    @NotBlank(message = "喷头编号不能为空")
    private String sprinklerNo;

    @NotBlank(message = "领用人不能为空")
    private String owner;

    @NotBlank(message = "机台名称不能为空")
    private String machine;

    @Pattern(regexp = "[A-Z]", message = "颜色分类格式错误")
    private String color;

    @Pattern(regexp = "\\d+", message = "位置格式错误")
    private String position;

    private LocalDate usageDate;

    private String history;

}
