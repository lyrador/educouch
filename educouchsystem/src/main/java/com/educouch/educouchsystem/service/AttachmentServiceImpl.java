package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.*;
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

    @Autowired
    private FileSubmissionService fileSubmissionService;

    @Autowired
    private ReelService reelService;

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
        Boolean isValid = isValid(folderId, attachment.getFileOriginalName());

        if(isValid) {
            f.getAttachments().add(attachment);
            folderService.saveFolder(f);
        } else {
            throw new FolderUnableToSaveException("Duplicate attachment name detected.");
        }

    }

    @Override
    public void rename(Long attachmentId, String fileName) throws FileNotFoundException {
        Attachment attachment = getAttachment(attachmentId);
        List<Folder> listOfFolders = folderService.getAllFolders();
        Folder parentFolder = new Folder();
        for(Folder f: listOfFolders) {
            if(f.getAttachments().contains(attachment)) {
                parentFolder = f;
                break;
            }
        }

        if(parentFolder.getFolderId() != null) {
            Boolean isValid = isValid(parentFolder.getFolderId(), fileName);
            if(isValid) {
                attachment.setFileOriginalName(fileName);
                attachmentRepository.save(attachment);
            } else {
                throw new FileNotFoundException("Duplicate attachment name detected.");
            }
        } else {
            throw new FileNotFoundException("Unable to find the parent folder of the file.");
        }

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

    private Boolean isValid(Long folderId, String attachmentTitle) {
        try {
            Folder folder = folderService.getFolder(folderId);
            List<Attachment> listOfAttachments = folder.getAttachments();
            for(Attachment a: listOfAttachments) {
                if(a.getFileOriginalName().equals(attachmentTitle)) {
                    System.out.println("Is invalid.");
                    return false;
                }
            }

            return true;
        } catch(FolderNotFoundException ex) {
            return false;
        }

    }

    @Override
    public void uploadAttachmentToFileSubmissionAttempt(Attachment attachment, Long fileSubmissionAttemptId) throws FileSubmissionAttemptNotFoundException, FileNotFoundException {
        FileSubmissionAttempt fileSubmissionAttempt = fileSubmissionAttemptService.retrieveFileSubmissionAttemptById(fileSubmissionAttemptId);
        fileSubmissionAttempt.setAttachments(attachment);
        fileSubmissionAttemptService.saveFileSubmissionAttempt(fileSubmissionAttempt);
    }

//    @Override
//    public void removeAttachmentFromFileSubmissionAttempt(Long attachmentId, Long fileSubmissionAttemptId) throws FileSubmissionAttemptNotFoundException, FileNotFoundException {
//        Attachment attachment = getAttachment(attachmentId);
//        FileSubmissionAttempt fileSubmissionAttempt = fileSubmissionAttemptService.retrieveFileSubmissionAttemptById(fileSubmissionAttemptId);
//        fileSubmissionAttempt.setAttachments(null);
//        fileSubmissionAttemptService.saveFileSubmissionAttempt(fileSubmissionAttempt);
//        this.deleteAttachment(attachmentId);
//    }

    @Override
    public void uploadAttachmentToFileSubmissionAssessment(Attachment attachment, Long fileSubmissionId) throws FileSubmissionNotFoundException, FileNotFoundException {
        FileSubmission fileSubmission = fileSubmissionService.retrieveFileSubmissionById(fileSubmissionId);
        fileSubmission.getAttachments().add(attachment);
        fileSubmissionService.saveFileSubmission(fileSubmission);
    }

    @Override
    public void removeAttachmentFromFileSubmissionAssessment(Long attachmentId, Long fileSubmissionId) throws FileSubmissionNotFoundException, FileNotFoundException {
        Attachment attachment = getAttachment(attachmentId);
        FileSubmission fileSubmission = fileSubmissionService.retrieveFileSubmissionById(fileSubmissionId);
        fileSubmission.getAttachments().remove(attachment);
        fileSubmissionService.saveFileSubmission(fileSubmission);
        this.deleteAttachment(attachmentId);
    }

    @Override
    public void uploadVideoToReel(Attachment attachment, Long reelId) throws ReelNotFoundException {
        try {
            Reel reel = reelService.retrieveReelById(reelId);
            reel.setVideo(attachment);
            reelService.saveReel(reel);
        } catch (ReelNotFoundException exception) {
            throw new ReelNotFoundException();
        }
    }

    @Override
    public void removeVideoFromReel(Long attachmentId, Long reelId) throws ReelNotFoundException, FileNotFoundException {
        Attachment attachment = getAttachment(attachmentId);
        Reel reel = reelService.retrieveReelById(reelId);
        reel.setVideo(null);
        reelService.saveReel(reel);
        this.deleteAttachment(attachmentId);
    }
}
