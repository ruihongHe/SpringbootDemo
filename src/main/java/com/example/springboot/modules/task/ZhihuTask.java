package com.example.springboot.modules.task;

import com.example.springboot.modules.service.impl.ZhihuPageProcessor;
import com.example.springboot.modules.service.impl.ZhihuPipeline;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author heruihong
 * @createTime 2022-01-05 16:18:10
 */
@Component
@Log4j2
public class ZhihuTask {


    @Autowired
    private ZhihuPipeline zhihuPipeline;

    @Autowired
    private ZhihuPageProcessor zhihuPageProcessor;

    private ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor();

    public void crawl() {
        // 定时任务，每10分钟爬取一次
        timer.scheduleWithFixedDelay(() -> {
            Thread.currentThread().setName("zhihuCrawlerThread");

            try {
                Spider.create(zhihuPageProcessor)
                        // 从https://www.zhihu.com/explore开始抓
                        .addUrl("https://www.zhihu.com/explore")
                        // 抓取到的数据存数据库
                        .addPipeline(zhihuPipeline)
                        // 开启2个线程抓取
                        .thread(2)
                        // 异步启动爬虫
                        .start();
            } catch (Exception ex) {
                log.error("定时抓取知乎数据线程执行异常", ex);
            }
        }, 0, 10, TimeUnit.MINUTES);
    }
}
