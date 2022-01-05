package com.example.springboot.modules.dao;

import com.example.springboot.modules.entity.CmsContentPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author heruihong
 * @createTime 2022-01-05 16:07:38
 */
@Mapper
public interface CrawlerMapper {
    int addCmsContent(CmsContentPO record);
}
