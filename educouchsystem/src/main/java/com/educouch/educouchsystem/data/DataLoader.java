package com.educouch.educouchsystem.data;

import com.educouch.educouchsystem.model.*;
import com.educouch.educouchsystem.repository.*;
import com.educouch.educouchsystem.service.*;
import com.educouch.educouchsystem.util.enumeration.AgeGroupEnum;
import com.educouch.educouchsystem.util.enumeration.CourseApprovalStatusEnum;
import com.educouch.educouchsystem.util.enumeration.FileSubmissionEnum;
import com.educouch.educouchsystem.util.enumeration.InstructorAccessRight;
import com.educouch.educouchsystem.util.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

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
    private DepositRefundRequestRepository depositRefundRequestRepository;


    @Autowired
    private OrganisationService organisationService;

    @Autowired
    private QuizService quizService;

    @Autowired
    private ClassRunRepository classRunRepository;

    @Autowired
    private EnrolmentStatusTrackerService enrolmentStatusTrackerService;

    @Autowired
    private StripeService stripeService;



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


        // lms admin
        lmsAdminService.saveLmsAdmin(new LmsAdmin("manager", "manager@gmail.com", "password", "manager"));

        // learner
        Learner learner_1  = learnerRepository.save(new Learner("Alex", "irenelie@u.nus.edu", "password",
                "alex", "https://educouchbucket.s3.ap-southeast-1.amazonaws.com/1662869709706_alex.png", true, "23456"));

        Learner learner_2 = learnerRepository.save(new Learner("Beatrice", "irenelie1412@gmail.com", "password",
                "beatrice", "https://educouchbucket.s3.ap-southeast-1.amazonaws.com/1662869709706_alex.png", true, "23456"));

        Learner learner_3 =  learnerRepository.save(new Learner("Diana", "irenelie@nushackers.org", "password",
                "diana", "https://educouchbucket.s3.ap-southeast-1.amazonaws.com/1662869709706_alex.png", true, "23456"));

        Learner learner_4 =  learnerRepository.save(new Learner("Beatrice", "lielieirene@gmail.com", "password",
                "david", "https://educouchbucket.s3.ap-southeast-1.amazonaws.com/1662869709706_alex.png", true, "23456"));

        // courses
        Course cs1010 = new Course("CS1010", "Introduction to Computer Science",
                "xxx", "xxx", 100.0, AgeGroupEnum.ADULTS);
        cs1010.setCourseApprovalStatus(CourseApprovalStatusEnum.LIVE);
        cs1010.setCourseFee(new BigDecimal(1000));
        cs1010.setStartDate(LocalDate.now().plusDays(7));

        Course bio4000 = new Course("BIO4000", "Molecular Genetics", "xxx",
                "xxx", 100.0, AgeGroupEnum.KIDS);
        bio4000.setCourseApprovalStatus(CourseApprovalStatusEnum.LIVE);
        bio4000.setCourseFee(new BigDecimal(1000));
        bio4000.setStartDate(LocalDate.now().plusDays(10));

        // class run
        Integer[] test1 = new Integer[2];
        test1[0] = 2;
        test1[1] = 5;

        Integer[] test2 = new Integer[2];
        test2[0] = 1;
        test2[1] = 3;

        ClassRun c1 = new ClassRun();
        c1.setClassRunStart(LocalDate.now().plusDays(7));
        c1.setClassRunEnd(LocalDate.now().plusMonths(3));
        c1.setMinClassSize(1);
        c1.setMaximumCapacity(3);
        c1.setClassRunStartTime(LocalTime.MIDNIGHT);
        c1.setClassRunEndTime(LocalTime.NOON);

        c1.setClassRunDaysOfTheWeek(test1);

        ClassRun c2 = new ClassRun();
        c2.setClassRunStart(LocalDate.now().plusDays(10));
        c2.setClassRunEnd(LocalDate.now().plusMonths(3));
        c2.setMinClassSize(10);
        c2.setMaximumCapacity(20);
        c2.setClassRunStartTime(LocalTime.MIDNIGHT);
        c2.setClassRunEndTime(LocalTime.NOON);
        c2.setClassRunDaysOfTheWeek(test2);

        ClassRun c3 = new ClassRun();
        c3.setClassRunStart(LocalDate.now().plusDays(7));
        c3.setClassRunEnd(LocalDate.now().plusMonths(3));
        c3.setMinClassSize(1);
        c3.setMaximumCapacity(3);
        c3.setClassRunStartTime(LocalTime.MIDNIGHT);
        c3.setClassRunEndTime(LocalTime.NOON);
        c3.setClassRunDaysOfTheWeek(test1);

        ClassRun c4 = new ClassRun();
        c4.setClassRunStart(LocalDate.now().plusDays(7));
        c4.setClassRunEnd(LocalDate.now().plusMonths(3));
        c4.setMinClassSize(10);
        c4.setMaximumCapacity(20);
        c4.setClassRunStartTime(LocalTime.MIDNIGHT);
        c4.setClassRunEndTime(LocalTime.NOON);
        c4.setClassRunDaysOfTheWeek(test2);


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


        try {
            // to test maximum class enrolment
            stripeService.payDeposit(c1.getClassRunId(), learner_1.getLearnerId(), new BigDecimal(100));
            stripeService.payDeposit(c1.getClassRunId(), learner_2.getLearnerId(), new BigDecimal(100));
            stripeService.payDeposit(c1.getClassRunId(), learner_3.getLearnerId(), new BigDecimal(100));
            stripeService.payDeposit(c1.getClassRunId(), learner_4.getLearnerId(), new BigDecimal(100));

            // to test if nvr hit min capacity
            stripeService.payDeposit(c4.getClassRunId(), learner_1.getLearnerId(), new BigDecimal(100));
            stripeService.payDeposit(c4.getClassRunId(), learner_2.getLearnerId(), new BigDecimal(100));
            stripeService.payDeposit(c4.getClassRunId(), learner_3.getLearnerId(), new BigDecimal(100));
            stripeService.payDeposit(c4.getClassRunId(), learner_4.getLearnerId(), new BigDecimal(100));
        } catch(ClassRunNotFoundException | LearnerNotFoundException ex) {
            System.out.println("Could not add deposit record data");
        }



        //create organisation
        Organisation org1 = new Organisation("FakeTuition", "issa fake tuition", "23423423234234");
        OrganisationAdmin orgAdmin = new OrganisationAdmin("grinivas", "grini@gmail.com", "password", "grinivas");

        organisationService.instantiateOrganisation(orgAdmin, org1);

        //create instructors
        Instructor i1 = new Instructor("milo", "milo@gmail.com", "milo", "password", InstructorAccessRight.INSTRUCTOR);
        Instructor i2 = new Instructor("horlicks", "horlicks@gmail.com", "horlicks", "password", InstructorAccessRight.HEADINSTRUCTOR);

        organisationService.addInstructor("1", i1);
        organisationService.addInstructor("1", i2);

        //linking a course to horlicks head instructor and org 1
        Course courseRequest = new Course();
        courseRequest.setCourseCode("TEST123");
        courseRequest.setCourseTitle("TESTING 123");
        courseRequest.setCourseDescription("TESTING 123");
        courseRequest.setCourseTimeline("TILL END 2022");
        courseRequest.setCourseMaxScore(100.00);
        courseRequest.setAgeGroup(AgeGroupEnum.ADULTS);

        if (i2.getCourses() == null) {
            List<Course> courseList = new ArrayList<>();
            i2.setCourses(courseList);
        }
        i2.getCourses().add(courseRequest);

        if (org1.getCourses() == null) {
            List<Course> courseList = new ArrayList<>();
            org1.setCourses(courseList);
        }
        org1.getCourses().add(courseRequest);
        courseRequest.setOrganisation(org1);

        if (courseRequest.getInstructors() == null) {
            List<Instructor> instructorList = new ArrayList<>();
            courseRequest.setInstructors(instructorList);
        }
        courseRequest.getInstructors().add(i2);

        Course course = courseService.saveCourse(courseRequest);
        depositRefundRequestRepository.save(new DepositRefundRequest(1l, new BigDecimal(100)));
//
//        //create FileSubmission Assessment
//        FileSubmission newFileSubmission = new FileSubmission("Quiz A", "abcde", 20.0, new Date(), new Date(), FileSubmissionEnum.INDIVIDUAL);
//        fileSubmissionRepository.save(newFileSubmission);
    }

    private void testDataForTimer() {

    }
}
