package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.dto.PurchaseItemDTO;
import com.educouch.educouchsystem.model.Item;
import com.educouch.educouchsystem.model.ItemOwned;
import com.educouch.educouchsystem.service.GalleryService;
import com.educouch.educouchsystem.util.enumeration.ItemSizeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/treePoints")
@CrossOrigin
public class GalleryController {

    @Autowired
    private GalleryService galleryService;

    @PostMapping("/createNewItem")
    public String addNewItem(@RequestBody Item newItem) {
        galleryService.saveItem(newItem);
        return "Item has successfully been added.";
    }

    @PostMapping("/purchaseItem")
    @ResponseBody
    public String purchaseItem(@RequestBody PurchaseItemDTO p) throws Exception{
        try {
            System.out.println(p.positionX);
            System.out.println(p.positionY);
            System.out.println(p.learnerId.toString());
            System.out.println(p.itemId.toString());
            // later change this to make it more than small
            ItemOwned newItem = new ItemOwned(p.positionX, p.positionY, true, ItemSizeEnum.SMALL);
            ItemOwned item = galleryService.purchaseItem(new Long(p.learnerId), new Long(p.itemId), newItem);
            return "Successfully created an item";
        } catch(Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    @GetMapping("/getAllItems")
    public List<Item> getAllItems() {
        List<Item> items = galleryService.getAllItems();
        return items;
    }

    @GetMapping("/retrieveItemOwnedByLearnerId")
    public List<ItemOwned> retrieveItemOwnedByLearner(@RequestParam Long learnerId) {
        return galleryService.retrieveItemsByLearnerId(learnerId);
    }

    @GetMapping("/retrieveTreePointByLearnerId")
    public Integer retrieveTreePointByLearner(@RequestParam Long learnerId) {
        return galleryService.retrieveTreePointFromUserId(learnerId);
    }



}
