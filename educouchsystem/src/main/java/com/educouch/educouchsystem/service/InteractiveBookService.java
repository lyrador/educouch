package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.InteractiveBook;
import com.educouch.educouchsystem.util.exception.InteractiveBookNotFoundException;

import java.util.List;

public interface InteractiveBookService {

    public InteractiveBook saveInteractiveBook(InteractiveBook interactiveBook);

    public List<InteractiveBook> getAllInteractiveBooks();

    public InteractiveBook getInteractiveBookById(Long interactiveBookId) throws InteractiveBookNotFoundException;

    public void deleteInteractiveBook(Long id);
}
