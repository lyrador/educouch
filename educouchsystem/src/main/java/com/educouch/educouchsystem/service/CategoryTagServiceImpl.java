package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.CategoryTag;
import com.educouch.educouchsystem.model.Course;
import com.educouch.educouchsystem.repository.CategoryTagRepository;
import com.educouch.educouchsystem.repository.CourseRepository;
import com.educouch.educouchsystem.util.exception.CategoryTagNotFoundException;
import com.educouch.educouchsystem.util.exception.CourseNotFoundException;
import com.educouch.educouchsystem.util.exception.UnableToDeleteCategoryTagException;
import com.educouch.educouchsystem.util.exception.UnableToSaveCategoryTagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class CategoryTagServiceImpl implements CategoryTagService{

    @Autowired
    private CategoryTagRepository categoryTagRepository;

    @Autowired
    private CourseService courseService;

    @Override
    public CategoryTag saveCategoryTag(CategoryTag categoryTag) throws UnableToSaveCategoryTagException {
        try{
            return categoryTagRepository.save(categoryTag);
        } catch(Exception e) {
            throw new UnableToSaveCategoryTagException("Unable to save category tag.");
        }
    }

    @Override
    public CategoryTag editCategoryTag(CategoryTag categoryTag) throws UnableToSaveCategoryTagException {
        try {
            CategoryTag c = categoryTagRepository.getReferenceById(categoryTag.getCategoryTagId());
            c.setTagName(categoryTag.getTagName());
            categoryTagRepository.save(c);
            return c;
        } catch(Exception ex) {
            throw new UnableToSaveCategoryTagException("Unable to update category tag. ");
        }
    }

    @Override
    public void deleteCategoryTag(Long categoryTagId) throws UnableToDeleteCategoryTagException {
        try{
            CategoryTag c = categoryTagRepository.getReferenceById(categoryTagId);
            List<Course> listOfAllCourses = courseService.getAllCourses();

            for(Course course: listOfAllCourses) {
                List<CategoryTag> listOfCategoryTags = course.getCategoryTags();

                if(listOfCategoryTags.contains(c)) {
                    throw new UnableToDeleteCategoryTagException("Unable to delete category tag because there" +
                            " are course(s) associated with it. ");
                }
            }

            categoryTagRepository.delete(c);
        } catch(Exception ex) {
            throw new UnableToDeleteCategoryTagException("Unable to delete category tag due to unknown error.");
        }
    }

    @Override
    public List<CategoryTag> retrieveAllCategoryTags() {
        return categoryTagRepository.findAll();
    }

    @Override
    public CategoryTag retrieveCategoryTagById(Long categoryTagId) throws CategoryTagNotFoundException {
        Optional<CategoryTag> cOpt = categoryTagRepository.findById(categoryTagId);
        if(cOpt.isPresent()) {
            return cOpt.get();
        } else {
            throw new CategoryTagNotFoundException("Unable to find category tag.");
        }
    }

    @Override
    public void editCourseTags(List<Long> categoryTagIds, Long courseId) throws CategoryTagNotFoundException,
            CourseNotFoundException {
        Course c = courseService.getCourseById(courseId);
        if(c != null) {
            List<CategoryTag> categoryTags = new ArrayList<>();

            for(Long id: categoryTagIds) {
                CategoryTag cat = retrieveCategoryTagById(id);
                categoryTags.add(cat);
            }

            c.setCategoryTags(categoryTags);
            courseService.saveCourse(c);
        } else {
            throw new CourseNotFoundException("Course cannot be found.");
        }
    }

    @Override
    public List<CategoryTag> retrieveCategoryTagsByCourse(Long courseId) throws CourseNotFoundException {
        Course c = courseService.getCourseById(courseId);
        if(c!= null) {
            return c.getCategoryTags();
        } else {
            throw new CourseNotFoundException("Course cannot be found.");
        }
    }


}
