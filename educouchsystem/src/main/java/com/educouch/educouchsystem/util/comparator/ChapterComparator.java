package com.educouch.educouchsystem.util.comparator;

import com.educouch.educouchsystem.model.InteractiveChapter;

import java.util.Comparator;

public class ChapterComparator implements Comparator<InteractiveChapter> {
    @Override
    public int compare(InteractiveChapter a, InteractiveChapter b) {
        return a.getChapterIndex() < b.getChapterIndex() ? -1 : a.getChapterIndex() == b.getChapterIndex() ? 0 : 1;
    }
}