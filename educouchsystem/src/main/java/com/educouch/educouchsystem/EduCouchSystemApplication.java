package com.educouch.educouchsystem;

import com.educouch.educouchsystem.model.Learner;
import com.educouch.educouchsystem.repository.LearnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

	@SpringBootApplication
	public class EduCouchSystemApplication implements CommandLineRunner {

	@Autowired
	private LearnerRepository learnerRepository;

	public static void main(String[] args) {
		SpringApplication.run(EduCouchSystemApplication.class, args);
	}

		//initialise new entities here
		@Override
		public void run(String... args) throws Exception {
			learnerRepository.save(new Learner("Alex", "SG", "alex@gmail.com", "password", "alex", "https://educouchbucket.s3.ap-southeast-1.amazonaws.com/1662869709706_alex.png"));
		}
	}