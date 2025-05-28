package com.pj.journal;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.pj.journal.model")
public class PerfectJournalApplication {

	public static void main(String[] args) {
		SpringApplication.run(PerfectJournalApplication.class, args);
	}

}
