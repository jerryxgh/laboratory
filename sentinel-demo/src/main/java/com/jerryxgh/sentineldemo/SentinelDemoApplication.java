package com.jerryxgh.sentineldemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.jerryxgh.sentineldemo.persistence.mybatisplus.mapper")
@tk.mybatis.spring.annotation.MapperScan(basePackages = "com.jerryxgh.sentineldemo.persistence.tkmapper.mapper")
public class SentinelDemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(SentinelDemoApplication.class, args);
	}
}
