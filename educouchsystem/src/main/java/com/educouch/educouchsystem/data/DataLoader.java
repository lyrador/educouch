package com.educouch.educouchsystem.data;

import com.educouch.educouchsystem.model.*;
import com.educouch.educouchsystem.repository.*;
import com.educouch.educouchsystem.service.*;
import com.educouch.educouchsystem.util.enumeration.AgeGroupEnum;
import com.educouch.educouchsystem.util.enumeration.CourseApprovalStatusEnum;
import com.educouch.educouchsystem.util.enumeration.FileSubmissionEnum;
import com.educouch.educouchsystem.util.enumeration.InstructorAccessRight;
import com.educouch.educouchsystem.util.exception.CourseNotFoundException;
import com.educouch.educouchsystem.util.exception.FolderUnableToSaveException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component("loader")
public class DataLoader implements CommandLineRunner {

    private final LmsAdminService lmsAdminService;

    @Autowired
    private LearnerRepository learnerRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseService courseService;

    @Autowired
    private AssessmentRepository assessmentRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private FileSubmissionRepository fileSubmissionRepository;

    @Autowired
    private FileSubmissionService fileSubmissionService;

    @Autowired
    private FolderService folderService;

    @Autowired
    private EducatorService educatorService;

    @Autowired
    private OrganisationService organisationService;

    @Autowired
    private QuizService quizService;

    @Autowired
    private ClassRunRepository classRunRepository;



    public DataLoader(LmsAdminService lmsAdminService) {
        this.lmsAdminService = lmsAdminService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (lmsAdminService.getAllLmsAdmins().isEmpty()) {
            loadData();
            System.out.println("this is happening");
        }

    }

    public void loadData() {


        // data for irene's part - course enrollment
        lmsAdminService.saveLmsAdmin(new LmsAdmin("manager", "manager@gmail.com", "password", "manager"));

        learnerRepository.save(new Learner("Alex", "alex@gmail.com", "password",
                "alex", "https://educouchbucket.s3.ap-southeast-1.amazonaws.com/1662869709706_alex.png", true));

        System.out.println("Creating course...");
        Course cs1010 = new Course("CS1010", "CS1010 Introduction to Computer Science",
                "xxx", "xxx", 100.0, AgeGroupEnum.ADULTS);
        cs1010.setCourseApprovalStatus(CourseApprovalStatusEnum.LIVE);
        cs1010.setCourseFee(new BigDecimal(1000));
        Course bio4000 = new Course("BIO4000", "BIO4000 Molecular Genetics", "xxx",
                "xxx", 100.0, AgeGroupEnum.KIDS);
        bio4000.setCourseApprovalStatus(CourseApprovalStatusEnum.LIVE);
        bio4000.setCourseFee(new BigDecimal(1000));


        System.out.println("Creating class runs...");

        ClassRun c1 = new ClassRun();
        c1.setClassRunStart(LocalDate.parse("2022-09-30"));
        c1.setClassRunEnd(LocalDate.parse("2022-12-30"));
        c1.setMinClassSize(1);
        c1.setMaximumCapacity(3);
        c1.setClassRunStartTime(LocalTime.MIDNIGHT);
        c1.setClassRunEndTime(LocalTime.NOON);

        ClassRun c2 = new ClassRun();
        c2.setClassRunStart(LocalDate.parse("2022-10-01"));
        c2.setClassRunEnd(LocalDate.parse("2023-01-30"));
        c2.setMinClassSize(10);
        c2.setMaximumCapacity(20);
        c2.setClassRunStartTime(LocalTime.MIDNIGHT);
        c2.setClassRunEndTime(LocalTime.NOON);

        ClassRun c3 = new ClassRun();
        c3.setClassRunStart(LocalDate.parse("2022-09-30"));
        c3.setClassRunEnd(LocalDate.parse("2022-12-30"));
        c3.setMinClassSize(1);
        c3.setMaximumCapacity(3);
        c3.setClassRunStartTime(LocalTime.MIDNIGHT);
        c3.setClassRunEndTime(LocalTime.NOON);

        ClassRun c4 = new ClassRun();
        c4.setClassRunStart(LocalDate.parse("2022-10-01"));
        c4.setClassRunEnd(LocalDate.parse("2023-01-30"));
        c4.setMinClassSize(10);
        c4.setMaximumCapacity(20);
        c4.setClassRunStartTime(LocalTime.MIDNIGHT);
        c4.setClassRunEndTime(LocalTime.NOON);


        cs1010 =  courseService.saveCourse(cs1010);
        bio4000 =  courseService.saveCourse(bio4000);

        try {
            courseService.addClassRunToCourse(cs1010.getCourseId(), c1);
            courseService.addClassRunToCourse(cs1010.getCourseId(), c2);

            courseService.addClassRunToCourse(bio4000.getCourseId(), c3);
            courseService.addClassRunToCourse(bio4000.getCourseId(), c4);
        } catch(CourseNotFoundException ex) {
            System.out.println("Course not found exception.");
        }







        //create organisation
        Organisation org1 = new Organisation("FakeTuition");
        OrganisationAdmin orgAdmin = new OrganisationAdmin("grinivas", "grini@gmail.com", "password", "grinivas");

        organisationService.instantiateOrganisation(orgAdmin, org1);

        //create instructors
        Instructor i1 = new Instructor("milo", "milo@gmail.com", "milo", "password", InstructorAccessRight.INSTRUCTOR);
        Instructor i2 = new Instructor("horlicks", "horlicks@gmail.com", "horlicks", "password", InstructorAccessRight.HEADINSTRUCTOR);

        organisationService.addInstructor("1", i1);
        organisationService.addInstructor("1", i2);



//
//        //create FileSubmission Assessment
//        FileSubmission newFileSubmission = new FileSubmission("Quiz A", "abcde", 20.0, new Date(), new Date(), FileSubmissionEnum.INDIVIDUAL);
//        fileSubmissionRepository.save(newFileSubmission);
    }
}
