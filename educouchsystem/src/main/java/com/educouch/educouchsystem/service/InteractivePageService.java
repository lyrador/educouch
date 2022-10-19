package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.InteractivePage;
import com.educouch.educouchsystem.util.exception.InteractivePageNotFoundException;

import java.util.List;

public interface InteractivePageService {

    public InteractivePage saveInteractivePage(InteractivePage interactivePage);

    public List<InteractivePage> getAllInteractivePages();

    public InteractivePage getInteractivePageById(Long interactivePageId) throws InteractivePageNotFoundException;

    public void deleteInteractivePage(Long id);
}
