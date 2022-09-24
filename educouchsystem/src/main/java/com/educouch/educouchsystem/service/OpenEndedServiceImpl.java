package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.OpenEnded;
import com.educouch.educouchsystem.repository.OpenEndedRepository;
import com.educouch.educouchsystem.util.exception.OpenEndedNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpenEndedServiceImpl implements OpenEndedService {
    @Autowired
    OpenEndedRepository openEndedRepository;

    @Override
    public OpenEnded saveOpenEnded(OpenEnded openEnded) {
        return openEndedRepository.save(openEnded);
    }

    @Override
    public List<OpenEnded> getAllOpenEnded() {
        return openEndedRepository.findAll();
    }

    @Override
    public OpenEnded retrieveOpenEndedById(Long openEndedId) throws OpenEndedNotFoundException {
        OpenEnded openEnded = openEndedRepository.findById(openEndedId).get();
        if (openEnded != null) {
            return openEnded;
        } else {
            throw new OpenEndedNotFoundException("OpenEnded Id " + openEndedId + " does not exist!");
        }
    }

    @Override
    public void deleteOpenEnded(Long openEndedId) throws OpenEndedNotFoundException {
        OpenEnded openEnded = openEndedRepository.findById(openEndedId).get();
        if (openEnded != null) {
            openEndedRepository.deleteById(openEndedId);
        } else {
            throw new OpenEndedNotFoundException("OpenEnded Id " + openEndedId + " does not exist!");
        }
    }
}
