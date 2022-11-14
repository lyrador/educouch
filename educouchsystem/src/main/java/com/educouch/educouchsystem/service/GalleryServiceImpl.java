package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Gallery;
import com.educouch.educouchsystem.model.Item;
import com.educouch.educouchsystem.model.ItemOwned;
import com.educouch.educouchsystem.repository.GalleryRepository;
import com.educouch.educouchsystem.repository.ItemOwnedRepository;
import com.educouch.educouchsystem.repository.ItemRepository;
import com.educouch.educouchsystem.util.exception.ItemNotFoundException;
import com.educouch.educouchsystem.util.exception.LocationOccupiedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.stream.Location;
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
    public ItemOwned purchaseItem(Long galleryId, Long itemId, ItemOwned newItem) throws LocationOccupiedException {
        Gallery gallery = galleryRepository.getReferenceById(galleryId);
        Item item = itemRepository.getReferenceById(itemId);

        // need to add a check on whether there are conflicting coordinates
        Integer currX = newItem.getPositionX();
        Integer currY = newItem.getPositionY();
        List<ItemOwned> listOfItemsOwned = gallery.getItemsOwned();
        for(ItemOwned io :listOfItemsOwned) {
            if(io.getPositionX() == currX && io.getPositionY() == currY) {
                throw new LocationOccupiedException("Current coordinates has been taken by another item.");
            }
        }

        // adding reference to the catalogue
        newItem.setItem(item);
        newItem = itemOwnedRepository.save(newItem);

        // add the new item to the gallery
        gallery.getItemsOwned().add(newItem);
        galleryRepository.save(gallery);

        return newItem;

    }


}
