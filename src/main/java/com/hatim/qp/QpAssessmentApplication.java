package com.hatim.qp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@EnableScheduling
@CrossOrigin
public class QpAssessmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(QpAssessmentApplication.class, args);
	}

}
