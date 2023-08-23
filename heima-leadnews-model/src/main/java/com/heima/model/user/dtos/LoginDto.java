package com.heima.model.user.dtos;

import lombok.Data;

/**
 * @author: William
 * @date: 2023-08-24 02:41
 **/
@Data
public class LoginDto {

    /**
     * 手机号
     */
    private String phone;

    /**
     * 密码
     */
    private String password;
}
