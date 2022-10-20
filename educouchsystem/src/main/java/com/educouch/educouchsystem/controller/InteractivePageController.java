package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.model.*;
import com.educouch.educouchsystem.service.InteractiveChapterService;
import com.educouch.educouchsystem.service.InteractivePageService;
import com.educouch.educouchsystem.util.exception.InteractiveBookNotFoundException;
import com.educouch.educouchsystem.util.exception.InteractiveChapterNotFoundException;
import com.educouch.educouchsystem.util.exception.InteractivePageNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/interactivePage")
@CrossOrigin
public class InteractivePageController {

    @Autowired
    private InteractivePageService interactivePageService;

    @Autowired
    private InteractiveChapterService interactiveChapterService;

    @PostMapping("/{interactiveChapterId}/interactivePages")
    public ResponseEntity<InteractivePage> addInteractivePage(@PathVariable(value="interactiveChapterId") Long interactiveChapterId, @RequestBody InteractivePage interactivePageRequest) {
        try {
            InteractiveChapter interactiveChapter = interactiveChapterService.getInteractiveChapterById(interactiveChapterId);
            interactivePageRequest.setInteractiveChapter(interactiveChapter);
            if(interactiveChapter.getInteractivePages() != null) {
                interactiveChapter.getInteractivePages().add(interactivePageRequest);
            } else {
                List<InteractivePage> interactivePageList = new ArrayList<>();
                interactivePageList.add(interactivePageRequest);
                interactiveChapter.setInteractivePages(interactivePageList);
            }
            InteractivePage interactivePage = interactivePageService.saveInteractivePage(interactivePageRequest);
            return new ResponseEntity<>(interactivePage, HttpStatus.OK);
        } catch (InteractiveChapterNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/interactivePages")
    public ResponseEntity<List<InteractivePage>> getAllInteractiveChapters() {
        List<InteractivePage> allInteractivePages = new ArrayList<>();
        if (!interactivePageService.getAllInteractivePages().isEmpty()) {
            allInteractivePages = interactivePageService.getAllInteractivePages();
            return new ResponseEntity<>(allInteractivePages, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/interactivePages/{interactivePageId}")
    public ResponseEntity<InteractivePage> deleteInteractivePage(@PathVariable("interactivePageId") Long interactivePageId) {
        try {
            InteractivePage existingInteractivePage = interactivePageService.getInteractivePageById(interactivePageId);
            InteractiveChapter interactiveChapter = existingInteractivePage.getInteractiveChapter();
            interactiveChapter.getInteractivePages().remove(existingInteractivePage);
            existingInteractivePage.setInteractiveChapter(null);

            if (!existingInteractivePage.getPageItems().isEmpty()) {
                List<PageItem> pageItemList = existingInteractivePage.getPageItems();
                for (PageItem pageItem : pageItemList) {
                    pageItem.setInteractivePage(null);
                }
            }
            existingInteractivePage.setPageItems(null);

            interactivePageService.deleteInteractivePage(interactivePageId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (InteractivePageNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/interactivePages/{interactivePageId}")
    public ResponseEntity<InteractivePage> updateInteractivePage (@RequestBody InteractivePage interactivePage, @PathVariable("interactivePageId") Long interactivePageId) {
        try {
            InteractivePage existingInteractivePage = interactivePageService.getInteractivePageById(interactivePageId);
            existingInteractivePage.setPageDescription(interactivePage.getPageDescription());
            existingInteractivePage.setPageNumber(interactivePage.getPageNumber());
           // existingInteractivePage.setPageQuestions(interactivePage.getPageQuestions());
           // existingInteractivePage.setAttachments(interactivePage.getAttachments());

            interactivePageService.saveInteractivePage(existingInteractivePage);
            return new ResponseEntity<>(existingInteractivePage, HttpStatus.OK);
        }  catch (InteractivePageNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<InteractivePage>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/interactiveChapter/{interactiveChapterId}/interactivePages")
    public ResponseEntity<List<InteractivePage>> getAllInteractivePagesByInteractiveChapterId(@PathVariable("interactiveChapterId") Long interactiveChapterId) {
        try {
            InteractiveChapter interactiveChapter = interactiveChapterService.getInteractiveChapterById(interactiveChapterId);
            List<InteractivePage> interactivePageList = new ArrayList<>();
            interactivePageList.addAll(interactiveChapter.getInteractivePages());
            return new ResponseEntity<>(interactivePageList, HttpStatus.OK);
        } catch (InteractiveChapterNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


 }
