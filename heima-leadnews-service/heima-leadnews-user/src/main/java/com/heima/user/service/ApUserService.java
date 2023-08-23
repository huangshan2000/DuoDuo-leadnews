package com.heima.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.user.dtos.LoginDto;
import com.heima.model.user.pojos.ApUser;

/**
 * @author: William
 * @date: 2023-08-24 02:52
 **/
public interface ApUserService extends IService<ApUser> {

    /**
     * App端登录
     * @param dto
     * @return
     */
    public ResponseResult login(LoginDto dto);

}
