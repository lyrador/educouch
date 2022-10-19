package com.educouch.educouchsystem.service;


import com.educouch.educouchsystem.model.InteractiveChapter;
import com.educouch.educouchsystem.model.InteractivePage;
import com.educouch.educouchsystem.repository.InteractivePageRepository;
import com.educouch.educouchsystem.util.exception.InteractiveChapterNotFoundException;
import com.educouch.educouchsystem.util.exception.InteractivePageNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InteractivePageServiceImpl implements InteractivePageService{

    @Autowired
    private InteractivePageRepository interactivePageRepository;

    @Override
    public InteractivePage saveInteractivePage(InteractivePage interactivePage) {
        return interactivePageRepository.save(interactivePage);
    }

    @Override
    public List<InteractivePage> getAllInteractivePages() {
        return interactivePageRepository.findAll();
    }

    @Override
    public InteractivePage getInteractivePageById(Long interactivePageId) throws InteractivePageNotFoundException {
        Optional<InteractivePage> interactivePageOptional = interactivePageRepository.findById(interactivePageId);
        if (interactivePageOptional.isPresent()) {
            return interactivePageOptional.get();
        } else {
            throw new InteractivePageNotFoundException("Interactive Page cannot be found");
        }
    }

    @Override
    public void deleteInteractivePage(Long id) {
        interactivePageRepository.deleteById(id);
    }
}
