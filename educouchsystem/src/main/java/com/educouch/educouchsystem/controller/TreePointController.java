package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.model.Item;
import com.educouch.educouchsystem.repository.GalleryRepository;
import com.educouch.educouchsystem.repository.ItemOwnedRepository;
import com.educouch.educouchsystem.repository.ItemRepository;
import com.educouch.educouchsystem.service.GalleryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/treePoints")
@CrossOrigin
public class TreePointController {

    @Autowired
    private GalleryService galleryService;

    @PostMapping("/createNewItem")
    public String addNewItem(@RequestBody Item newItem) {
        galleryService.saveItem(newItem);
        return "Item has successfully been added.";
    }

    @GetMapping("/getAllItems")
    public List<Item> getAllItems() {
        List<Item> items = galleryService.getAllItems();
        return items;
    }



}
