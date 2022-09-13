package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Course;
import com.educouch.educouchsystem.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
