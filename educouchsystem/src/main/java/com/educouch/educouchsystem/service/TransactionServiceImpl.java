package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.CourseStats;
import com.educouch.educouchsystem.model.Organisation;
import com.educouch.educouchsystem.model.Transaction;
import com.educouch.educouchsystem.repository.TransactionRepository;
import com.educouch.educouchsystem.util.enumeration.PaymentStatusEnum;
import com.educouch.educouchsystem.util.enumeration.PaymentTypeEnum;
import com.educouch.educouchsystem.util.exception.TransactionNotFoundException;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import com.educouch.educouchsystem.s3.service.StorageService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private OrganisationService organisationService;
    @Autowired
    StorageService storageService;

    @Autowired
    EmailSenderService emailSenderService;



//    @Override
//    public Transaction createTransaction(Transaction transaction) {
//        Organisation org = organisationService.findOrganisationByOrganisationName(transaction.getOrgName());
//        org.setPaymentStatus(PaymentStatusEnum.PAID);
//        org.setOrgBalance(org.getOrgBalance().subtract(transaction.getAmountPaid()));
//        organisationService.saveOrganisation(org);
//
//        return transactionRepository.save(transaction);
//    }

    @Override
    public Transaction createTransaction(Long organisationId) throws FileNotFoundException, JRException{
        Organisation org = organisationService.findOrganisationById(organisationId);
        Transaction transaction = new Transaction(org.getOrganisationId(), org.getOrganisationName(),org.getPaymentAcc(), org.getOrgBalance());
        List<CourseStats> list = org.getCourseStatsList();

        File file = ResourceUtils.getFile("classpath:OrgReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);
        Map<String,Object> params = new HashMap<>();
        params.put("createdBy", "Lms");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,params, dataSource);
        byte[] arr = JasperExportManager.exportReportToPdf(jasperPrint);
        LocalDate monthYear = LocalDate.now().minusMonths(1);
        String fileName = monthYear.getMonth().toString() + "_"+ monthYear.getYear();
        String fileNameWithOrg = org.getOrganisationName() + "_" + fileName;
        String[] strArr = storageService.uploadFile(arr,fileNameWithOrg);

        emailSenderService.sendEmailWithAttachment(org.getOrganisationAdmin().getEmail(),
                "Educouch Monthly Invoice",
                "Your monthly invoice from Educouch is here! For any discrepancies, please reply this email and our LMS Admins will get in touch. Thank you for your continued support of the Educouch Platform.",
                arr,fileNameWithOrg);

        transaction.setMonthYear(fileName);
        transaction.setReportUrl(strArr[1]);
        transaction.setFileStorageName(strArr[0]);
        org.setOrgBalance(new BigDecimal(0));
        organisationService.saveOrganisation(org);

        return transactionRepository.save(transaction);
    }

    public void dayOneTransactionGeneration() throws JRException, FileNotFoundException {
        List<Organisation> list = organisationService.findAllOrganisation();
        for(Organisation o : list) {
            createTransaction(o.getOrganisationId());
        }
    }


    @Override
    public List<Transaction> findAllTransactions() {
        return transactionRepository.findAll();
    }
    @Override
    public Transaction findTransactionById(Long transactionId) throws TransactionNotFoundException{
        if(transactionRepository.findById(transactionId).isPresent()) {
            return transactionRepository.findById(transactionId).get();
        } else {
            throw new TransactionNotFoundException("transaction not found!");

        }
    }
    @Override
    public Transaction updateTransaction(Transaction transaction) throws TransactionNotFoundException {
        Transaction transactionToUpdate = findTransactionById(transaction.getTransactionId());
        transactionToUpdate.setAmountPaid(transaction.getAmountPaid());
        transactionRepository.save(transactionToUpdate);
        return transactionToUpdate;
    }

    @Override
    public void deleteTransaction(Transaction transaction) throws TransactionNotFoundException{
        Transaction transactionToDelete = findTransactionById(transaction.getTransactionId());
        transactionRepository.delete(transactionToDelete);

    }

}
