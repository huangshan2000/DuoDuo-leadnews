package com.heima.article.test;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.heima.article.ArticleApplication;
import com.heima.article.mapper.ApArticleContentMapper;
import com.heima.article.mapper.ApArticleMapper;
import com.heima.file.service.FileStorageService;
import com.heima.model.article.pojos.ApArticle;
import com.heima.model.article.pojos.ApArticleContent;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.HashMap;

/**
 * @author: William
 * @date: 2023-08-31 02:38
 **/
@SpringBootTest(classes = ArticleApplication.class)
@RequiredArgsConstructor
@RunWith(SpringRunner.class)
public class ArticleFreemarkerTest {
    private final Configuration configuration;

    private final ApArticleContentMapper apArticleContentMapper;

    private final FileStorageService fileStorageService;

    private final ApArticleMapper apArticleMapper;

    @Test
    public void createStaticUrlTest() throws Exception {

        //4.1 获取文章内容
        ApArticleContent apArticleContent = apArticleContentMapper.selectOne(Wrappers.<ApArticleContent>lambdaQuery().eq(ApArticleContent::getArticleId, 1390536764510310401L));
        if (apArticleContent != null && StringUtils.isNotBlank(apArticleContent.getContent())) {
            //4.2 文章内容通过freemarker生成html文件
            StringWriter out = new StringWriter();
            Template template = configuration.getTemplate("article.ftl");
            HashMap<String, Object> params = new HashMap<>();
            params.put("content", JSONArray.parseArray(apArticleContent.getContent()));
            template.process(params, out);
            ByteArrayInputStream is = new ByteArrayInputStream(out.toString().getBytes());

            //4.3 把html文件上传到minio中
            String path = fileStorageService.uploadHtmlFile("", apArticleContent.getArticleId() + ".html", is);
            //4.4 修改ap_articlr表，保存static_url字段
            ApArticle article = new ApArticle();

            article.setId(apArticleContent.getArticleId());
            article.setStaticUrl(path);

            apArticleMapper.updateById(article);
        }
    }
}
