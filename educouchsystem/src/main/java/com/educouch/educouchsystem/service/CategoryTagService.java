package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.CategoryTag;
import com.educouch.educouchsystem.util.exception.CategoryTagNotFoundException;
import com.educouch.educouchsystem.util.exception.CourseNotFoundException;
import com.educouch.educouchsystem.util.exception.UnableToDeleteCategoryTagException;
import com.educouch.educouchsystem.util.exception.UnableToSaveCategoryTagException;

import java.util.List;

public interface CategoryTagService{
    public CategoryTag saveCategoryTag(CategoryTag categoryTag) throws UnableToSaveCategoryTagException;

    public CategoryTag editCategoryTag(CategoryTag categoryTag) throws UnableToSaveCategoryTagException;

    public void deleteCategoryTag(Long categoryTagId) throws UnableToDeleteCategoryTagException;

    public List<CategoryTag> retrieveAllCategoryTags();

    public CategoryTag retrieveCategoryTagById(Long categoryTagId) throws CategoryTagNotFoundException;

    public void editCourseTags(List<Long> categoryTagIds, Long courseId) throws CategoryTagNotFoundException,
            CourseNotFoundException;

    public List<CategoryTag> retrieveCategoryTagsByCourse(Long courseId) throws CourseNotFoundException;

    public void addTagToCourse(Long courseId, Long tagId) throws CourseNotFoundException, CategoryTagNotFoundException;

    public void removeTagFromCourse(Long courseId, Long tagId) throws CourseNotFoundException, CategoryTagNotFoundException;

    public List<CategoryTag> retrieveTagsNotInCourse(Long courseId) throws CourseNotFoundException;

    public Long retrieveTagIdFromName(String tagName);
}
