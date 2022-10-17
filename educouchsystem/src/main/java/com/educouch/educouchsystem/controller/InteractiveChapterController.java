package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.model.InteractiveBook;
import com.educouch.educouchsystem.model.InteractiveChapter;
import com.educouch.educouchsystem.model.InteractivePage;
import com.educouch.educouchsystem.service.InteractiveBookService;
import com.educouch.educouchsystem.service.InteractiveChapterService;
import com.educouch.educouchsystem.util.exception.InteractiveBookNotFoundException;
import com.educouch.educouchsystem.util.exception.InteractiveChapterNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/interactiveChapter")
@CrossOrigin
public class InteractiveChapterController {

    @Autowired
    private InteractiveChapterService interactiveChapterService;

    @Autowired
    private InteractiveBookService interactiveBookService;

    @PostMapping("/{interactiveBookId}/interactiveChapters")
    public ResponseEntity<InteractiveChapter> addInteractiveChapter(@PathVariable(value="interactiveBookId") Long interactiveBookId, @RequestBody InteractiveChapter interactiveChapterRequest) {
        try {
            InteractiveBook interactiveBook = interactiveBookService.getInteractiveBookById(interactiveBookId);
            interactiveChapterRequest.setInteractiveBook(interactiveBook);
            if(interactiveBook.getInteractiveChapters() != null) {
                interactiveBook.getInteractiveChapters().add(interactiveChapterRequest);
            } else {
                List<InteractiveChapter> interactiveChapterList = new ArrayList<>();
                interactiveChapterList.add(interactiveChapterRequest);
                interactiveBook.setInteractiveChapters(interactiveChapterList);
            }
            InteractiveChapter interactiveChapter = interactiveChapterService.saveInteractiveChapter(interactiveChapterRequest);
            return new ResponseEntity<>(interactiveChapter, HttpStatus.OK);
        } catch (InteractiveBookNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/interactiveChapters")
    public ResponseEntity<List<InteractiveChapter>> getAllInteractiveChapters() {
        List<InteractiveChapter> allInteractiveChapters = new ArrayList<>();
        if (!interactiveBookService.getAllInteractiveBooks().isEmpty()) {
            allInteractiveChapters = interactiveChapterService.getAllInteractiveChapters();
            return new ResponseEntity<>(allInteractiveChapters, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("{interactiveChapterId}/interactiveChapters")
    public ResponseEntity<InteractiveChapter> getInteractiveChapterById(@PathVariable("interactiveChapterId") Long interactiveChapterId) {
        try {
            InteractiveChapter interactiveChapter = interactiveChapterService.getInteractiveChapterById(interactiveChapterId);
            return new ResponseEntity<InteractiveChapter>(interactiveChapter, HttpStatus.OK);
        } catch (InteractiveChapterNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/interactiveChapters/{interactiveChapterId}")
    public ResponseEntity<InteractiveChapter> deleteInteractiveChapter(@PathVariable("interactiveChapterId") Long interactiveChapterId) {
        try {
            InteractiveChapter existingInteractiveChapter = interactiveChapterService.getInteractiveChapterById(interactiveChapterId);
            InteractiveBook interactiveBook = existingInteractiveChapter.getInteractiveBook();
            interactiveBook.getInteractiveChapters().remove(existingInteractiveChapter);
            existingInteractiveChapter.setInteractiveBook(null);

            if (!existingInteractiveChapter.getInteractivePages().isEmpty()) {
                List<InteractivePage> interactivePageList = existingInteractiveChapter.getInteractivePages();
                for (InteractivePage interactivePage : interactivePageList) {
                    interactivePage.setInteractiveChapter(null);
                    //interactiveChapterService.deleteInteractiveChapter(interactiveChapter.getInteractiveChapterId());
                }
            }
            existingInteractiveChapter.setInteractivePages(null);
            interactiveChapterService.deleteInteractiveChapter(interactiveChapterId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (InteractiveChapterNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/interactiveChapters/{interactiveChapterId}")
    public ResponseEntity<InteractiveChapter> updateInteractiveChapter (@RequestBody InteractiveChapter interactiveChapter, @PathVariable("interactiveChapterId") Long interactiveChapterId) {
        try {
            InteractiveChapter existingInteractiveChapter = interactiveChapterService.getInteractiveChapterById(interactiveChapterId);
            existingInteractiveChapter.setChapterTitle(interactiveChapter.getChapterTitle());
            existingInteractiveChapter.setChapterDescription(interactiveChapter.getChapterDescription());
            existingInteractiveChapter.setCreationDate(interactiveChapter.getCreationDate());
            existingInteractiveChapter.setInteractiveBook(interactiveChapter.getInteractiveBook());
            existingInteractiveChapter.setInteractivePages(interactiveChapter.getInteractivePages());

            interactiveChapterService.saveInteractiveChapter(existingInteractiveChapter);
            return new ResponseEntity<>(existingInteractiveChapter, HttpStatus.OK);
        }  catch (InteractiveChapterNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }  catch (NoSuchElementException ex) {
            return new ResponseEntity<InteractiveChapter>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{interactiveChapter}/interactiveChapters")
    public ResponseEntity<List<InteractiveChapter>> getAllInteractiveChaptersByInteractiveBookId(@PathVariable("interactiveBookId") Long interactiveBookId) {
        try {
            InteractiveBook interactiveBook = interactiveBookService.getInteractiveBookById(interactiveBookId);
            List<InteractiveChapter> interactiveChapterList = new ArrayList<>();
            interactiveChapterList.addAll(interactiveBook.getInteractiveChapters());
            return new ResponseEntity<>(interactiveChapterList, HttpStatus.OK);
        } catch (InteractiveBookNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
