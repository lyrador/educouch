package com.educouch.educouchsystem.data;

import com.educouch.educouchsystem.model.Learner;
import com.educouch.educouchsystem.model.LmsAdmin;
import com.educouch.educouchsystem.repository.LearnerRepository;
import com.educouch.educouchsystem.service.LmsAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component("loader")
public class DataLoader implements CommandLineRunner {

    private final LmsAdminService lmsAdminService;

    @Autowired
    private LearnerRepository learnerRepository;

    public DataLoader(LmsAdminService lmsAdminService) {
        this.lmsAdminService = lmsAdminService;
    }

    @Override
    public void run(String... args) throws Exception {
        if(lmsAdminService.getAllLmsAdmins().isEmpty()) {
            loadData();
            System.out.println("this is happening");
        }

    }

    public void loadData() {
        lmsAdminService.saveLmsAdmin(new LmsAdmin("manager","manager@gmail.com","password", "manager"));
        learnerRepository.save(new Learner("Alex", "SG", "alex@gmail.com", "password", "alex", "https://educouchbucket.s3.ap-southeast-1.amazonaws.com/1662869709706_alex.png"));

    }
}
