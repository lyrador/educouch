package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Course;
import com.educouch.educouchsystem.util.enumeration.CourseApprovalStatusEnum;
import com.educouch.educouchsystem.repository.CourseRepository;
import com.educouch.educouchsystem.util.exception.CourseNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService{

    @Autowired
    private CourseRepository courseRepository;

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



}
