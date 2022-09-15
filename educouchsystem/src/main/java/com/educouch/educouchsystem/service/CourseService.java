package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Course;
import com.educouch.educouchsystem.util.exception.CourseNotFoundException;

import java.util.List;

public interface CourseService {
    public Course saveCourse(Course course);
    public List<Course> getAllCourses();

    public Course getCourseById(Long courseId) throws CourseNotFoundException;

    public Course getCourseByCourseCode(String courseCode);
}
