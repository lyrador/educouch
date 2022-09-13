package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.model.Course;
import com.educouch.educouchsystem.model.Folder;
import com.educouch.educouchsystem.service.FolderService;
import com.educouch.educouchsystem.util.exception.FolderNotFoundException;
import com.educouch.educouchsystem.util.exception.FolderUnableToSaveException;
import com.educouch.educouchsystem.webServiceModel.ChildFolder;
import com.educouch.educouchsystem.webServiceModel.ParentFolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
//giving learner path here
@RequestMapping("/folder")
//tells the react and springboot application to connect to each other
@CrossOrigin
public class FolderController {
    @Autowired
    //inject learnerService here
    private FolderService folderService;

    //giving path here
    @PostMapping("/addParentFolder")
    public String addParentFolder(@RequestBody ParentFolder parentFolder) {
        Long courseId = parentFolder.getCourseId();
        Folder newFolder = parentFolder.getFolder();
        System.out.println("Course id is : " + courseId.toString());
        System.out.println("Folder name is: " + newFolder.getFolderName());
        try {
            folderService.saveFolder(courseId, newFolder);
            return "New parent folder has been created. ";
        } catch(FolderUnableToSaveException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to save folder", ex);
        }
    }

    @PostMapping("/addSubFolder")
    public String addSubFolder(@RequestBody ChildFolder childFolder) {
        Long courseId = childFolder.getCourseId();
        Folder newFolder = childFolder.getFolder();
        Long parentId = childFolder.getParentFolderId();
        try {
            folderService.saveFolder(courseId, parentId, newFolder);
            return "New child folder has been created. ";
        } catch(FolderUnableToSaveException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to save folder", ex);
        }
    }

    @GetMapping("/getAll")
    public List<Folder> getAllFolder(){
        List<Folder> folders = folderService.getAllFolders();
        for(Folder folder: folders){
            processFolder(folder);
        }
        return folders;
    }

    @DeleteMapping("/deleteFolder/{folderId}")
    public String deleteFolder(@PathVariable Long folderId){
        System.out.print("Reach here");
        Long fid = new Long(folderId);

        try {
            folderService.deleteFolder(fid);
            return "Folder successfully deleted. ";
        } catch(FolderNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Folder not found", ex);
        }
    }

    private Folder processFolder(Folder folder) {
        List<Folder> subFolders = folder.getChildFolders();
        for(Folder cf: subFolders) {
            cf.setParentFolder(null);
        }
        Folder parentFolder = folder.getParentFolder();
        if(parentFolder != null) {
            parentFolder.setChildFolders(null);
        }
        folder.setCourse(null);

        return folder;

    }





}
