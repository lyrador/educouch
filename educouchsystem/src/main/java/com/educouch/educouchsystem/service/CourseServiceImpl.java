package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.ClassRun;
import com.educouch.educouchsystem.model.Course;
import com.educouch.educouchsystem.model.Learner;
import com.educouch.educouchsystem.repository.ClassRunRepository;
import com.educouch.educouchsystem.repository.LearnerRepository;
import com.educouch.educouchsystem.util.enumeration.CourseApprovalStatusEnum;
import com.educouch.educouchsystem.repository.CourseRepository;
import com.educouch.educouchsystem.util.exception.CourseNotFoundException;
import com.educouch.educouchsystem.util.exception.LearnerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService{

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ClassRunRepository classRunRepository;

    @Autowired
    private LearnerService learnerService;

    //creating a course
    @Override
    public Course saveCourse(Course course) {

        return courseRepository.save(course);
    }

    //retrieving all courses
    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    //retrieving a course by Id
    @Override
    public Course retrieveCourseById(Long id) {
        return courseRepository.findById(id).get();
    }

    //deleting a course by Id
    @Override
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    public Course getCourseById(Long courseId) throws CourseNotFoundException {
        Optional<Course> courseOpt =  courseRepository.findById(courseId);
        if(courseOpt.isPresent()) {
            return courseOpt.get();
        } else {
            throw new CourseNotFoundException("Course cannot be found");
        }
    }

    @Override
    public Course getCourseByCourseCode(String courseCode) {
        Course c = courseRepository.findCourseByCourseCode(courseCode);
        return c;
    }

    @Override
    public List<Course> getCoursesByCourseApprovalStatus(String courseApprovalStatus) {
        CourseApprovalStatusEnum courseApprovalStatusEnum = CourseApprovalStatusEnum.LIVE;
        if (courseApprovalStatus.equals("LIVE")) {
            courseApprovalStatusEnum = CourseApprovalStatusEnum.LIVE;
        } else if (courseApprovalStatus.equals("PENDINGAPPROVAL")) {
            courseApprovalStatusEnum = CourseApprovalStatusEnum.PENDINGAPPROVAL;
        } else if (courseApprovalStatus.equals("REJECTED")) {
            courseApprovalStatusEnum = CourseApprovalStatusEnum.REJECTED;
        } else if (courseApprovalStatus.equals("ACCEPTED")) {
            courseApprovalStatusEnum = CourseApprovalStatusEnum.ACCEPTED;
        } else if (courseApprovalStatus.equals("UNDERCONSTRUCTION")) {
            courseApprovalStatusEnum = CourseApprovalStatusEnum.UNDERCONSTRUCTION;
        }
        List<Course> courses = courseRepository.findCoursesByCourseApprovalStatus(courseApprovalStatusEnum);
        return courses;
    }

    @Override
    public void submitCourseForApproval(Long courseId) throws CourseNotFoundException {
        Course c = retrieveCourseById(courseId);
        if(c != null) {
            c.setCourseApprovalStatus(CourseApprovalStatusEnum.PENDINGAPPROVAL);
            courseRepository.save(c);
        } else {
            throw new CourseNotFoundException("Course cannot be found.");
        }

    }

    @Override
    public void publishCourse(Long courseId) throws CourseNotFoundException {
        Course c = retrieveCourseById(courseId);
        if(c != null) {
            c.setCourseApprovalStatus(CourseApprovalStatusEnum.LIVE);
            courseRepository.save(c);
        } else {
            throw new CourseNotFoundException("Course cannot be found.");
        }

    }

    @Override
    public void approveCourse(Long courseId) throws CourseNotFoundException {
        Course c = retrieveCourseById(courseId);
        if(c != null) {
            c.setCourseApprovalStatus(CourseApprovalStatusEnum.ACCEPTED);
            courseRepository.save(c);
        } else {
            throw new CourseNotFoundException("Course cannot be found.");
        }
    }

    @Override
    public void rejectCourse(Long courseId, String rejectionReason) throws CourseNotFoundException{
        Course c = retrieveCourseById(courseId);
        if (c != null) {
            c.setCourseApprovalStatus(CourseApprovalStatusEnum.REJECTED);
            c.setRejectionReason(rejectionReason);
            courseRepository.save(c);
        } else {
            throw new CourseNotFoundException("Course cannot be found.");
        }

    }

    public List<ClassRun> retrieveClassRuns(Long courseId) throws CourseNotFoundException {
        Course c = retrieveCourseById(courseId);
        if (c != null) {
            return c.getClassRuns();
        } else {
            throw new CourseNotFoundException("Course cannot be found.");
        }
    }

    public void addClassRunToCourse(Long courseId, ClassRun classRun) throws CourseNotFoundException{
        Course c = retrieveCourseById(courseId);
        if (c != null) {

            classRun.setCourse(c);
            classRun = classRunRepository.save(classRun);

            c.getClassRuns();
            c.getClassRuns().add(classRun);
            courseRepository.save(c);
        } else {
            throw new CourseNotFoundException("Course cannot be found.");
        }
    }

    public boolean checkIfStudentIsEnrolledInACourse(Long studentId, Long courseId) throws CourseNotFoundException,
            LearnerNotFoundException{
        Course c = retrieveCourseById(courseId);
        Learner l = learnerService.getLearnerById(studentId);
        if (c != null) {
            if(l != null) {
                List<ClassRun> listOfClassRuns = c.getClassRuns();
                for(ClassRun classRun: listOfClassRuns) {
                    List<Learner> listOfLearners = classRun.getEnrolledLearners();
                    if (listOfLearners.contains(l)) {
                        return true;
                    }
                }

                return false;
            } else {
                throw new LearnerNotFoundException("Learner cannot be found.");
            }


        } else {
            throw new CourseNotFoundException("Course cannot be found.");
        }
    }



}
