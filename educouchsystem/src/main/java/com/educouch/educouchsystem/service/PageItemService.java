package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.PageItem;
import com.educouch.educouchsystem.util.exception.PageItemNotFoundException;

import java.util.List;

public interface PageItemService {

    public PageItem savePageItem(PageItem pageItem);

    public List<PageItem> getAllPageItems();

    public PageItem getPageItemById (Long pageItemId) throws PageItemNotFoundException;

    public void deletePageItem(Long id);
}
