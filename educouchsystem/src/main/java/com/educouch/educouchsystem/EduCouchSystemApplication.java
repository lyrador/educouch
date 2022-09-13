package com.educouch.educouchsystem;

import com.educouch.educouchsystem.model.Learner;
import com.educouch.educouchsystem.repository.LearnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

	@SpringBootApplication
	public class EduCouchSystemApplication  {

	@Autowired
	private LearnerRepository learnerRepository;

	public static void main(String[] args) {
		SpringApplication.run(EduCouchSystemApplication.class, args);
	}

	}
