package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.dto.PurchaseItemDTO;
import com.educouch.educouchsystem.dto.UpdateLocationDTO;
import com.educouch.educouchsystem.model.EnhancementItem;
import com.educouch.educouchsystem.model.Item;
import com.educouch.educouchsystem.model.ItemOwned;
import com.educouch.educouchsystem.service.GalleryService;
import com.educouch.educouchsystem.util.enumeration.ItemSizeEnum;
import com.educouch.educouchsystem.util.exception.InsufficientTreePointBalanceException;
import com.educouch.educouchsystem.util.exception.LocationOccupiedException;
import com.educouch.educouchsystem.util.exception.UnauthorizedActionException;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @PostMapping("/createNewEnhancementItem")
    public String addNewEnhancementItem(@RequestBody EnhancementItem et) {
        galleryService.initiateEnhancementItem(et);
        return "Enhancement item has successfully been added.";
    }


    @PostMapping("/purchaseItem")
    @ResponseBody
    public ResponseEntity<String> purchaseItem(@RequestBody PurchaseItemDTO p){
        try {
            System.out.println(p.positionX);
            System.out.println(p.positionY);
            System.out.println(p.learnerId.toString());
            System.out.println(p.itemId.toString());
            // later change this to make it more than small
            ItemOwned newItem = new ItemOwned(p.positionX, p.positionY);
            ItemOwned item = galleryService.purchaseItem(new Long(p.learnerId), new Long(p.itemId), newItem);
            return new ResponseEntity<>("Successfully purchased the item", HttpStatus.OK);
        } catch(Exception ex1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex1.getMessage(), ex1);
        }
    }

    @GetMapping("/getAllItems")
    public List<Item> getAllItems() {
        List<Item> items = galleryService.getAllItems();
        return items;
    }

    @GetMapping("/getAllEnhancementItems")
    public List<EnhancementItem> getAllEnhancementItems() {
        List<EnhancementItem> listOfItems = galleryService.getAllEnhancementItems();
        return listOfItems;
    }

    @GetMapping("/retrieveItemOwnedByLearnerId")
    public List<ItemOwned> retrieveItemOwnedByLearner(@RequestParam Long learnerId) {
        return galleryService.retrieveItemsByLearnerId(learnerId);
    }

    @GetMapping("/hideItems")
    public ResponseEntity<String> hideItem(@RequestParam Long learnerId,@RequestParam Long itemOwnedId) {
        try {
            galleryService.hideItemOwned(learnerId, itemOwnedId);
            return new ResponseEntity<>("Successful action.", HttpStatus.OK);
        } catch(Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    @GetMapping("/unhideItems")
    public ResponseEntity<String> unhideItem(@RequestParam Long learnerId,@RequestParam Long itemOwnedId) {
        try {
            galleryService.unhideItemOwned(learnerId, itemOwnedId);
            return new ResponseEntity<>("Successful action.", HttpStatus.OK);
        } catch(Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    @PostMapping("/relocateItem")
    @ResponseBody
    public ResponseEntity<String> relocateItem(@RequestBody UpdateLocationDTO newItem){
        try {
            galleryService.updateLocation(newItem.getLearnerId(), newItem.getItemOwnedId(), newItem.getX(), newItem.getY());
            return new ResponseEntity<>("Successfully relocated the item.", HttpStatus.OK);
        } catch(Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    @GetMapping("/retrieveTreePointByLearnerId")
    public Integer retrieveTreePointByLearner(@RequestParam Long learnerId) {
        return galleryService.retrieveTreePointFromUserId(learnerId);
    }

    @GetMapping("/enhanceItem")
    @ResponseBody
    public ItemOwned enhanceItem(@RequestParam Long learnerId, @RequestParam Long itemOwnedId,
                                 @RequestParam Long enhancementId) {
        try{
            ItemOwned io = galleryService.enhanceItem(enhancementId, itemOwnedId, learnerId);
            return io;
        } catch(Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }



}
