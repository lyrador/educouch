package com.educouch.educouchsystem.s3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.educouch.educouchsystem.model.Attachment;
import com.educouch.educouchsystem.util.logger.LoggingController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class StorageService {

    @Value("${application.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Client;

    Logger logger = LoggerFactory.getLogger(LoggingController.class);

    public String[] uploadFile(MultipartFile file) {
        String[] fileStorageNameAndURLArray = new String[2];
        File fileObj = convertMultiPartFileToFile(file);
        String fileStorageName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        s3Client.putObject(new PutObjectRequest(bucketName, fileStorageName, fileObj));
        fileObj.delete();
        fileStorageNameAndURLArray[0] = fileStorageName;
        fileStorageNameAndURLArray[1] = String.valueOf(s3Client.getUrl(bucketName, fileStorageName));
        return fileStorageNameAndURLArray;
    }

    public byte[] downloadFile(String fileStorageName) {
        S3Object s3Object = s3Client.getObject(bucketName, fileStorageName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            byte[] content = IOUtils.toByteArray(inputStream);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void downloadZipFile(HttpServletResponse response, List<Attachment> listOfAttachments) throws FileNotFoundException {
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=download.zip");
        try(ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream())) {
            for(Attachment attachment : listOfAttachments) {
                ByteArrayResource fileSystemResource = new ByteArrayResource(downloadFile(attachment.getFileStorageName()));
                ZipEntry zipEntry = new ZipEntry(attachment.getFileOriginalName());
                zipEntry.setSize(fileSystemResource.contentLength());
                zipEntry.setTime(System.currentTimeMillis());
                zipOutputStream.putNextEntry(zipEntry);
                StreamUtils.copy(fileSystemResource.getInputStream(), zipOutputStream);
                zipOutputStream.closeEntry();
            }
            zipOutputStream.finish();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public String deleteFile(String fileStorageName) {
        s3Client.deleteObject(bucketName, fileStorageName);
        return fileStorageName + " removed ...";
    }

    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            logger.error("Error converting multipartFile to file", e);
        }
        return convertedFile;
    }
}

