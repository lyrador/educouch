package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Folder;
import com.educouch.educouchsystem.repository.FolderRepository;
import com.educouch.educouchsystem.util.exception.FolderNotFoundException;
import com.educouch.educouchsystem.util.exception.FolderUnableToSaveException;
import org.springframework.stereotype.Service;
// Steps to make a new folder
// Make a new empty folder
// Add the attachment and sub folder one by one
// Hence subfolder and attachment can only be created inside a persisted / managed folder

import java.util.Optional;

@Service
public class FolderServiceImpl implements FolderService{

    private FolderRepository folderRepository;

    public Folder createFolder(Folder folder) throws FolderUnableToSaveException {

        return folderRepository.save(folder);
    }

    public Folder getFolder(Long folderId) throws FolderNotFoundException{

        Optional<Folder> folderOpt =  folderRepository.findById(folderId);
        if(folderOpt.isPresent()) {
            return folderOpt.get();
        } else {
            throw new FolderNotFoundException("Folder cannot be found");
        }
    }

    public void deleteFolder(Long folderId) throws FolderNotFoundException{
        try {
            folderRepository.deleteById(folderId);
        } catch (Exception ex) {
            throw new FolderNotFoundException(ex.getMessage());
        }

    }

    public Folder createFolder(Long parentFolderId, Folder folder) throws FolderUnableToSaveException {
        try {
            Folder parentFolder = getFolder(parentFolderId);
            folder.setParentFolder(parentFolder);
            return createFolder(folder);
        } catch(FolderNotFoundException | FolderUnableToSaveException ex) {
            throw new FolderUnableToSaveException(ex.getMessage());
        }

    }



}
