package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.TechnicalSupportRequest;
import com.educouch.educouchsystem.util.exception.TechnicalSupportRequestNotFoundException;

import java.util.List;

public interface TechnicalSupportRequestService {

    public TechnicalSupportRequest saveTechnicalSupportRequest(TechnicalSupportRequest technicalSupportRequest) throws TechnicalSupportRequestNotFoundException;

    public List<TechnicalSupportRequest> getAllTechnicalSupportRequests();

    public TechnicalSupportRequest retrieveTechnicalSupportRequestById(Long id);

    public void deleteTechnicalSupportRequest(Long id);
}
