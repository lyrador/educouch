package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Attachment;
import com.educouch.educouchsystem.util.exception.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.List;

public interface AttachmentService {
    Attachment saveAttachment(MultipartFile file) throws FilenameContainsInvalidPathSequenceException, FileUnableToSaveException;

    Attachment getAttachment(Long fileId) throws FileNotFoundException;

    List<Attachment> getAttachments(List<Long> fileIds) throws FileNotFoundException;

    String deleteAttachment(Long attachmentId) throws FileNotFoundException;

    public void uploadFileToFolder(Attachment attachment, Long folderId) throws FolderNotFoundException,
            FolderUnableToSaveException;

    public void rename(Long attachmentId, String fileName) throws FileNotFoundException;

    public void deleteAttachmentFromFolder(Long attachmentId, Long folderId) throws FolderNotFoundException,
            FileNotFoundException, FolderUnableToSaveException;

    public void uploadAttachmentToFileSubmissionAttempt(Attachment attachment, Long fileSubmissionAttemptId) throws FileSubmissionAttemptNotFoundException,
            FileNotFoundException;

//    public void removeAttachmentFromFileSubmissionAttempt(Long attachmentId, Long fileSubmissionAttemptId) throws FileSubmissionAttemptNotFoundException,
//            FileNotFoundException;

    public void uploadAttachmentToFileSubmissionAssessment(Attachment attachment, Long fileSubmissionId) throws FileSubmissionNotFoundException,
            FileNotFoundException;

    public void removeAttachmentFromFileSubmissionAssessment(Long attachmentId, Long fileSubmissionId) throws FileSubmissionNotFoundException,
            FileNotFoundException;

    public void uploadVideoToReel(Attachment attachment, Long reelId) throws FileSubmissionNotFoundException,
            ReelNotFoundException;

    public void removeVideoFromReel(Long attachmentId, Long reelId) throws ReelNotFoundException, FileNotFoundException;
}
