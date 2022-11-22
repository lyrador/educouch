package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.dto.LearnerDTO;
import com.educouch.educouchsystem.model.*;
import com.educouch.educouchsystem.service.EducatorService;
import com.educouch.educouchsystem.service.LearnerService;
import com.educouch.educouchsystem.util.exception.FolderNotFoundException;
import com.educouch.educouchsystem.util.exception.UsernameExistException;
import com.educouch.educouchsystem.util.exception.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

//so it will get response body and controller at the same time
@RestController
//giving learner path here
@RequestMapping("/learner")
//tells the react and springboot application to connect to each other
@CrossOrigin
public class LearnerController {
    @Autowired
    private LearnerService learnerService;

    @Autowired
    private EducatorService educatorService;

    //giving path here
    @PostMapping("/add")
    public String add(@RequestBody LearnerDTO learnerDTO) {
        Boolean isKid = true;
        if (learnerDTO.getIsKid().equals("false")) {
            isKid = false;
        }
        if (educatorService.findInstructorByUsername(learnerDTO.getUsername()) != null
                || educatorService.findOrganisationAdminByUsernameNonException(learnerDTO.getUsername()) != null
                || learnerService.findLearnerByUsernameNonException(learnerDTO.getUsername()) != null) {
            throw new UsernameExistException("Username is taken!");
        } else {
            learnerService.saveLearnerWithoutGallery(
                    new Learner(learnerDTO.getName(), learnerDTO.getEmail(), learnerDTO.getPassword(), learnerDTO.getUsername(), learnerDTO.getProfilePictureURL(), isKid, "123123312")
            );
        }
        return "New learner is added";
    }

    @GetMapping("/getAll")
    public List<Learner> getAllLearners(){
        List<Learner> learners = learnerService.getAllLearners();
        for(Learner l: learners) {
            List<ClassRun> listOfClassRuns = l.getClassRuns();
            for(ClassRun cr: listOfClassRuns) {
                processClassRun(cr);
            }
            List<EnrolmentStatusTracker> enrolmentStatusTrackers = l.getEnrolmentStatusTrackers();
            for(EnrolmentStatusTracker est: enrolmentStatusTrackers) {
                processEnrolmentStatusTracker(est);
            }
        }
        return learnerService.getAllLearners();
    }
    @GetMapping("/getAllKidsLearner")
    public List<LearnerDTO> getAllKidsLearners(){
        List<Learner> learners = learnerService.getAllKidsLearner();
        List<LearnerDTO> listOfKidsLearners = new ArrayList<>();
        for(Learner l: learners) {
            listOfKidsLearners.add(new LearnerDTO(l.getLearnerId(), l.getName(), l.getEmail(), l.getUsername(), l.getProfilePictureURL()));

        }
        return listOfKidsLearners;
    }

    @GetMapping("/retrieveById")
    public LearnerDTO getAllKidsLearners(@RequestParam Long learnerId){
        Learner l = learnerService.getLearnerById(learnerId);
        LearnerDTO dto = new LearnerDTO(l.getLearnerId(), l.getName(), l.getEmail(),
                l.getUsername(), l.getProfilePictureURL());
        return dto;
    }

    @PostMapping("/update")
    public ResponseEntity<Learner> updateLearner(@RequestBody Learner learner) {
        Learner updatedLearner = learnerService.updateLearner(learner);
        return ResponseEntity.status(HttpStatus.OK).body(updatedLearner);
    }

    @GetMapping("/learnerEnrolled")
    @ResponseBody
    public Boolean learnerEnrolled(@RequestParam String courseId, @RequestParam String learnerId) {
        return true;

    }

    @GetMapping("/getById")
    public ResponseEntity<Learner> getLearnerById(@RequestParam Long learnerId) {
        Learner l = learnerService.getLearnerById(learnerId);
        List<ClassRun> listOfClassRuns = l.getClassRuns();
        for(ClassRun cr: listOfClassRuns) {
            processClassRun(cr);
        }
        l.setEnrolmentStatusTrackers(null);
//        List<EnrolmentStatusTracker> enrolmentStatusTrackers = l.getEnrolmentStatusTrackers();
//        for(EnrolmentStatusTracker est: enrolmentStatusTrackers) {
//            processEnrolmentStatusTracker(est);
//        }

        return ResponseEntity.status(HttpStatus.OK).body(l);


    }

    private void processEnrolmentStatusTracker(EnrolmentStatusTracker e) {
        e.setClassRun(null);
        e.setLearner(null);
    }

    private Course processCourse(Course c) {
        List<Forum> forums = c.getForums();
        for(Forum f: forums) {
            f.setCreatedByLearner(null);
            f.setCreatedByInstructor(null);
            f.setCreatedByOrganisationAdmin(null);
            f.setForumDiscussions(null);
            f.setCourse(null);
//            f.setLearners(null);
//            f.setEducators(null);
        }

        List<Folder> childFolders = c.getFolders();
        for(Folder f: childFolders) {
            processFolder(f);
        }
        List<ClassRun> listOfClassRuns = c.getClassRuns();
        for(ClassRun r: listOfClassRuns) {
            processClassRun(r);
        }

        return c;
    }

    private void processClassRun(ClassRun c) {
        List<EnrolmentStatusTracker> enrolmentStatusTrackers = c.getEnrolmentStatusTrackers();
        for(EnrolmentStatusTracker e: enrolmentStatusTrackers) {
            e.setClassRun(null);
            e.setLearner(null);
        }

        c.setCalendar(null);
        c.setEvents(null);
        Instructor i = c.getInstructor();
        if(i != null) {
            i.setClassRuns(null);
            i.setOrganisation(null);
            i.setCourses(null);
            i.setRequests(null);
        }


        Course course = c.getCourse();
        course.setFolders(null);
        course.setClassRuns(null);
        course.setFolders(null);
        course.setAssessments(null);
        course.setInstructors(null);
        course.setOrganisation(null);

        c.setEnrolledLearners(null);
        c.setEnrolmentStatusTrackers(null);
        c.setLearnerTransactions(null);


    }

    private Folder processFolder(Folder folder) {
        List<Folder> subFolders = folder.getChildFolders();
//        for(Folder cf: subFolders) {
//            cf.setParentFolder(null);
//            cf.setAttachments(null);
//            cf.setChildFolders(null);
//            cf.setCourse(null);
//        }
        Folder parentFolder = folder.getParentFolder();
        if(parentFolder != null) {
            parentFolder.setChildFolders(null);
            parentFolder.setAttachments(null);
            parentFolder.setParentFolder(null);
            parentFolder.setCourse(null);
        }
        folder.setCourse(null);
        folder.getAttachments();

        return folder;

    }
}
