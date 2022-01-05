package com.example.springboot;

import com.example.springboot.common.banner.MyBanner;
import com.example.springboot.modules.task.ZhihuTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootApplication {

	@Autowired
	private ZhihuTask zhihuTask;

	public static void main(String[] args) {
		SpringApplication springApplication=new SpringApplication(SpringbootApplication.class);

		springApplication.setBanner(new MyBanner());

		springApplication.run(args);
	}

	public void run(String... strings) throws Exception {
		// 爬取知乎数据
		zhihuTask.crawl();
	}


}
