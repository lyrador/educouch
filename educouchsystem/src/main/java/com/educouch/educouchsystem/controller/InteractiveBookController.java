package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.model.*;
import com.educouch.educouchsystem.service.CourseService;
import com.educouch.educouchsystem.service.InteractiveBookService;
import com.educouch.educouchsystem.service.InteractiveChapterService;
import com.educouch.educouchsystem.util.exception.InteractiveBookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/interactiveBook")
@CrossOrigin
public class InteractiveBookController {

    @Autowired
    private InteractiveBookService interactiveBookService;

    //@Autowired
    //private InteractiveChapterService interactiveChapterService;

    @Autowired
    private CourseService courseService;

    @PostMapping("/{courseId}/interactiveBooks")
    public ResponseEntity<InteractiveBook> addInteractiveBook(@PathVariable(value="courseId") Long courseId, @RequestBody InteractiveBook interactiveBookRequest) {
        Course course  = courseService.retrieveCourseById(courseId);
        interactiveBookRequest.setCourse(course);
        if (course.getInteractiveBooks() != null) {
            course.getInteractiveBooks().add(interactiveBookRequest);
        } else {
            List<InteractiveBook> interactiveBookList = new ArrayList<>();
            interactiveBookList.add(interactiveBookRequest);
            course.setInteractiveBooks(interactiveBookList);
        }

        InteractiveBook interactiveBook = interactiveBookService.saveInteractiveBook(interactiveBookRequest);
        return new ResponseEntity<>(interactiveBook, HttpStatus.OK);
    }

    @GetMapping("/interactiveBooks")
    public ResponseEntity<List<InteractiveBook>> getAllInteractiveBooks() {
        List<InteractiveBook> allInteractiveBooks = new ArrayList<>();
        if (!interactiveBookService.getAllInteractiveBooks().isEmpty()) {
            allInteractiveBooks = interactiveBookService.getAllInteractiveBooks();
            return new ResponseEntity<>(allInteractiveBooks, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("{interactiveBookId}/interactiveBooks")
    public ResponseEntity<InteractiveBook> getInteractiveBookById(@PathVariable("interactiveBookId") Long interactiveBookId) {
        try {
            InteractiveBook interactiveBook = interactiveBookService.getInteractiveBookById(interactiveBookId);
            return new ResponseEntity<InteractiveBook>(interactiveBook, HttpStatus.OK);
        } catch (InteractiveBookNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/interactiveBooks/{interactiveBookId}")
    public ResponseEntity<InteractiveBook> deleteInteractiveBook(@PathVariable("interactiveBookId") Long interactiveBookId) {
        try {
            InteractiveBook existingInteractiveBook = interactiveBookService.getInteractiveBookById(interactiveBookId);
            Course course = existingInteractiveBook.getCourse();
            course.getInteractiveBooks().remove(existingInteractiveBook);
            existingInteractiveBook.setCourse(null);

            if (!existingInteractiveBook.getInteractiveChapters().isEmpty()) {
                List<InteractiveChapter> interactiveChapterList = existingInteractiveBook.getInteractiveChapters();
                for (InteractiveChapter interactiveChapter : interactiveChapterList) {
                    interactiveChapter.setInteractiveBook(null);
                    //interactiveChapterService.deleteInteractiveChapter(interactiveChapter.getInteractiveChapterId());
                }
            }
            existingInteractiveBook.setInteractiveChapters(null);
            interactiveBookService.deleteInteractiveBook(interactiveBookId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (InteractiveBookNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/interactiveBooks/{interactiveBookId}")
    public ResponseEntity<InteractiveBook> updateInteractiveBook (@RequestBody InteractiveBook interactiveBook, @PathVariable("interactiveBookId") Long interactiveBookId) {
        try {
            InteractiveBook existingInteractiveBook = interactiveBookService.getInteractiveBookById(interactiveBookId);
            existingInteractiveBook.setBookTitle(interactiveBook.getBookTitle());
            existingInteractiveBook.setBookMaxScore(interactiveBook.getBookMaxScore());
            existingInteractiveBook.setBookActualScore(interactiveBook.getBookActualScore());
            existingInteractiveBook.setCreationDate(interactiveBook.getCreationDate());

            interactiveBookService.saveInteractiveBook(existingInteractiveBook);
            return new ResponseEntity<>(existingInteractiveBook, HttpStatus.OK);
        } catch (InteractiveBookNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<InteractiveBook>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{courseId}/interactiveBooks")
    public ResponseEntity<List<InteractiveBook>> getAllInteractiveBooksByCourseId(@PathVariable("courseId") Long courseId) {
        Course course  = courseService.retrieveCourseById(courseId);
        List<InteractiveBook> interactiveBookList = new ArrayList<>();
        interactiveBookList.addAll(course.getInteractiveBooks());

        return new ResponseEntity<>(interactiveBookList, HttpStatus.OK);
    }

}
