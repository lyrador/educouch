package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.dto.FolderData;
import com.educouch.educouchsystem.model.Folder;
import com.educouch.educouchsystem.util.exception.FolderNotFoundException;
import com.educouch.educouchsystem.util.exception.FolderUnableToSaveException;

import java.util.List;
import java.util.Map;

public interface FolderService {

    Folder getFolder(Long folderId) throws FolderNotFoundException;

    void deleteFolder(Long folderId) throws FolderNotFoundException;

    Folder saveFolder(Long courseId, Long parentFolderId, Folder folder) throws FolderUnableToSaveException;

    Folder saveFolder(Long courseId, Folder folder) throws FolderUnableToSaveException;

    Folder saveFolder(Folder folder) throws FolderUnableToSaveException;

    List<Folder> getAllFolders();

    public List<Folder> getFoldersByCourseCode(String courseCode) throws FolderNotFoundException;

    public List<Folder> getFoldersByCourseId(Long courseId) throws FolderNotFoundException;

    public void renameFolderByFolderId(String folderName, Long folderId) throws FolderNotFoundException,FolderUnableToSaveException;

    public List<FolderData> retrieveParentFolders(Long folderId);
}
