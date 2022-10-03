package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.DepositRefundRequest;

import java.util.List;

public interface DepositRefundRequestService {

    public DepositRefundRequest saveRefundRequest(DepositRefundRequest depositRefundRequest);


    public List<DepositRefundRequest> getAllRefundRequests();


    public DepositRefundRequest getDepositRefundRequestById(Long id);


    public void deleteDepositRefundRequest(Long id);
}
