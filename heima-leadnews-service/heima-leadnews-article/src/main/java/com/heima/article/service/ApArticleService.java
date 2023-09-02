package com.heima.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.article.dtos.ArticleHomeDto;
import com.heima.model.article.pojos.ApArticle;
import com.heima.model.common.dtos.ResponseResult;

/**
 * @author: William
 * @date: 2023-08-27 01:31
 **/
public interface ApArticleService extends IService<ApArticle> {

    /**
     * 根据参数加载列表信息 1为加载更多 2为加载最新
     * @param loadtype
     * @param dto
     * @return
     */
    ResponseResult load(Short loadtype, ArticleHomeDto dto);
}
