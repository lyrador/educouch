package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Gallery;
import com.educouch.educouchsystem.model.Item;
import com.educouch.educouchsystem.model.ItemOwned;
import com.educouch.educouchsystem.util.exception.*;

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

    public void hideItemOwned(Long learnerId, Long itemOwnedId) throws UnauthorizedActionException, ItemOwnedNotFoundException;

    public void updateLocation(Long learnerId, Long itemOwnedId, Integer x, Integer y) throws UnauthorizedActionException,
            ItemOwnedNotFoundException, LocationOccupiedException;

    public void unhideItemOwned(Long learnerId, Long itemOwnedId) throws UnauthorizedActionException, ItemOwnedNotFoundException;
}
