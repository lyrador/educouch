package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Course;
import com.educouch.educouchsystem.util.exception.CourseNotFoundException;

import java.util.List;

public interface CourseService {
    public Course saveCourse(Course course);
    public List<Course> getAllCourses();

    public Course getCourseById(Long courseId) throws CourseNotFoundException;

    public Course getCourseByCourseCode(String courseCode);
    public Course retrieveCourseById(Long id);
    public void deleteCourse(Long id);

    public void submitCourseForApproval(Long courseId) throws CourseNotFoundException;

    public void approveCourse(Long courseId) throws CourseNotFoundException;

    public void rejectCourse(Long courseId, String rejectionReason) throws CourseNotFoundException;
}
