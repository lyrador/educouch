package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Folder;
import com.educouch.educouchsystem.util.exception.FolderNotFoundException;
import com.educouch.educouchsystem.util.exception.FolderUnableToSaveException;

import java.util.Optional;

public interface FolderService {

    Folder createFolder(Folder folder) throws FolderUnableToSaveException;

    Folder getFolder(Long folderId) throws FolderNotFoundException;

    void deleteFolder(Long folderId) throws FolderNotFoundException;

    Folder createFolder(Long parentFolderId, Folder folder) throws FolderUnableToSaveException;

}
