package com.cmc.recruitment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;

@SpringBootApplication(exclude = RepositoryRestMvcAutoConfiguration.class)
public class CmcRecruitmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(CmcRecruitmentApplication.class, args);
	}
}
