package com.ISC.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.ISC.project.config.FileStorageProperties;


@SpringBootApplication
@EnableConfigurationProperties({
    FileStorageProperties.class
})
public class SpringBootJpaIsCmanagementProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootJpaIsCmanagementProjectApplication.class, args);
		System.out.println("done");
	}

}
