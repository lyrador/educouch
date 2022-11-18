package com.educouch.educouchsystem.util.comparator;

import com.educouch.educouchsystem.model.InteractiveChapter;
import com.educouch.educouchsystem.model.TriviaQuestion;

import java.util.Comparator;

public class TriviaQuestionComparator implements Comparator<TriviaQuestion> {
    @Override
    public int compare(TriviaQuestion a, TriviaQuestion b) {
        return a.getQuestionNumber() < b.getQuestionNumber() ? -1 : a.getQuestionNumber() == b.getQuestionNumber() ? 0 : 1;
    }
}