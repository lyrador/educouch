package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Forum;
import com.educouch.educouchsystem.model.DepositRefundRequest;
import com.educouch.educouchsystem.repository.DepositRefundRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("refundRequestService")
public class DepositRefundRequestServiceImpl implements DepositRefundRequestService {

    @Autowired
    DepositRefundRequestRepository refundRequestRepository;

    @Override
    public DepositRefundRequest saveRefundRequest(DepositRefundRequest depositRefundRequest) {
        return refundRequestRepository.saveAndFlush(depositRefundRequest);
    }

    @Override
    public List<DepositRefundRequest> getAllRefundRequests() {
        return refundRequestRepository.findAll();
    }

    @Override
    public DepositRefundRequest getDepositRefundRequestById(Long id) {
        return refundRequestRepository.findById(id).get();
    }

    @Override
    public void deleteDepositRefundRequest(Long id) {
        refundRequestRepository.deleteById(id);
    }
}
