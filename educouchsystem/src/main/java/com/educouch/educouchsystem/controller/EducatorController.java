package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.model.*;
import com.educouch.educouchsystem.service.EducatorService;
import com.educouch.educouchsystem.service.LearnerService;
import com.educouch.educouchsystem.service.OrganisationService;
import com.educouch.educouchsystem.util.exception.*;
import com.educouch.educouchsystem.util.exception.InstructorNotFoundException;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/educator")
@CrossOrigin
public class EducatorController {

    @Autowired
    private EducatorService educatorService;
    @Autowired
    private OrganisationService organisationService;

    @Autowired
    private LearnerService learnerService;

    @GetMapping("/getAllOrgAdmin")
    public ResponseEntity<List<OrganisationAdmin>> getAllOrgAdmin() {
        return new ResponseEntity<List<OrganisationAdmin>>(educatorService.findAllOrgAdmin(), HttpStatus.OK);
    }

    @PostMapping("/updateOrgAdmin")
    public ResponseEntity<OrganisationAdmin> updateOrgAdmin(@RequestBody OrganisationAdmin organisationAdmin) {
        OrganisationAdmin updatedAdmin =educatorService.updateOrganisationAdmin(organisationAdmin);
        return new ResponseEntity<OrganisationAdmin>(updatedAdmin, HttpStatus.OK);
    }
    @PutMapping("/instantiateOrganisation")
    public ResponseEntity<Organisation> instantiateOrganisation(@RequestBody OrganisationInstantiation organisationInstantiation) {

        Organisation orgToInstantiate = organisationInstantiation.getOrganisation();
        OrganisationAdmin orgAdminToInstantiate = organisationInstantiation.getOrganisationAdmin();

        organisationService.instantiateOrganisation(orgAdminToInstantiate, orgToInstantiate);

        Organisation instantiatedOrganisation = organisationService.findOrganisationByOrganisationName(orgToInstantiate.getOrganisationName());
        instantiatedOrganisation = deserializeOrganisation(instantiatedOrganisation);
        return new ResponseEntity<Organisation>(instantiatedOrganisation, HttpStatus.OK);
    }

    @GetMapping("/findAllInstructors")
    public ResponseEntity<List<Instructor>> findAllInstructors(@RequestParam String organisationId) {
        List<Instructor> instr = new ArrayList<>();
        if (!organisationService.findAllInstructors(organisationId).isEmpty()) {
            instr = organisationService.findAllInstructors(organisationId);
            for (Instructor i : instr) {
                i = deserializeInstructor(i);
                i.setClassRuns(null);
                i.setOrganisation(null);
                i.setCourses(null);
            }
            return new ResponseEntity<>(instr, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/instructors/{instructorId}")
    public ResponseEntity<Instructor> findInstructorById (@PathVariable("instructorId") Long instructorId) {
        try {
            Instructor existingInstructor = educatorService.findInstructorById(instructorId);
            return new ResponseEntity<Instructor>(existingInstructor, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<Instructor>(HttpStatus.NOT_FOUND);
        } catch (InstructorNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findInstructor")
    public ResponseEntity<Instructor> findInstructor(@RequestParam String instructorUsername) {

        Instructor instructor = educatorService.findInstructorByUsername(instructorUsername);
        if(instructor!=null) {
            instructor = deserializeInstructor(instructor);
            return new ResponseEntity<Instructor>(instructor, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/addInstructor")
    public String addInstructor(@RequestParam String organisationId, @RequestBody Instructor instructor) {
        if (educatorService.findInstructorByUsername(instructor.getUsername()) != null
                || educatorService.findOrganisationAdminByUsernameNonException(instructor.getUsername()) != null
                || learnerService.findLearnerByUsernameNonException(instructor.getUsername()) != null) {
            throw new UsernameExistException("Username is taken!");
        } else {
            organisationService.addInstructor(organisationId, instructor);
        }
        return instructor.getUsername() + " user successfully added";
    }

    //need to do updateInstructor
    @PutMapping("/updateInstructor")
    public ResponseEntity<Instructor> updateInstructor(@RequestBody Instructor instructor) {
        try {
            Instructor updatedInstructor = educatorService.updateInstructor(instructor);
            updatedInstructor = deserializeInstructor(updatedInstructor);
            return new ResponseEntity<Instructor>(updatedInstructor, HttpStatus.OK);
        } catch (InstructorNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateInstructorAccessRight")
    public ResponseEntity<Instructor> updateInstructorAccessRight(@RequestParam String instructorId, @RequestBody String instructorAccessRight) {
        try {
            Instructor updatedInstructor = educatorService.updateInstructorAccessRight(instructorId, instructorAccessRight);
            updatedInstructor = deserializeInstructor(updatedInstructor);
            return new ResponseEntity<Instructor>(updatedInstructor, HttpStatus.OK);
        } catch (InstructorNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (InvalidInstructorAccessRight exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteInstructor")
    public ResponseEntity<String> deleteInstructor(@RequestParam String instructorId) {
        try {
            Long deletedInstructorId = educatorService.deleteInstructor(instructorId);
            return new ResponseEntity<String>("Instructor with Id: " + String.valueOf(deletedInstructorId) + " successfully Deleted", HttpStatus.OK);
        } catch (InstructorNotFoundException exception) {
            return new ResponseEntity<String>("Instructor Not Found", HttpStatus.BAD_REQUEST);
        } catch (OngoingClassRunException exception) {
            return new ResponseEntity<>("Instructor has Ongoing ClassRun", HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/findOrganisation/{instructorId}")
    public ResponseEntity<Organisation> getOrganisationByInstructorId(@PathVariable(value="instructorId") Long instructorId) {
        try {
            Instructor instructor = educatorService.findInstructorById(instructorId);
            Organisation organisation = organisationService.findOrganisationById(instructor.getOrganisation().getOrganisationId());
            return new ResponseEntity<Organisation>(organisation, HttpStatus.OK);
        } catch (InstructorNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findAllOrganisation")
    public ResponseEntity<List<Organisation>> getAllOrganisation() {
        List<Organisation> listOfOrganizations = organisationService.findAllOrganisation();
        for(Organisation o: listOfOrganizations) {
            List<Course> listOfCourses = o.getCourses();
            for(Course c: listOfCourses) {
                processCourse(c);
            }
        }
        return new ResponseEntity<List<Organisation>>(listOfOrganizations, HttpStatus.OK);

    }

    @GetMapping("/findAllDueOrganisation")
    public ResponseEntity<List<Organisation>> getAllDueOrganisation() {
        List<Organisation> listOfOrganizations = organisationService.findAllDue();
        for(Organisation o: listOfOrganizations) {
            List<Course> listOfCourses = o.getCourses();
            for(Course c: listOfCourses) {
                processCourse(c);
            }
        }
        return new ResponseEntity<List<Organisation>>(listOfOrganizations , HttpStatus.OK);

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





    public Instructor deserializeInstructor(Instructor i) {

        Organisation tempOrg = i.getOrganisation();
        tempOrg.getOrganisationAdmin().setOrganisation(null);
        tempOrg.setInstructors(null);
        return i;
    }

    public Organisation deserializeOrganisation(Organisation o) {

        List<Instructor> instr = o.getInstructors();
        for(Instructor i : instr) {
            i.setOrganisation(null);
        }
        o.getOrganisationAdmin().setOrganisation(null);

        List<Course> courses = o.getCourses();
        for(Course c: courses) {
            processCourse(c);
        }


        return o;
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

class OrganisationInstantiation {

    private Organisation organisation;
    private OrganisationAdmin organisationAdmin;

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    public OrganisationAdmin getOrganisationAdmin() {
        return organisationAdmin;
    }

    public void setOrganisationAdmin(OrganisationAdmin organisationAdmin) {
        this.organisationAdmin = organisationAdmin;
    }
}