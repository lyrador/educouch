package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Option;
import com.educouch.educouchsystem.util.exception.OptionNotFoundException;
import com.educouch.educouchsystem.util.exception.QuestionNotFoundException;

import java.util.List;

public interface OptionService {
    public Option saveOption(Option option);

    public List<Option> getAllOptionsFromQuestion(Long questionId) throws QuestionNotFoundException;

    public Option retrieveOptionById(Long optionId) throws OptionNotFoundException;

    public void deleteOptionById(Long optionId) throws OptionNotFoundException;

    public Option updateOption(Option optionToUpdate, Option option) throws OptionNotFoundException;
}
