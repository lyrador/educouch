package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.InteractiveBook;
import com.educouch.educouchsystem.model.InteractiveChapter;
import com.educouch.educouchsystem.repository.InteractiveChapterRepository;
import com.educouch.educouchsystem.util.exception.InteractiveBookNotFoundException;
import com.educouch.educouchsystem.util.exception.InteractiveChapterNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InteractiveChapterServiceImpl implements InteractiveChapterService{

    @Autowired
    private InteractiveChapterRepository interactiveChapterRepository;

    @Override
    public InteractiveChapter saveInteractiveChapter(InteractiveChapter interactiveChapter) {
        if (interactiveChapter.getInteractiveBook() != null) {
            if (interactiveChapter.getInteractiveBook().getInteractiveChapters() != null) {
                interactiveChapter.setChapterIndex(interactiveChapter.getInteractiveBook().getInteractiveChapters().size());
           }
        }
        return interactiveChapterRepository.save(interactiveChapter);
    }

    @Override
    public List<InteractiveChapter> getAllInteractiveChapters() {
        return interactiveChapterRepository.findAll();
    }

    @Override
    public InteractiveChapter getInteractiveChapterById(Long interactiveChapterId) throws InteractiveChapterNotFoundException {
        Optional<InteractiveChapter> interactiveChapterOptional = interactiveChapterRepository.findById(interactiveChapterId);
        if (interactiveChapterOptional.isPresent()) {
            return interactiveChapterOptional.get();
        } else {
            throw new InteractiveChapterNotFoundException("Interactive Chapter cannot be found");
        }
    }

    @Override
    public void deleteInteractiveChapter(Long id) {
        interactiveChapterRepository.deleteById(id);
    }
}
