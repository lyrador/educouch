package com.educouch.educouchsystem.data;

import com.educouch.educouchsystem.model.*;
import com.educouch.educouchsystem.repository.*;
import com.educouch.educouchsystem.service.*;
import com.educouch.educouchsystem.util.enumeration.*;
import com.educouch.educouchsystem.util.exception.*;
import com.stripe.model.Price;
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
    private GradeBookEntryService gradeBookEntryService;

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

    @Autowired
    private GalleryService galleryService;

    @Autowired
    private LearnerService learnerService;

    @Autowired
    private ClassRunService classRunService;

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

        // create lms admin
        lmsAdminService.saveLmsAdmin(new LmsAdmin("manager", "manager@gmail.com", "password", "manager"));

        // create learners
        Learner learner_1 = learnerRepository.save(new Learner("Alex", "irenelie@u.nus.edu", "password",
                "alex", "https://educouchbucket.s3.ap-southeast-1.amazonaws.com/1662869709706_alex.png", false,
                "23456"));

        Learner learner_2 = learnerRepository.save(new Learner("Beatrice", "irenelie1412@gmail.com", "password",
                "beatrice", "https://educouchbucket.s3.ap-southeast-1.amazonaws.com/1665376248092_2938982-middle.png",
                false, "23456"));

        Learner learner_3 = learnerRepository.save(new Learner("Carol", "irenelie@nushackers.org", "password",
                "carol",
                "https://educouchbucket.s3.ap-southeast-1.amazonaws.com/1665376289662_imgbin-computer-icons-woman-avatar-avatar-girl-TBWeJMyXNwtNQA661FQ0rZSv2.jpg",
                false, "23456"));

        Learner learner_4 = learnerRepository.save(new Learner("David", "lielieirene@gmail.com", "password",
                "david",
                "https://educouchbucket.s3.ap-southeast-1.amazonaws.com/1665376079555_png-transparent-avatar-user-computer-icons-software-developer-avatar-child-face-heroes.png",
                true, "23456"));

        // create organisation and organisation admin
        Organisation org1 = new Organisation("FakeTuition", "issa fake tuition", "23423423234234");
        OrganisationAdmin orgAdmin = new OrganisationAdmin("grinivas", "grini@gmail.com", "password", "grinivas");
        organisationService.instantiateOrganisation(orgAdmin, org1);

        // create instructors
        Instructor i1 = new Instructor("milo", "milo@gmail.com", "milo", "password", InstructorAccessRight.INSTRUCTOR);
        Instructor i2 = new Instructor("horlicks", "horlicks@gmail.com", "horlicks", "password",
                InstructorAccessRight.HEADINSTRUCTOR);

        organisationService.addInstructor("1", i1);
        organisationService.addInstructor("1", i2);

        // create a course and link it to head instructor horlicks of organisation org 1
        Course cs2102 = new Course();
        cs2102.setCourseCode("CS2102");
        cs2102.setCourseTitle("Database Systems");
        cs2102.setCourseDescription("This is a new CS2102 course!");
        cs2102.setCourseTimeline("TILL END 2022");
        cs2102.setCourseMaxScore(100.00);
        cs2102.setAgeGroup(AgeGroupEnum.ADULTS);
        cs2102.setCourseFee(new BigDecimal(1000));
        cs2102.setStartDate(LocalDate.now().plusDays(7));
        // set it to live status to test enrollment functionality (in normal situations
        // classruns should be created first before making the course live)
        cs2102.setCourseApprovalStatus(CourseApprovalStatusEnum.LIVE);

        if (i2.getCourses() == null) {
            List<Course> courseList = new ArrayList<>();
            i2.setCourses(courseList);
        }
        i2.getCourses().add(cs2102);

        if (org1.getCourses() == null) {
            List<Course> courseList = new ArrayList<>();
            org1.setCourses(courseList);
        }
        org1.getCourses().add(cs2102);
        cs2102.setOrganisation(org1);

        if (cs2102.getInstructors() == null) {
            List<Instructor> instructorList = new ArrayList<>();
            cs2102.setInstructors(instructorList);
        }
        cs2102.getInstructors().add(i2);

        cs2102 = courseService.saveCourse(cs2102);

        // from here can create classruns and enrol learners in the course cs2102 to
        // test functionality
        Integer[] daysOfWeek = new Integer[2];
        daysOfWeek[0] = 1;
        daysOfWeek[1] = 2;
        ClassRun classRunOne = new ClassRun(LocalDate.now().plusDays(7), LocalDate.now().plusDays(90),
                LocalTime.MIDNIGHT, LocalTime.NOON, 1, 10, daysOfWeek, RecurringEnum.ALTERNATE);
        classRunOne.setInstructor(i2);
        try {
            courseService.addClassRunToCourse(cs2102.getCourseId(), classRunOne);
            cs2102 = courseService.getCourseById(cs2102.getCourseId());
            int size = cs2102.getClassRuns().size();
            List<ClassRun> listOfClassRuns = cs2102.getClassRuns();
            classRunOne = listOfClassRuns.get(0);
            try {
                stripeService.payDeposit(classRunOne.getClassRunId(), learner_1.getLearnerId(), new BigDecimal(100));
                // stripeService.payCourseFee(classRunOne.getClassRunId(),
                // learner_1.getLearnerId(), new BigDecimal(900));
            } catch (ClassRunNotFoundException | LearnerNotFoundException ex) {
                System.out.println("Error in enrolment.");
            }
        } catch (CourseNotFoundException ex) {
            System.out.println("Error in generating class run. ");
        }


        // create a course and link it to head instructor horlicks of organisation org 1
        Course cs2102k = new Course();
        cs2102k.setCourseCode("CS2102K");
        cs2102k.setCourseTitle("Database Systems for Kids");
        cs2102k.setCourseDescription("This is a new CS2102 course!");
        cs2102k.setCourseTimeline("TILL END 2022");
        cs2102k.setCourseMaxScore(100.00);
        cs2102k.setAgeGroup(AgeGroupEnum.KIDS);
        cs2102k.setCourseFee(new BigDecimal(1000));
        cs2102k.setStartDate(LocalDate.now().plusDays(7));
        // set it to live status to test enrollment functionality (in normal situations
        // classruns should be created first before making the course live)
        cs2102k.setCourseApprovalStatus(CourseApprovalStatusEnum.LIVE);

        if (i2.getCourses() == null) {
            List<Course> courseList = new ArrayList<>();
            i2.setCourses(courseList);
        }
        i2.getCourses().add(cs2102k);

        if (org1.getCourses() == null) {
            List<Course> courseList = new ArrayList<>();
            org1.setCourses(courseList);
        }
        org1.getCourses().add(cs2102k);
        cs2102k.setOrganisation(org1);

        if (cs2102k.getInstructors() == null) {
            List<Instructor> instructorList = new ArrayList<>();
            cs2102k.setInstructors(instructorList);
        }
        cs2102k.getInstructors().add(i2);

        cs2102k = courseService.saveCourse(cs2102k);

        // from here can create classruns and enrol learners in the course cs2102 to
        // test functionality
        Integer[] daysOfWeek1 = new Integer[2];
        daysOfWeek1[0] = 1;
        daysOfWeek1[1] = 2;
        ClassRun classRunOne1 = new ClassRun(LocalDate.now().plusDays(7), LocalDate.now().plusDays(90),
                LocalTime.MIDNIGHT, LocalTime.NOON, 1, 10, daysOfWeek, RecurringEnum.ALTERNATE);
        classRunOne1.setInstructor(i2);
        try {
            courseService.addClassRunToCourse(cs2102k.getCourseId(), classRunOne1);
            cs2102k = courseService.getCourseById(cs2102k.getCourseId());
            int size = cs2102k.getClassRuns().size();
            List<ClassRun> listOfClassRuns = cs2102k.getClassRuns();
            classRunOne1 = listOfClassRuns.get(0);
            try {
                stripeService.payDeposit(classRunOne1.getClassRunId(), learner_4.getLearnerId(), new BigDecimal(100));
                // stripeService.payCourseFee(classRunOne.getClassRunId(),
                // learner_1.getLearnerId(), new BigDecimal(900));
            } catch (ClassRunNotFoundException | LearnerNotFoundException ex) {
                System.out.println("Error in enrolment.");
            }
        } catch (CourseNotFoundException ex) {
            System.out.println("Error in generating class run. ");
        }

        // create a course and link it to head instructor horlicks of organisation org 1
        Course cs2105 = new Course();
        cs2105.setCourseCode("CS2105");
        cs2105.setCourseTitle("Introduction to Computer Network");
        cs2105.setCourseDescription("This is a new CS2102 course!");
        cs2105.setCourseTimeline("TILL END 2022");
        cs2105.setCourseMaxScore(100.00);
        cs2105.setAgeGroup(AgeGroupEnum.ADULTS);
        cs2105.setCourseFee(new BigDecimal(1000));
        cs2105.setStartDate(LocalDate.now().plusDays(7));
        // set it to live status to test enrollment functionality (in normal situations
        // classruns should be created first before making the course live)
        cs2105.setCourseApprovalStatus(CourseApprovalStatusEnum.LIVE);

        if (i2.getCourses() == null) {
            List<Course> courseList = new ArrayList<>();
            i2.setCourses(courseList);
        }
        i2.getCourses().add(cs2105);

        if (org1.getCourses() == null) {
            List<Course> courseList = new ArrayList<>();
            org1.setCourses(courseList);
        }
        org1.getCourses().add(cs2105);
        cs2105.setOrganisation(org1);

        if (cs2105.getInstructors() == null) {
            List<Instructor> instructorList = new ArrayList<>();
            cs2105.setInstructors(instructorList);
        }
        cs2105.getInstructors().add(i2);

        cs2105 = courseService.saveCourse(cs2105);

        // from here can create classruns and enrol learners in the course cs2102 to
        // test functionality
        Integer[] daysOfWeek2 = new Integer[2];
        daysOfWeek2[0] = 1;
        daysOfWeek2[1] = 2;
        ClassRun classRunOne2 = new ClassRun(LocalDate.now().plusDays(7), LocalDate.now().plusDays(90),
                LocalTime.MIDNIGHT, LocalTime.NOON, 1, 10, daysOfWeek, RecurringEnum.ALTERNATE);
        classRunOne2.setInstructor(i2);
        try {
            courseService.addClassRunToCourse(cs2105.getCourseId(), classRunOne2);
            cs2105 = courseService.getCourseById(cs2105.getCourseId());
            int size = cs2105.getClassRuns().size();
            List<ClassRun> listOfClassRuns = cs2105.getClassRuns();
            classRunOne2 = listOfClassRuns.get(0);
            try {
                stripeService.payDeposit(classRunOne2.getClassRunId(), learner_1.getLearnerId(), new BigDecimal(100));
                // stripeService.payCourseFee(classRunOne.getClassRunId(),
                // learner_1.getLearnerId(), new BigDecimal(900));
            } catch (ClassRunNotFoundException | LearnerNotFoundException ex) {
                System.out.println("Error in enrolment.");
            }
        } catch (CourseNotFoundException ex) {
            System.out.println("Error in generating class run. ");
        }

        // create a course and link it to head instructor horlicks of organisation org 1
        Course cs2105k = new Course();
        cs2105k.setCourseCode("CS2105K");
        cs2105k.setCourseTitle("Introduction to Computer Network for Kids");
        cs2105k.setCourseDescription("This is a new CS2102 course!");
        cs2105k.setCourseTimeline("TILL END 2022");
        cs2105k.setCourseMaxScore(100.00);
        cs2105k.setAgeGroup(AgeGroupEnum.KIDS);
        cs2105k.setCourseFee(new BigDecimal(1000));
        cs2105k.setStartDate(LocalDate.now().plusDays(7));
        // set it to live status to test enrollment functionality (in normal situations
        // classruns should be created first before making the course live)
        cs2105k.setCourseApprovalStatus(CourseApprovalStatusEnum.LIVE);

        if (i2.getCourses() == null) {
            List<Course> courseList = new ArrayList<>();
            i2.setCourses(courseList);
        }
        i2.getCourses().add(cs2105k);

        if (org1.getCourses() == null) {
            List<Course> courseList = new ArrayList<>();
            org1.setCourses(courseList);
        }
        org1.getCourses().add(cs2105k);
        cs2105k.setOrganisation(org1);

        if (cs2105k.getInstructors() == null) {
            List<Instructor> instructorList = new ArrayList<>();
            cs2105k.setInstructors(instructorList);
        }
        cs2105k.getInstructors().add(i2);

        cs2105k = courseService.saveCourse(cs2105k);

        // from here can create classruns and enrol learners in the course cs2102 to
        // test functionality
        Integer[] daysOfWeek3 = new Integer[2];
        daysOfWeek3[0] = 1;
        daysOfWeek3[1] = 2;
        ClassRun classRunOne3 = new ClassRun(LocalDate.now().plusDays(7), LocalDate.now().plusDays(90),
                LocalTime.MIDNIGHT, LocalTime.NOON, 1, 10, daysOfWeek, RecurringEnum.ALTERNATE);
        classRunOne3.setInstructor(i2);
        try {
            courseService.addClassRunToCourse(cs2105k.getCourseId(), classRunOne3);
            cs2105k = courseService.getCourseById(cs2105k.getCourseId());
            int size = cs2105k.getClassRuns().size();
            List<ClassRun> listOfClassRuns = cs2105k.getClassRuns();
            classRunOne3 = listOfClassRuns.get(0);
            try {
                stripeService.payDeposit(classRunOne3.getClassRunId(), learner_4.getLearnerId(), new BigDecimal(100));
                // stripeService.payCourseFee(classRunOne.getClassRunId(),
                // learner_1.getLearnerId(), new BigDecimal(900));
            } catch (ClassRunNotFoundException | LearnerNotFoundException ex) {
                System.out.println("Error in enrolment.");
            }
        } catch (CourseNotFoundException ex) {
            System.out.println("Error in generating class run. ");
        }

        // creating items
        // public Item(Integer price, String imageUrl, Boolean smallAvailable, Boolean mediumAvailable,
        // Boolean largeAvailable, String itemName, String itemDescription)

        Item item1 = new Item(30, "https://educouchbucket.s3.ap-southeast-1.amazonaws.com/1668456539402_castle.png", true, true, true, "Castle", "Sleeping Beauty Castle is a fairy tale castle at the center of Disneyland and formerly at Hong Kong Disneyland.");
        Item item2 = new Item(10, "https://educouchbucket.s3.ap-southeast-1.amazonaws.com/1668458409292_trees.png", true, true, true, "Pine Tree", "Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book");
        Item item3 = new Item(10, "https://educouchbucket.s3.ap-southeast-1.amazonaws.com/1668458465609_bonsai.png", true, true, true, "Bonsai Tree", "Test");
        Item item4 = new Item(15, "https://educouchbucket.s3.ap-southeast-1.amazonaws.com/1668459046674_bench.png", true, true, true, "Bench", "test");
        Item item5 = new Item(15, "https://educouchbucket.s3.ap-southeast-1.amazonaws.com/1668459006015_table.png", true, true, true, "Table", "test");


        galleryService.saveItem(item1);
        galleryService.saveItem(item2);
        galleryService.saveItem(item3);
        galleryService.saveItem(item4);
        galleryService.saveItem(item5);

    }
}