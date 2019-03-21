package com.lwq.miaosha06.vo;

import com.lwq.miaosha06.validator.IsMobile;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @Author: Lwq
 * @Date: 2019/3/19 10:47
 * @Version 1.0
 * @Describe
 */
@Getter
@Setter
@ToString
public class LoginVo {

    @NotNull
    @IsMobile
    private String mobile;

    @NotNull
    @Length(min=32)
    private String password;

}

