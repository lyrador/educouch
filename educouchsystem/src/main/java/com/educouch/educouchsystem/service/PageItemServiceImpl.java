package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.PageItem;
import com.educouch.educouchsystem.repository.PageItemRepository;
import com.educouch.educouchsystem.util.exception.PageItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PageItemServiceImpl implements PageItemService{

    @Autowired
    private PageItemRepository pageItemRepository;

    @Override
    public PageItem savePageItem(PageItem pageItem) {
        return pageItemRepository.save(pageItem);
    }

    @Override
    public List<PageItem> getAllPageItems() {
        return pageItemRepository.findAll();
    }

    @Override
    public PageItem getPageItemById(Long pageItemId) throws PageItemNotFoundException {
        Optional<PageItem> pageItemOptional = pageItemRepository.findById(pageItemId);
        if (pageItemOptional.isPresent()) {
            return pageItemOptional.get();
        } else {
            throw new PageItemNotFoundException("Page Item cannot be found");
        }
    }

    @Override
    public void deletePageItem(Long id) {
        pageItemRepository.deleteById(id);
    }
}
