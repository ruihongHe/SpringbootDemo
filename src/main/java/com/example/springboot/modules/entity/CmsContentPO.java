package com.example.springboot.modules.entity;

/**
 * @author heruihong
 * @createTime 2022-01-05 16:04:31
 */
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CmsContentPO {

    private String contentId;

    private String title;

    private String content;

    private Date releaseDate;
}
