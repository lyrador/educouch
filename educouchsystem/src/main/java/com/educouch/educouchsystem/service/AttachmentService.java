package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Attachment;
import com.educouch.educouchsystem.util.exception.FileUnableToSaveException;
import com.educouch.educouchsystem.util.exception.FilenameContainsInvalidPathSequenceException;
import com.educouch.educouchsystem.util.exception.FolderNotFoundException;
import com.educouch.educouchsystem.util.exception.FolderUnableToSaveException;
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
}
