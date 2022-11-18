package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.TechnicalSupportRequest;
import com.educouch.educouchsystem.repository.TechnicalSupportRequestRepository;
import com.educouch.educouchsystem.util.exception.TechnicalSupportRequestNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechnicalSupportRequestServiceImpl implements TechnicalSupportRequestService {

    @Autowired
    TechnicalSupportRequestRepository technicalSupportRequestRepository;

    @Override
    public TechnicalSupportRequest saveTechnicalSupportRequest(TechnicalSupportRequest technicalSupportRequest) throws TechnicalSupportRequestNotFoundException {
        return technicalSupportRequestRepository.save(technicalSupportRequest);
    }

    @Override
    public List<TechnicalSupportRequest> getAllTechnicalSupportRequests() {
        return technicalSupportRequestRepository.findAll();
    }

    @Override
    public TechnicalSupportRequest retrieveTechnicalSupportRequestById(Long id) {
        return technicalSupportRequestRepository.findById(id).get();
    }

    @Override
    public void deleteTechnicalSupportRequest(Long id) {
        technicalSupportRequestRepository.deleteById(id);
    }
}
