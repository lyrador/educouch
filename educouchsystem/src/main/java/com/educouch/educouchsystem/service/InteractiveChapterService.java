package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.InteractiveChapter;
import com.educouch.educouchsystem.util.exception.InteractiveChapterNotFoundException;

import java.util.List;

public interface InteractiveChapterService {

    public InteractiveChapter saveInteractiveChapter (InteractiveChapter interactiveChapter);

    public List<InteractiveChapter> getAllInteractiveChapters();

    public InteractiveChapter getInteractiveChapterById(Long interactiveChapterId) throws InteractiveChapterNotFoundException;

    public void deleteInteractiveChapter(Long id);
}
