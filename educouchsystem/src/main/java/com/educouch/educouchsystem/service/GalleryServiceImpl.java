package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Gallery;
import com.educouch.educouchsystem.model.Item;
import com.educouch.educouchsystem.model.ItemOwned;
import com.educouch.educouchsystem.model.Learner;
import com.educouch.educouchsystem.repository.GalleryRepository;
import com.educouch.educouchsystem.repository.ItemOwnedRepository;
import com.educouch.educouchsystem.repository.ItemRepository;
import com.educouch.educouchsystem.util.enumeration.ItemSizeEnum;
import com.educouch.educouchsystem.util.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.stream.Location;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GalleryServiceImpl implements GalleryService {

    @Autowired
    private GalleryRepository galleryRepository;

    @Autowired
    private ItemOwnedRepository itemOwnedRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private LearnerService learnerService;

    // item first
    @Override
    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public Item getItembyId(Long itemId) throws ItemNotFoundException {
        Optional<Item> itemOptional = itemRepository.findById(itemId);
        if(itemOptional.isPresent()) {
            return itemOptional.get();
        } else {
            throw new ItemNotFoundException("Item cannot be found.");
        }
    }

    @Override
    public void deleteItem(Long itemId) {
        itemRepository.deleteById(itemId);
    }

    // learner buy a new item
    @Override
    public ItemOwned purchaseItem(Long learnerId, Long itemId, ItemOwned newItem) throws LocationOccupiedException, InsufficientTreePointBalanceException {
        Learner learner = learnerService.getLearnerById(learnerId);

        Gallery gallery = learner.getGallery();

        Item item = itemRepository.getReferenceById(itemId);

        Boolean locationIsAvailable = checkIfLocationIsAvailable(gallery.getGalleryId(), newItem);

        if(locationIsAvailable) {
            // deduct tree points from the learner

            if(learnerBalanceIsEnough(newItem, item, learner)){
                // adding reference to the catalogue
                newItem.setItem(item);
                newItem = itemOwnedRepository.save(newItem);

                // add the new item to the gallery
                gallery.getItemsOwned().add(newItem);
                galleryRepository.save(gallery);

                // reducing learner points
                learner.setTreePoints(learner.getTreePoints() - item.getPrice());
                learnerService.saveLearnerWithoutGallery(learner);
                return newItem;
            } else {
                throw new InsufficientTreePointBalanceException("Insufficient tree points");
            }


        } else {
//            List<ItemOwned> itemsOwned = gallery.getItemsOwned();
//            ItemOwned replacedItem = null;
//            for(ItemOwned itemOwned: itemsOwned) {
//                Integer itemOwnedX = itemOwned.getPositionX();
//                Integer itemOwnedY = itemOwned.getPositionY();
//                if(itemOwnedX == newItem.getPositionX() && itemOwnedY == newItem.getPositionY()) {
//                    replacedItem = itemOwned;
//                }
//
//            }
//
//            // proceed to updating the replacedItem with the new item
//
//            replacedItem.setHorizontal(newItem.getHorizontal());
//            replacedItem.setHidden(newItem.getHidden());
//            replacedItem.setSize(newItem.getSize());
//            replacedItem.setItem(newItem.getItem());
//
//            replacedItem = itemOwnedRepository.save(replacedItem);
//
//            learner.setTreePoints(learner.getTreePoints() - item.getPrice());
//            learnerService.saveLearner(learner);
//
//            return replacedItem;
            throw new LocationOccupiedException("Location has been occupied.");
        }



    }

    private boolean learnerBalanceIsEnough(ItemOwned newItem, Item item, Learner learner) {
        Integer price = 0;
        if(newItem.getSize() == ItemSizeEnum.SMALL) {
            price = item.getPrice();
        } else if(newItem.getSize() == ItemSizeEnum.MEDIUM) {
            price = item.getPrice() + 10;
        } else {
            price = item.getPrice() + 20;
        }

        if(learner.getTreePoints() < price) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void hideItemOwned(Long learnerId, Long itemOwnedId) throws UnauthorizedActionException, ItemOwnedNotFoundException {

        ItemOwned itemOwned = itemOwnedRepository.getReferenceById(itemOwnedId);

        if(itemOwned == null) {
            throw new ItemOwnedNotFoundException("Item cannot be found.");
        }
        // check if the learner owned the item
        Learner learner = learnerService.getLearnerById(learnerId);
        Gallery gallery = learner.getGallery();
        if(!gallery.getItemsOwned().contains(itemOwned)) {
            throw new UnauthorizedActionException("Incorrect login credentials.");
        }

        itemOwned.setHidden(true);
        itemOwnedRepository.save(itemOwned);
    }

    @Override
    public void unhideItemOwned(Long learnerId, Long itemOwnedId) throws UnauthorizedActionException, ItemOwnedNotFoundException {

        ItemOwned itemOwned = itemOwnedRepository.getReferenceById(itemOwnedId);

        if(itemOwned == null) {
            throw new ItemOwnedNotFoundException("Item cannot be found.");
        }
        // check if the learner owned the item
        Learner learner = learnerService.getLearnerById(learnerId);
        Gallery gallery = learner.getGallery();
        if(!gallery.getItemsOwned().contains(itemOwned)) {
            throw new UnauthorizedActionException("Incorrect login credentials.");
        }

        itemOwned.setHidden(false);
        itemOwnedRepository.save(itemOwned);
    }

    @Override
    public void updateLocation(Long learnerId, Long itemOwnedId, Integer x, Integer y) throws UnauthorizedActionException,
            ItemOwnedNotFoundException, LocationOccupiedException {

        ItemOwned itemOwned = itemOwnedRepository.getReferenceById(itemOwnedId);
        itemOwned.setPositionX(x);
        itemOwned.setPositionY(y);


        if(itemOwned == null) {

            throw new ItemOwnedNotFoundException("Item cannot be found.");
        }

        // check if the learner owned the item

        Learner learner = learnerService.getLearnerById(learnerId);

        Gallery gallery = learner.getGallery();
        if(!gallery.getItemsOwned().contains(itemOwned)) {

            throw new UnauthorizedActionException("Incorrect login credentials.");
        }

        Boolean isValid = checkIfLocationIsAvailable(learner.getGallery().getGalleryId(), itemOwned);


        itemOwnedRepository.save(itemOwned);



    }



    public List<ItemOwned> retrieveItemsByLearnerId(Long learnerId) {
        Gallery gallery = learnerService.getLearnerById(learnerId).getGallery();
        List<ItemOwned> listOfItemsOwned = gallery.getItemsOwned();
        return listOfItemsOwned;
    }

    public Boolean checkIfLocationIsAvailable(Long galleryId, ItemOwned newItem) {
        Gallery gallery = galleryRepository.getReferenceById(galleryId);

        // find restricted area
        Integer currX = newItem.getPositionX();
        Integer currY = newItem.getPositionY();
        List<Integer> restrictedX = new ArrayList<>();
        List<Integer> restrictedY = new ArrayList<>();
        restrictedX.add(currX);
        restrictedY.add(currY);
        if(newItem.getSize() == ItemSizeEnum.MEDIUM) {
            restrictedX.add(currX + 1);
            restrictedY.add(currY + 1);
        } else if(newItem.getSize() == ItemSizeEnum.LARGE) {
            restrictedX.add(currX + 1);
            restrictedY.add(currY + 1);
            restrictedX.add(currX + 2);
            restrictedY.add(currY + 2);
        }
        List<Integer> blockedX = new ArrayList<>();
        List<Integer> blockedY = new ArrayList<>();
        List<ItemOwned> listOfItemsOwned = gallery.getItemsOwned();
        for(ItemOwned io :listOfItemsOwned) {
            // small -> as long as they are not in the restricted area
            blockedX = new ArrayList<>();
            blockedY = new ArrayList<>();

            if(io.getSize() == ItemSizeEnum.SMALL) {
                if(restrictedX.contains(io.getPositionX()) && restrictedY.contains(io.getPositionY())) {
                    return false;
                } else {
                    return true;
                }
            } else if(io.getSize() == ItemSizeEnum.MEDIUM) {
                blockedX.add(io.getPositionX());
                blockedX.add(io.getPositionX() + 1);


                blockedY.add(io.getPositionY());
                blockedY.add(io.getPositionY() + 1);


            } else if(io.getSize() == ItemSizeEnum.LARGE) {
                blockedX.add(io.getPositionX());
                blockedX.add(io.getPositionX() + 1);
                blockedX.add(io.getPositionX() + 2);

                blockedY.add(io.getPositionY());
                blockedY.add(io.getPositionY() + 1);
                blockedY.add(io.getPositionY() + 2);
            }

            Boolean xOverlap = false;
            Boolean yOverlap = false;

            for(Integer newItemX: restrictedX) {
                if(blockedX.contains(newItemX)) {
                    xOverlap = true;
                }
            }

            for(Integer newItemY: restrictedY) {
                if(blockedY.contains(newItemY)) {
                    yOverlap = true;
                }
            }

            if(xOverlap && yOverlap) {
                return false;
            }
        }
        return true;
    }


    @Override
    public Gallery saveGallery(Gallery gallery) {
        return galleryRepository.save(gallery);
    }

    public Integer retrieveTreePointFromUserId(Long learnerId) {
        Learner learner = learnerService.getLearnerById(learnerId);
        return learner.getTreePoints();
    }



}
