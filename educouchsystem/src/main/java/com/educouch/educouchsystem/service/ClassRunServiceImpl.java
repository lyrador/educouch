package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.ClassRun;
import com.educouch.educouchsystem.repository.ClassRunRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassRunServiceImpl implements ClassRunService {

    @Autowired
    private ClassRunRepository classRunRepository;

    public ClassRun saveClassRun(ClassRun classRun) {
        return classRunRepository.save(classRun);
    }

    @Override
    public List<ClassRun> getAllClassRuns() {
        return classRunRepository.findAll();
    }

    @Override
    public ClassRun retrieveClassRunById(Long id) {
        return classRunRepository.findById(id).get();
    }

    @Override
    public void deleteClassRun(Long id) {
        classRunRepository.deleteById(id);
    }

}
