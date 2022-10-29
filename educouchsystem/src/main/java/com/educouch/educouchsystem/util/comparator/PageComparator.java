package com.educouch.educouchsystem.util.comparator;

import com.educouch.educouchsystem.model.InteractiveChapter;
import com.educouch.educouchsystem.model.InteractivePage;

import java.util.Comparator;

public class PageComparator implements Comparator<InteractivePage> {
    @Override
    public int compare(InteractivePage a, InteractivePage b) {
        return a.getPageNumber() < b.getPageNumber() ? -1 : a.getPageNumber() == b.getPageNumber() ? 0 : 1;
    }
}