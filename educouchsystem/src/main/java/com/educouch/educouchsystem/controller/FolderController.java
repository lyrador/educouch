package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.model.Course;
import com.educouch.educouchsystem.model.Folder;
import com.educouch.educouchsystem.model.Forum;
import com.educouch.educouchsystem.service.FolderService;
import com.educouch.educouchsystem.util.exception.FolderNotFoundException;
import com.educouch.educouchsystem.util.exception.FolderUnableToSaveException;
import com.educouch.educouchsystem.dto.ChildFolder;
import com.educouch.educouchsystem.dto.ParentFolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
            System.out.println("Unable to save due to duplicate name.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }
    }

    @GetMapping("/getAll")
    public List<Folder> getAllFolder(){
        List<Folder> folders = folderService.getAllFolders();
        System.out.println("Length of folders list is " + folders.size());
        for(Folder folder: folders){
            processFolder(folder);
        }
        return folders;
    }
    @GetMapping("/getFolderByFolderId/{folderId}")
    public Folder getFolderByFolderId(@PathVariable Long folderId) {
        try {
            Folder folder = folderService.getFolder(folderId);
            processFolder(folder);
            return folder;
        } catch(FolderNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Folder not found", ex);
        }
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
        System.out.println("When folder is folder " + folder.getFolderId());
        List<Folder> subFolders = folder.getChildFolders();
        System.out.println("SubFolders is " + subFolders.toString());
        for(Folder cf: subFolders) {
            cf.setParentFolder(null);
            cf.setAttachments(null);
            cf.setChildFolders(null);
            cf.setCourse(null);
        }
        Folder parentFolder = folder.getParentFolder();
        if(parentFolder != null) {
            parentFolder.setChildFolders(null);
            parentFolder.setAttachments(null);
            parentFolder.setParentFolder(null);
            parentFolder.setCourse(null);
        }
        folder.setCourse(null);
        folder.getAttachments();

        return folder;

    }
    @GetMapping("/getFoldersByCourseCode/{courseCode}")
    public List<Folder> getFoldersByCourseCode(@PathVariable String courseCode) {
        try{

            List<Folder> folders = folderService.getFoldersByCourseCode(courseCode);
            for(Folder f: folders) {
                processFolder(f);
            }

            return folders;
        } catch(FolderNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Course cannot be found", ex);
        }
    }

    @GetMapping("/getFoldersByCourseId/{courseId}")
    public List<Folder> getFoldersByCourseId(@PathVariable String courseId) {
        Long courseIdInLong = new Long(courseId);
        try {
            List<Folder> folders = folderService.getFoldersByCourseId(courseIdInLong);
            for(Folder f: folders) {
                processFolder(f);
            }

            return folders;
        } catch(FolderNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Course cannot be found", ex);
        }
    }



    @GetMapping("/renameFolderByFolderId")
    @ResponseBody
    public String renameFolderByFolderId(@RequestParam String folderId, @RequestParam String folderName) {
        try{
            folderService.renameFolderByFolderId(folderName, new Long(folderId));
            return "Successfully renamed the folder.";
        }catch(FolderNotFoundException | FolderUnableToSaveException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        }

    }
}