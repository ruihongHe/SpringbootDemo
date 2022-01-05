package com.example.springboot.modules.service.impl;

import com.example.springboot.modules.dao.CrawlerMapper;
import com.example.springboot.modules.entity.CmsContentPO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Date;
import java.util.UUID;


/**
 * @author heruihong
 * @createTime 2022-01-05 16:14:04
 */
@Log4j2
@Component
public class ZhihuPipeline implements Pipeline {

    @Autowired
    private CrawlerMapper crawlerMapper;

    @Override
    public void process(ResultItems resultItems, Task task) {
        String title = resultItems.get("title");
        String answer = resultItems.get("answer");

        CmsContentPO contentPO = new CmsContentPO();
        contentPO.setContentId(UUID.randomUUID().toString());
        contentPO.setTitle(title);
        contentPO.setReleaseDate(new Date());
        contentPO.setContent(answer);

        try {
            boolean success = crawlerMapper.addCmsContent(contentPO) > 0;
            log.info("保存知乎文章成功：{}", title);
        } catch (Exception ex) {
            log.error("保存知乎文章失败", ex);
        }
    }
}
