package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.dto.EditCourseTagsDTO;
import com.educouch.educouchsystem.model.CategoryTag;
import com.educouch.educouchsystem.service.CategoryTagService;
import com.educouch.educouchsystem.util.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/categoryTag")
@CrossOrigin
public class CategoryTagController {

    @Autowired
    private CategoryTagService categoryTagService;

    @GetMapping("/createNewCategoryTag")
    @ResponseBody
    public String createNewCategoryTag(@RequestParam String categoryTagName) {
        CategoryTag c = new CategoryTag(categoryTagName);
        try {
            categoryTagService.saveCategoryTag(c);
            return "Successfully creating a new category tag. ";
        } catch(UnableToSaveCategoryTagException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to save category tag.", ex);
        }
    }

    @PostMapping("/editCategoryTag")
    public String editCategoryTag(@RequestBody CategoryTag categoryTag) {
        try{
            categoryTagService.editCategoryTag(categoryTag);
            return "Successfully edit category tag.";
        }catch(UnableToSaveCategoryTagException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to edit category tag.", ex);
        }
    }
    @PostMapping("/editCourseTags")
    private String editCourseTags(@RequestBody EditCourseTagsDTO editCourseModel) {
        Long courseId = editCourseModel.getCourseId();
        List<String> tagIdsInString = editCourseModel.getTagIds();
        List<Long> tagIds = new ArrayList<>();

        for(String id: tagIdsInString){
            Long id_long = new Long(id);
            tagIds.add(id_long);
        }
        try {
            categoryTagService.editCourseTags(tagIds, courseId);
            return "Successfully edited course tags.";
        } catch(CourseNotFoundException | CategoryTagNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    @DeleteMapping("/deleteCategoryTag/{categoryTagId}")
    public String deleteCategoryTag(@PathVariable String categoryTagId) {
        Long tagId = new Long(categoryTagId);

        try{
            categoryTagService.deleteCategoryTag(tagId);
            return "Successfully deleted category tag.";
        } catch(UnableToDeleteCategoryTagException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage(), ex);
        }
    }

    @GetMapping("/getAll")
    public List<CategoryTag> retrieveListOfCategoryTags() {
        List<CategoryTag> categoryTags = categoryTagService.retrieveAllCategoryTags();
        return categoryTags;
    }

    @GetMapping("/getCategoryTagNotInCourse")
    @ResponseBody
    public List<CategoryTag> retrieveTagsNotInCourse(@RequestParam String courseId) {
        try {
            List<CategoryTag> categoryTags = categoryTagService.retrieveTagsNotInCourse(new Long(courseId));
            return categoryTags;
        } catch(CourseNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Course cannot be found.", ex);
        }
    }


    @GetMapping("/getCourseCategoryTag/{courseId}")
    public List<CategoryTag> retrieveListOfCategoryTags(@PathVariable String courseId) {
        Long courseIdInLong = new Long(courseId);
        try {
            List<CategoryTag> categoryTags = categoryTagService.retrieveCategoryTagsByCourse(courseIdInLong);
            return categoryTags;
        } catch(CourseNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Course cannot be found.", ex);
        }
    }

    @GetMapping("/addTagToCourse")
    @ResponseBody
    public String addTagToCourse(@RequestParam Long courseId, @RequestParam String tagId) {
        try{
            categoryTagService.addTagToCourse(new Long(courseId), new Long(tagId));
            return "Successfully added category tag to course.";
        }catch(CourseNotFoundException | CategoryTagNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Course cannot be found.", ex);
        }

    }

    @GetMapping("/removeTagFromCourse")
    @ResponseBody
    public String removeTagFromCourse(@RequestParam Long courseId, @RequestParam String tagId) {
        try{
            categoryTagService.removeTagFromCourse(new Long(courseId), new Long(tagId));
            return "Successfully removed category tag to course.";
        }catch(CourseNotFoundException | CategoryTagNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Course cannot be found.", ex);
        }

    }







}
