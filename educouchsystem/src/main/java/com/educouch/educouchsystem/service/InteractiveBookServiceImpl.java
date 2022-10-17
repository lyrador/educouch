package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.InteractiveBook;
import com.educouch.educouchsystem.repository.InteractiveBookRepository;
import com.educouch.educouchsystem.util.exception.InteractiveBookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InteractiveBookServiceImpl implements InteractiveBookService{

    @Autowired
    private InteractiveBookRepository interactiveBookRepository;

    @Override
    public InteractiveBook saveInteractiveBook(InteractiveBook interactiveBook) {
        return interactiveBookRepository.save(interactiveBook);
    }

    @Override
    public List<InteractiveBook> getAllInteractiveBooks() {
        return interactiveBookRepository.findAll();
    }

    @Override
    public InteractiveBook getInteractiveBookById(Long interactiveBookId) throws InteractiveBookNotFoundException {
        Optional<InteractiveBook> interactiveBookOptional = interactiveBookRepository.findById(interactiveBookId);
        if (interactiveBookOptional.isPresent()) {
            return interactiveBookOptional.get();
        } else {
            throw new InteractiveBookNotFoundException("Interactive Book cannot be found");
        }
    }

    @Override
    public void deleteInteractiveBook(Long id) {
        interactiveBookRepository.deleteById(id);
    }
}
