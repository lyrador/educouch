package com.educouch.educouchsystem.data;

import com.educouch.educouchsystem.model.*;
import com.educouch.educouchsystem.repository.*;
import com.educouch.educouchsystem.service.*;
import com.educouch.educouchsystem.util.enumeration.CourseApprovalStatusEnum;
import com.educouch.educouchsystem.util.enumeration.FileSubmissionEnum;
import com.educouch.educouchsystem.util.enumeration.InstructorAccessRight;
import com.educouch.educouchsystem.util.exception.FolderUnableToSaveException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

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
        lmsAdminService.saveLmsAdmin(new LmsAdmin("manager", "manager@gmail.com", "password", "manager"));
        learnerRepository.save(new Learner("Alex", "SG", "alex@gmail.com", "password",
                "alex", "https://educouchbucket.s3.ap-southeast-1.amazonaws.com/1662869709706_alex.png"));

        Course cs1010 = new Course("CS1010", "CS1010 Introduction to Computer Science",
                "xxx", "xxx", 100.0, AgeGroupEnum.ADULTS,
                CourseApprovalStatusEnum.UNDERCONSTRUCTION);
        Course bio4000 = new Course("BIO4000", "BIO4000 Molecular Genetics", "xxx",
                "xxx", 100.0, AgeGroupEnum.ADULTS, CourseApprovalStatusEnum.UNDERCONSTRUCTION);

        cs1010 = courseRepository.save(cs1010);
        bio4000 = courseRepository.save(bio4000);

        Folder a = new Folder("Week 1: Variable");
        Folder b = new Folder("Week 2: If conditionals");
        Folder c = new Folder("Week 3: Iterative Control");

        //create organisation
        Organisation org1 = new Organisation("FakeTuition");
        OrganisationAdmin orgAdmin = new OrganisationAdmin("grinivas", "grini@gmail.com", "password", "grinivas");

        organisationService.instantiateOrganisation(orgAdmin, org1);

        //create instructors
        Instructor i1 = new Instructor("milo", "milo@gmail.com", "milo", "password", InstructorAccessRight.INSTRUCTOR);
        Instructor i2 = new Instructor("horlicks", "horlicks@gmail.com", "horlicks", "password", InstructorAccessRight.HEADINSTRUCTOR);

        organisationService.addInstructor("1", i1);
        organisationService.addInstructor("1", i2);

//        Folder saveFolder(Long courseId, Folder folder) throws FolderUnableToSaveException;
        try {
            a = folderService.saveFolder(cs1010.getCourseId(), a);
            b = folderService.saveFolder(cs1010.getCourseId(), b);
            c = folderService.saveFolder(cs1010.getCourseId(), c);

//            Folder childA = new Folder("Lecture Slides");
//            Folder childB = new Folder("Homework");
//            Folder childC = new Folder("In-class Activity");
//
//
//            childA = folderService.saveFolder(cs1010.getCourseId(), a.getFolderId(), childA);
//            childB = folderService.saveFolder(cs1010.getCourseId(), a.getFolderId(), childB);
//            childC = folderService.saveFolder(cs1010.getCourseId(), a.getFolderId(), childC);
        } catch (FolderUnableToSaveException ex) {
            System.out.println("Unable to save folder during initialisation");
        }


        //create FileSubmission Assessment
        FileSubmission newFileSubmission = new FileSubmission("Quiz A", "abcde", 20.0, new Date(), new Date(), FileSubmissionEnum.INDIVIDUAL);
        fileSubmissionRepository.save(newFileSubmission);
    }
}
