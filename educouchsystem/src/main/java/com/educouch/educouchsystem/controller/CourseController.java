package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.dto.CourseDTO;
import com.educouch.educouchsystem.model.*;
import com.educouch.educouchsystem.service.CourseService;
import com.educouch.educouchsystem.service.EducatorService;
import com.educouch.educouchsystem.util.enumeration.CourseApprovalStatusEnum;
import com.educouch.educouchsystem.util.exception.CourseNotFoundException;
import com.educouch.educouchsystem.dto.CourseRejectionModel;
import com.educouch.educouchsystem.util.exception.InstructorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/course")
@CrossOrigin
public class CourseController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private EducatorService educatorService;

    @PostMapping("{instructorId}/courses")
    public ResponseEntity<Course> addCourse(@PathVariable(value="instructorId") Long instructorId, @RequestBody Course courseRequest) {
        try{
            Instructor instructor = educatorService.findInstructorById(instructorId);
            if (instructor.getCourses() == null) {
                List<Course> courseList = new ArrayList<>();
                instructor.setCourses(courseList);
            }
            instructor.getCourses().add(courseRequest);
            Course course = courseService.saveCourse(courseRequest);
            if (course.getInstructors() == null) {
                List<Instructor> instructorList = new ArrayList<>();
                course.setInstructors(instructorList);
            }
            course.getInstructors().add(instructor);
            return new ResponseEntity<>(course, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (InstructorNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> allCourses = new ArrayList<Course>();
        if (!courseService.getAllCourses().isEmpty()) {
            allCourses = courseService.getAllCourses();
            for(Course c: allCourses) {
                processCourse(c);
            }
            return new ResponseEntity<>(allCourses, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    private Course processCourse(Course c) {
        List<Forum> forums = c.getForums();
        for(Forum f: forums) {
            f.setForumDiscussions(null);
            f.setCourse(null);
//            f.setLearners(null);
//            f.setEducators(null);
        }

        List<Folder> childFolders = c.getFolders();
        for(Folder f: childFolders) {
            processFolder(f);
        }

        return c;
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

    @GetMapping("/courses/{courseId}")
    public ResponseEntity<Course> retrieveCourseById(@PathVariable("courseId") Long courseId) {
        try {
            Course existingCourse = courseService.retrieveCourseById(courseId);
            return new ResponseEntity<Course>(existingCourse, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<Course>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/courses/{courseId}")
    public ResponseEntity<HttpStatus> deleteCourse(@PathVariable("courseId") Long courseId) {
        courseService.deleteCourse(courseId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/courses/{courseId}")
    public ResponseEntity<Course> updateCourse(@RequestBody Course course, @PathVariable("courseId") Long courseId) {
        try {
            Course existingCourse = courseService.retrieveCourseById(courseId);
            existingCourse.setCourseDescription(course.getCourseDescription());
            existingCourse.setCourseTitle(course.getCourseTitle());
            existingCourse.setCourseTimeline(course.getCourseTimeline());
            existingCourse.setCourseCode(course.getCourseCode());
            existingCourse.setAgeGroup(course.getAgeGroup());
            existingCourse.setCourseApprovalStatus(course.getCourseApprovalStatus());
            existingCourse.setCourseMaxScore(course.getCourseMaxScore());
            courseService.saveCourse(existingCourse);

            return new ResponseEntity<Course>(existingCourse, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<Course>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/instructors/{instructorId}/courses")
    public ResponseEntity<List<Course>> getAllCoursesByInstructorId (@PathVariable(value="instructorId") Long instructorId) {
        try {
            Instructor instructor = educatorService.findInstructorById(instructorId);
            List<Course> courses = new ArrayList<>();
            courses.addAll(instructor.getCourses());

            return new ResponseEntity<>(courses, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (InstructorNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    @GetMapping("/coursesByCourseDTOValues")
//    public ResponseEntity<List<CourseDTO>> getAllCoursesByCourseDTOValues (@RequestBody CourseDTO courseDTO) {
    @GetMapping("/coursesByCourseDTOValues/{courseApprovalStatus}")
    public ResponseEntity<List<CourseDTO>> getAllCoursesByCourseDTOValues (@PathVariable(value="courseApprovalStatus") String courseApprovalStatus) {
        try {
            List<Course> courses = courseService.getCoursesByCourseApprovalStatus(courseApprovalStatus);
            List<CourseDTO> courseDTOs = new ArrayList<>();
            for (Course course : courses) {
                CourseDTO newCourseDTO = new CourseDTO();
                newCourseDTO.setCourseId(course.getCourseId());
                newCourseDTO.setCourseCode(course.getCourseCode());
                newCourseDTO.setCourseTitle(course.getCourseTitle());
                newCourseDTO.setCourseDescription(course.getCourseDescription());
                newCourseDTO.setCourseTimeline(course.getCourseTimeline());
                newCourseDTO.setCourseMaxScore(course.getCourseMaxScore());
                newCourseDTO.setRejectionReason(course.getRejectionReason());

                if (course.getAgeGroup() == AgeGroupEnum.KIDS) {
                    newCourseDTO.setAgeGroup("KIDS");
                } else if (course.getAgeGroup() == AgeGroupEnum.ADULTS){
                    newCourseDTO.setAgeGroup("ADULTS");
                }

                if (course.getCourseApprovalStatus() == CourseApprovalStatusEnum.LIVE) {
                    newCourseDTO.setCourseApprovalStatus("LIVE");
                } else if (course.getCourseApprovalStatus() == CourseApprovalStatusEnum.PENDINGAPPROVAL) {
                    newCourseDTO.setCourseApprovalStatus("PENDINGAPPROVAL");
                } else if (course.getCourseApprovalStatus() == CourseApprovalStatusEnum.REJECTED) {
                    newCourseDTO.setCourseApprovalStatus("REJECTED");
                } else if (course.getCourseApprovalStatus() == CourseApprovalStatusEnum.ACCEPTED) {
                    newCourseDTO.setCourseApprovalStatus("ACCEPTED");
                } else if (course.getCourseApprovalStatus() == CourseApprovalStatusEnum.UNDERCONSTRUCTION) {
                    newCourseDTO.setCourseApprovalStatus("UNDERCONSTRUCTION");
                }

                List<String> newCourseDTOCategoryTags = new ArrayList<>();
                for (CategoryTag categoryTag : course.getCategoryTags()) {
                    newCourseDTOCategoryTags.add(categoryTag.getTagName());
                }

                newCourseDTO.setCreatedByUserId(null);
                newCourseDTO.setCreatedByUserName(null);
                newCourseDTO.setCreatedByUserType(null);

                courseDTOs.add(newCourseDTO);
            }
            return new ResponseEntity<>(courseDTOs, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/courses/submitCourseForApproval/{courseId}")
    public String submitCourseForApproval(@PathVariable("courseId") Long courseId) {
        try {
            courseService.submitCourseForApproval(courseId);

            return "Course has been successfully submitted for approval.";
        } catch(CourseNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Course cannot be found", ex);
        }

    }

    @GetMapping("/courses/approveCourse/{courseId}")
    public String approveCourse(@PathVariable("courseId") Long courseId) {
        try {
            courseService.approveCourse(courseId);

            return "Course has been approved.";
        } catch(CourseNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Course cannot be found", ex);
        }

    }

    @GetMapping("/courses/publishCourse/{courseId}")
    public String publishCourse(@PathVariable("courseId") Long courseId) {
        try {
            courseService.publishCourse(courseId);

            return "Course has been published.";
        } catch(CourseNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Course cannot be found", ex);
        }

    }

    @PostMapping("/courses/rejectCourse")
    public String rejectCourse(@RequestBody CourseRejectionModel courseRejectionModel) {
        try {
            System.out.println("React A");
            Long courseId = courseRejectionModel.getCourseId();
            System.out.println("Course ID is " + courseId.toString());
            System.out.println("React b");
            String rejectionReason = courseRejectionModel.getRejectionReason();
            System.out.println("Rejection reason is " + rejectionReason);
            System.out.println("React c");

            courseService.rejectCourse(courseId, rejectionReason);

            return "Course has been rejected.";
        } catch(CourseNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Course cannot be found", ex);
        }

    }
}
