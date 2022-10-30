package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.dto.InteractiveBookRequestDTO;
import com.educouch.educouchsystem.model.*;
import com.educouch.educouchsystem.service.AttachmentService;
import com.educouch.educouchsystem.service.CourseService;
import com.educouch.educouchsystem.service.InteractiveBookService;
import com.educouch.educouchsystem.service.InteractiveChapterService;
import com.educouch.educouchsystem.util.exception.InteractiveBookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    private AttachmentService attachmentService;

    @PostMapping("/{courseId}/interactiveBooks")
    public ResponseEntity<InteractiveBook> addInteractiveBook(@PathVariable(value="courseId") Long courseId, @RequestBody InteractiveBookRequestDTO interactiveBookRequest) throws FileNotFoundException {
        Course course  = courseService.retrieveCourseById(courseId);
        InteractiveBook newInteractiveBook = new InteractiveBook();
        newInteractiveBook.setCourse(course);
        newInteractiveBook.setBookTitle(interactiveBookRequest.getBookTitle());
        newInteractiveBook.setBookMaxScore(interactiveBookRequest.getBookMaxScore());
        newInteractiveBook.setCreationDate(new Date());
        newInteractiveBook.setBookActualScore(0.0);

        newInteractiveBook.setAttachment(attachmentService.getAttachment(interactiveBookRequest.getAttachmentId()));

        if (course.getInteractiveBooks() != null) {
            course.getInteractiveBooks().add(newInteractiveBook);
        } else {
            List<InteractiveBook> interactiveBookList = new ArrayList<>();
            interactiveBookList.add(newInteractiveBook);
            course.setInteractiveBooks(interactiveBookList);
        }

        InteractiveBook interactiveBook = interactiveBookService.saveInteractiveBook(newInteractiveBook);
        return new ResponseEntity<>(interactiveBook, HttpStatus.OK);
    }

    @GetMapping("/interactiveBooks")
    public ResponseEntity<List<InteractiveBook>> getAllInteractiveBooks() {
        List<InteractiveBook> allInteractiveBooks = new ArrayList<>();
        if (!interactiveBookService.getAllInteractiveBooks().isEmpty()) {
            allInteractiveBooks = interactiveBookService.getAllInteractiveBooks();

            for (InteractiveBook interactiveBook : allInteractiveBooks) {
                interactiveBook.getCourse().setFolders(null);
                interactiveBook.getCourse().setClassRuns(null);
                interactiveBook.getCourse().setAssessments(null);
                interactiveBook.getCourse().setOrganisation(null);

            }

            return new ResponseEntity<>(allInteractiveBooks, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("{interactiveBookId}/interactiveBooks")
    public ResponseEntity<InteractiveBook> getInteractiveBookById(@PathVariable("interactiveBookId") Long interactiveBookId) {
        try {
            InteractiveBook interactiveBook = interactiveBookService.getInteractiveBookById(interactiveBookId);

            interactiveBook.getCourse().setFolders(null);
            interactiveBook.getCourse().setClassRuns(null);
            interactiveBook.getCourse().setAssessments(null);
            interactiveBook.getCourse().setOrganisation(null);

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

//            if (!existingInteractiveBook.getInteractiveChapters().isEmpty()) {
//                List<InteractiveChapter> interactiveChapterList = existingInteractiveBook.getInteractiveChapters();
//                for (InteractiveChapter interactiveChapter : interactiveChapterList) {
//                    interactiveChapter.setInteractiveBook(null);
//                    //interactiveChapterService.deleteInteractiveChapter(interactiveChapter.getInteractiveChapterId());
//                }
//            }
//            existingInteractiveBook.setInteractiveChapters(null);
            interactiveBookService.deleteInteractiveBook(interactiveBookId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (InteractiveBookNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/interactiveBooks/{interactiveBookId}")
    public ResponseEntity<InteractiveBook> updateInteractiveBook (@RequestBody InteractiveBookRequestDTO interactiveBook, @PathVariable("interactiveBookId") Long interactiveBookId) {
        try {
            InteractiveBook existingInteractiveBook = interactiveBookService.getInteractiveBookById(interactiveBookId);
            existingInteractiveBook.setBookTitle(interactiveBook.getBookTitle());
            existingInteractiveBook.setBookMaxScore(interactiveBook.getBookMaxScore());
           // existingInteractiveBook.setBookActualScore(interactiveBook.getBookActualScore());
           // existingInteractiveBook.setCreationDate(interactiveBook.getCreationDate());

            if (interactiveBook.getAttachmentId() != null) {
                    Long attachmentIdToDelete = existingInteractiveBook.getAttachment().getAttachmentId();
                    existingInteractiveBook.setAttachment(null);
                    attachmentService.deleteAttachment(attachmentIdToDelete);
                    existingInteractiveBook.setAttachment(attachmentService.getAttachment(interactiveBook.getAttachmentId()));
            }

            interactiveBookService.saveInteractiveBook(existingInteractiveBook);
            return new ResponseEntity<>(existingInteractiveBook, HttpStatus.OK);
        } catch (InteractiveBookNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<InteractiveBook>(HttpStatus.NOT_FOUND);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("course/{courseId}/interactiveBooks")
    public ResponseEntity<List<InteractiveBook>> getAllInteractiveBooksByCourseId(@PathVariable("courseId") Long courseId) {
        Course course  = courseService.retrieveCourseById(courseId);
        List<InteractiveBook> interactiveBookList = new ArrayList<>();
        interactiveBookList.addAll(course.getInteractiveBooks());

        for (InteractiveBook interactiveBook : interactiveBookList) {
            interactiveBook.getCourse().setFolders(null);
            interactiveBook.getCourse().setClassRuns(null);
            interactiveBook.getCourse().setAssessments(null);
            interactiveBook.getCourse().setOrganisation(null);
        }

        return new ResponseEntity<>(interactiveBookList, HttpStatus.OK);
    }

}
