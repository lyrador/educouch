package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Attachment;
import com.educouch.educouchsystem.model.FileSubmissionAttempt;
import com.educouch.educouchsystem.model.Folder;
import com.educouch.educouchsystem.repository.AttachmentRepository;
import com.educouch.educouchsystem.s3.service.StorageService;
import com.educouch.educouchsystem.util.exception.*;
import com.educouch.educouchsystem.util.logger.LoggingController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AttachmentServiceImpl implements AttachmentService{

    private Logger logger = LoggerFactory.getLogger(LoggingController.class);
    private AttachmentRepository attachmentRepository;

    private StorageService storageService;
    @Autowired
    private FolderService folderService;

    @Autowired
    private FileSubmissionAttemptService fileSubmissionAttemptService;

    public AttachmentServiceImpl(AttachmentRepository attachmentRepository, StorageService storageService) {
        this.attachmentRepository = attachmentRepository;
        this.storageService = storageService;
    }

    @Override
    public Attachment saveAttachment(MultipartFile file) throws FilenameContainsInvalidPathSequenceException, FileUnableToSaveException {
        String[] fileStorageNameAndURLArray;
        String fileOriginalName = StringUtils.cleanPath(file.getOriginalFilename());
        if (fileOriginalName.contains("..")) {
            throw new FilenameContainsInvalidPathSequenceException("Filename contains invalid path sequence " + fileOriginalName);
        }
        fileStorageNameAndURLArray = storageService.uploadFile(file);
        Attachment attachment = new Attachment(fileOriginalName, fileStorageNameAndURLArray[0], file.getContentType(), fileStorageNameAndURLArray[1]);
        return attachmentRepository.save(attachment);
    }

    @Override
    public Attachment getAttachment(Long attachmentId) throws FileNotFoundException {
        return attachmentRepository
                .findById(attachmentId)
                .orElseThrow(() -> new FileNotFoundException("File not found with Id: " + attachmentId));
    }

    @Override
    public List<Attachment> getAttachments(List<Long> attachmentIds) throws FileNotFoundException {
        List<Attachment> attachments = new ArrayList<>();
        for (Long attachmentId : attachmentIds) {
            attachments.add(getAttachment(attachmentId));
        }
        return attachments;
    }

    @Override
    public String deleteAttachment(Long attachmentId) throws FileNotFoundException {
        Attachment attachment = getAttachment(attachmentId);
        storageService.deleteFile(attachment.getFileStorageName());
        attachmentRepository.deleteById(attachmentId);
        return "Successfully deleted attachmentId: " + attachmentId + " from repository";
    }
    // attachment alr persisted
    @Override
    public void uploadFileToFolder(Attachment attachment, Long folderId) throws FolderNotFoundException,
            FolderUnableToSaveException {
        Folder f = folderService.getFolder(folderId);
        f.getAttachments().add(attachment);
        folderService.saveFolder(f);
    }

    @Override
    public void rename(Long attachmentId, String fileName) throws FileNotFoundException {
        Attachment attachment = getAttachment(attachmentId);
        attachment.setFileOriginalName(fileName);
        attachmentRepository.save(attachment);
    }

    @Override
    public void deleteAttachmentFromFolder(Long attachmentId, Long folderId) throws FolderNotFoundException,
            FileNotFoundException, FolderUnableToSaveException {
        Attachment attachment = getAttachment(attachmentId);
        Folder f = folderService.getFolder(folderId);
        f.getAttachments().remove(attachment);
        folderService.saveFolder(f);
        this.deleteAttachment(attachmentId);

    }

    @Override
    public void uploadAttachmentToFileSubmissionAttempt(Attachment attachment, Long fileSubmissionAttemptId) throws FileSubmissionAttemptNotFoundException, FileNotFoundException {
        FileSubmissionAttempt fileSubmissionAttempt = fileSubmissionAttemptService.retrieveFileSubmissionAttemptById(fileSubmissionAttemptId);
        fileSubmissionAttempt.getAttachments().add(attachment);
        fileSubmissionAttemptService.saveFileSubmissionAttempt(fileSubmissionAttempt);
    }

    @Override
    public void removeAttachmentFromFileSubmissionAttempt(Long attachmentId, Long fileSubmissionAttemptId) throws FileSubmissionAttemptNotFoundException, FileNotFoundException {
        Attachment attachment = getAttachment(attachmentId);
        FileSubmissionAttempt fileSubmissionAttempt = fileSubmissionAttemptService.retrieveFileSubmissionAttemptById(fileSubmissionAttemptId);
        fileSubmissionAttempt.getAttachments().remove(attachment);
        fileSubmissionAttemptService.saveFileSubmissionAttempt(fileSubmissionAttempt);
        this.deleteAttachment(attachmentId);
    }
}
