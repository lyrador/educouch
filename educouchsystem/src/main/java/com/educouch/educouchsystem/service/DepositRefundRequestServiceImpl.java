package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Forum;
import com.educouch.educouchsystem.model.DepositRefundRequest;
import com.educouch.educouchsystem.repository.DepositRefundRequestRepository;
import com.educouch.educouchsystem.util.enumeration.RefundStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public void checkRefundDueDate() {
        List<DepositRefundRequest> list = getAllRefundRequests();
        for(DepositRefundRequest req : list) {
            if(req.getDueTime().isAfter(LocalDateTime.now().plusDays(1l)) && req.getRefundStatusEnum() == RefundStatusEnum.REQUESTED) {
                req.setRefundStatusEnum(RefundStatusEnum.OVERDUE);
                saveRefundRequest(req);
            }
        }
    }

    @Override
    public void deleteDepositRefundRequest(Long id) {
        refundRequestRepository.deleteById(id);
    }
}
