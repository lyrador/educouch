package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.model.Attachment;
import com.educouch.educouchsystem.model.ResponseData;
import com.educouch.educouchsystem.s3.service.StorageService;
import com.educouch.educouchsystem.service.AttachmentService;
import com.educouch.educouchsystem.util.exception.FileUnableToSaveException;
import com.educouch.educouchsystem.util.exception.FilenameContainsInvalidPathSequenceException;
import com.educouch.educouchsystem.util.exception.FolderNotFoundException;
import com.educouch.educouchsystem.util.exception.FolderUnableToSaveException;
import com.educouch.educouchsystem.util.logger.LoggingController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.util.List;

@RestController
@CrossOrigin
public class AttachmentController {

    Logger logger = LoggerFactory.getLogger(LoggingController.class);
    private AttachmentService attachmentService;

    private StorageService storageService;

    public AttachmentController(AttachmentService attachmentService, StorageService storageService) {
        this.attachmentService = attachmentService;
        this.storageService = storageService;
    }

    @PostMapping("/uploadFile")
    public ResponseData uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        Attachment attachment = attachmentService.saveAttachment(file);
        return new ResponseData(attachment.getAttachmentId(),
                attachment.getFileOriginalName(),
                attachment.getFileStorageName(),
                attachment.getFileURL(),
                file.getContentType(),
                file.getSize());
    }

    @PostMapping("/uploadFiles")
    public ResponseData[] uploadFiles(@RequestParam("file") MultipartFile[] files) throws Exception {
        ResponseData[] responseDataArray = new ResponseData[files.length];
        int x = 0;
        for (MultipartFile file : files) {
            Attachment attachment = attachmentService.saveAttachment(file);
            responseDataArray[x] = new ResponseData(attachment.getAttachmentId(),
                    attachment.getFileOriginalName(),
                    attachment.getFileStorageName(),
                    attachment.getFileURL(),
                    file.getContentType(),
                    file.getSize());
            x++;
        }
        return responseDataArray;
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) throws Exception {
        Attachment attachment = attachmentService.getAttachment(fileId);
        byte[] data = storageService.downloadFile(attachment.getFileStorageName());
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + attachment.getFileOriginalName() + "\"")
                .body(resource);
    }

    @RequestMapping(value="/downloadZipFile/{listOfAttachmentIds}", method=RequestMethod.GET)
    @ResponseBody
    public void downloadZipFile(HttpServletResponse response, @PathVariable List<Long> listOfAttachmentIds) throws FileNotFoundException {
        List<Attachment> listOfAttachments = attachmentService.getAttachments(listOfAttachmentIds);
        storageService.downloadZipFile(response, listOfAttachments);
    }

    @DeleteMapping("/delete/{attachmentId}")
    public ResponseEntity<String> deleteAttachment(@PathVariable Long attachmentId) throws FileNotFoundException {
        return new ResponseEntity<>(attachmentService.deleteAttachment(attachmentId), HttpStatus.OK);
    }

    @PostMapping("/uploadFileToFolder")
    public ResponseData uploadAttachmentToFile(@RequestParam("file") MultipartFile file, @RequestParam Long folderId) {
        Attachment attachment = null;
        try {
            attachment = attachmentService.saveAttachment(file);
            attachmentService.uploadFileToFolder(attachment, folderId);
            return new ResponseData(attachment.getAttachmentId(),
                    attachment.getFileOriginalName(),
                    attachment.getFileStorageName(),
                    attachment.getFileURL(),
                    file.getContentType(),
                    file.getSize());
        } catch (FilenameContainsInvalidPathSequenceException | FileUnableToSaveException |
                FolderNotFoundException | FolderUnableToSaveException e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping("/renameAttachment")
    public void renameAttachment(@RequestParam Long attachmentId, @RequestParam String fileName) {
        try {
            attachmentService.rename(attachmentId, fileName);
        } catch (FileNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File cannot be found.", e);
        }
    }

    @DeleteMapping("/deleteFolderAttachment")
    public void deleteAttachmentFromFolder(@RequestParam Long attachmentId, @RequestParam Long folderId) {
        try {
            attachmentService.deleteAttachmentFromFolder(attachmentId, folderId);
        } catch (FolderNotFoundException | FileNotFoundException | FolderUnableToSaveException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File cannot be deleted.", e);
        }
    }
}
