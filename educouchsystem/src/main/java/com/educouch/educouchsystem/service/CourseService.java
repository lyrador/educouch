package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Course;

import java.util.List;

public interface CourseService {
    public Course saveCourse(Course course);
    public List<Course> getAllCourses();
    public Course retrieveCourseById(Long id);
    public void deleteCourse(Long id);
}
