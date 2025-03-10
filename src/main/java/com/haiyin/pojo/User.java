package com.haiyin.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

//lombok 在编译阶段，为实体类自动生成setter getter toString
//pom文件中引入依赖 在实体类上添加注解
@Data
public class User {
    @NotNull
    private Integer id;
    private String username;

    @JsonIgnore//springmvc把当前对象转换为json字符串时，忽略password
    private String password;

    @NotEmpty
    @Pattern(regexp = "^\\S{1,10}$")
    private String nickname;

    @NotEmpty
    @Email
    private String email;
    private String userPic;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
