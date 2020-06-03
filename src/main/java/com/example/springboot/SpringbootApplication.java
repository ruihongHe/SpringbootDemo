package com.example.springboot;

import com.example.springboot.common.banner.MyBanner;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
/*@MapperScan(basePackages = {"com.example.springboot.modules.dao"})*/
public class SpringbootApplication {

	public static void main(String[] args) {
		SpringApplication springApplication=new SpringApplication(SpringbootApplication.class);

		springApplication.setBanner(new MyBanner());

		springApplication.run(args);
	}

}
