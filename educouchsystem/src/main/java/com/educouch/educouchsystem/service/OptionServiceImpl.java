package com.educouch.educouchsystem.service;


import com.educouch.educouchsystem.model.Option;
import com.educouch.educouchsystem.model.Question;
import com.educouch.educouchsystem.repository.OptionRepository;
import com.educouch.educouchsystem.repository.QuestionRepository;
import com.educouch.educouchsystem.util.exception.OptionNotFoundException;
import com.educouch.educouchsystem.util.exception.QuestionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class OptionServiceImpl implements OptionService {
    @Autowired
    OptionRepository optionRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Override
    public Option saveOption(Option option) {
        return optionRepository.save(option);
    }

    @Override
    public List<Option> getAllOptionsFromQuestion(Long questionId) throws QuestionNotFoundException {
        Question question = questionRepository.findById(questionId).get();
        if (question != null) {
            return question.getOptions();
        } else {
            throw new QuestionNotFoundException("Question Id " + questionId + " does not exist!");
        }
    }

    @Override
    public Option retrieveOptionById(Long optionId) throws OptionNotFoundException {
        Option option = optionRepository.findById(optionId).get();
        if (option != null) {
            return option;
        } else {
            throw new OptionNotFoundException("Option Id " + optionId + " does not exist!");
        }
    }

    @Override
    public void deleteOptionById(Long optionId) throws OptionNotFoundException {
        Option option = optionRepository.findById(optionId).get();
        if (option != null) {
            questionRepository.deleteById(optionId);
        } else {
            throw new OptionNotFoundException("Option Id " + optionId + " does not exist!");
        }
    }

    @Override
    public Option updateOption(Option option) throws OptionNotFoundException {
        Option optionToUpdate = optionRepository.findById(option.getOptionId()).get();
        if (optionToUpdate.getOptionId().equals(option.getOptionId())) {
            optionToUpdate.setOptionContent(option.getOptionContent());
            optionToUpdate.setCorrect(option.getCorrect()); //for determining if this is a correct answer for MCQ/MRQ
            optionRepository.save(optionToUpdate);
            return optionToUpdate;
        } else {
            throw new OptionNotFoundException("Option to be updated does not exist!");
        }
    }
}
