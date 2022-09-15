package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Folder;
import com.educouch.educouchsystem.util.exception.FolderNotFoundException;
import com.educouch.educouchsystem.util.exception.FolderUnableToSaveException;

import java.util.List;

public interface FolderService {

    Folder getFolder(Long folderId) throws FolderNotFoundException;

    void deleteFolder(Long folderId) throws FolderNotFoundException;

    Folder saveFolder(Long courseId, Long parentFolderId, Folder folder) throws FolderUnableToSaveException;

    Folder saveFolder(Long courseId, Folder folder) throws FolderUnableToSaveException;

    Folder saveFolder(Folder folder) throws FolderUnableToSaveException;

    List<Folder> getAllFolders();

    public List<Folder> getFoldersByCourseCode(String courseCode) throws FolderNotFoundException;

}
