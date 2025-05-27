package com.pj.journal;

import javax.sql.DataSource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@SpringBootApplication
public class PerfectJournalApplication {

	public static void main(String[] args) {
		SpringApplication.run(PerfectJournalApplication.class, args);
	}
	
	
}
