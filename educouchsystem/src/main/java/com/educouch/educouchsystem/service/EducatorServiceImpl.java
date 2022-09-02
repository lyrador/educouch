package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Educator;
import com.educouch.educouchsystem.repository.EducatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EducatorServiceImpl implements EducatorService{

    @Autowired
    private EducatorRepository educatorRepository;
    
    @Override
    public Educator saveEducator(Educator educator) {

        return educatorRepository.save(educator);
    }

    public List<Educator> getAllEducators() {
        return educatorRepository.findAll();
    }


}
