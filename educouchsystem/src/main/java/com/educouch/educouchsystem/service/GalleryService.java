package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Gallery;
import com.educouch.educouchsystem.model.Item;
import com.educouch.educouchsystem.model.ItemOwned;
import com.educouch.educouchsystem.util.exception.InsufficientTreePointBalanceException;
import com.educouch.educouchsystem.util.exception.ItemNotFoundException;
import com.educouch.educouchsystem.util.exception.LocationOccupiedException;

import java.util.List;

public interface GalleryService {

    public Item saveItem(Item item);

    public List<Item> getAllItems();

    public Item getItembyId(Long itemId) throws ItemNotFoundException;

    public void deleteItem(Long itemId);

    public ItemOwned purchaseItem(Long learnerId, Long itemId, ItemOwned newItem) throws
            LocationOccupiedException, InsufficientTreePointBalanceException;

    public Gallery saveGallery(Gallery gallery);

    public Integer retrieveTreePointFromUserId(Long learnerId);

    public List<ItemOwned> retrieveItemsByLearnerId(Long learnerId);
}
