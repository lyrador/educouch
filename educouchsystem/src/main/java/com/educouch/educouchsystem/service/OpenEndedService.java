package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.OpenEnded;
import com.educouch.educouchsystem.model.Question;
import com.educouch.educouchsystem.util.exception.OpenEndedNotFoundException;
import com.educouch.educouchsystem.util.exception.QuestionNotFoundException;

import java.util.List;

public interface OpenEndedService {

    public OpenEnded saveOpenEnded(OpenEnded openEnded);

    public List<OpenEnded> getAllOpenEnded();

    public OpenEnded retrieveOpenEndedById(Long openEndedId) throws OpenEndedNotFoundException;

    public void deleteOpenEnded(Long openEndedId) throws OpenEndedNotFoundException;
}
