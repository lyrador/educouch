package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.model.InteractivePage;
import com.educouch.educouchsystem.model.PageItem;
import com.educouch.educouchsystem.service.InteractivePageService;
import com.educouch.educouchsystem.service.PageItemService;
import com.educouch.educouchsystem.util.exception.InteractivePageNotFoundException;
import com.educouch.educouchsystem.util.exception.PageItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/pageItem")
@CrossOrigin
public class PageItemController {

    @Autowired
    private PageItemService pageItemService;

    @Autowired
    private InteractivePageService interactivePageService;

    @PostMapping("/{interactivePageId}/pageItems")
    public ResponseEntity<PageItem> addPageItem(@PathVariable(value="interactivePageId") Long interactivePageId, @RequestBody PageItem pageItemRequest) {
        try {
            InteractivePage interactivePage = interactivePageService.getInteractivePageById(interactivePageId);
            pageItemRequest.setInteractivePage(interactivePage);
            if (interactivePage.getPageItems() != null) {
                interactivePage.getPageItems().add(pageItemRequest);
            } else {
                List<PageItem> pageItemList = new ArrayList<>();
                pageItemList.add(pageItemRequest);
                interactivePage.setPageItems(pageItemList);
            }
            PageItem pageItem = pageItemService.savePageItem(pageItemRequest);
            return new ResponseEntity<>(pageItem, HttpStatus.OK);
        } catch (InteractivePageNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/pageItems")
    public ResponseEntity<List<PageItem>> getAllPageItems() {
        List<PageItem> allPageItems = new ArrayList<>();
        if (!pageItemService.getAllPageItems().isEmpty()) {
            allPageItems = pageItemService.getAllPageItems();
            return new ResponseEntity<>(allPageItems, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/pageItems/{pageItemId}")
    public ResponseEntity<PageItem> deletePageItem(@PathVariable("pageItemId") Long pageItemId) {
        try {
            PageItem existingPageItem = pageItemService.getPageItemById(pageItemId);
            InteractivePage interactivePage = existingPageItem.getInteractivePage();
            interactivePage.getPageItems().remove(existingPageItem);
            existingPageItem.setInteractivePage(null);

            if (existingPageItem.getAttachment() != null) {
                existingPageItem.getAttachment().setPageItem(null);
                existingPageItem.setAttachment(null);
            }

            if (existingPageItem.getQuestion() != null) {
                existingPageItem.getQuestion().setPageItem(null);
                existingPageItem.setQuestion(null);
            }

            if (existingPageItem.getTextItem() != null) {
                existingPageItem.getTextItem().setPageItem(null);
                existingPageItem.setTextItem(null);
            }

            pageItemService.deletePageItem(existingPageItem.getPageItemId());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (PageItemNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/pageItems/{pageItemId}")
    public ResponseEntity<PageItem> updatePageItem (@RequestBody PageItem pageItem, @PathVariable("pageItemId") Long pageItemId) {
        try {
            PageItem existingPageItem = pageItemService.getPageItemById(pageItemId);
            existingPageItem.setPageItemXPosition(pageItem.getPageItemXPosition());
            existingPageItem.setPageItemYPosition(pageItem.getPageItemYPosition());

            pageItemService.savePageItem(existingPageItem);
            return new ResponseEntity<>(existingPageItem, HttpStatus.OK);
        } catch (PageItemNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<PageItem>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/interactivePage/{interactivePageId}/pageItem")
    public ResponseEntity<List<PageItem>> getAllPageItemsByInteractivePageId(@PathVariable("interactivePageId") Long interactivePageId) {
        try {
            InteractivePage interactivePage = interactivePageService.getInteractivePageById(interactivePageId);
            List<PageItem> pageItemList = new ArrayList<>();
            pageItemList.addAll(interactivePage.getPageItems());
            return new ResponseEntity<>(pageItemList, HttpStatus.OK);
        } catch (InteractivePageNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
