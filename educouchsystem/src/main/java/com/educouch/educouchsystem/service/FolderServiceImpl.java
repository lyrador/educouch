package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Course;
import com.educouch.educouchsystem.model.Folder;
import com.educouch.educouchsystem.repository.FolderRepository;
import com.educouch.educouchsystem.util.exception.CourseNotFoundException;
import com.educouch.educouchsystem.util.exception.FolderNotFoundException;
import com.educouch.educouchsystem.util.exception.FolderUnableToSaveException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
// Steps to make a new folder
// Make a new empty folder
// Add the attachment and sub folder one by one
// Hence subfolder and attachment can only be created inside a persisted / managed folder

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FolderServiceImpl implements FolderService{

    @Autowired
    private FolderRepository folderRepository;
    @Autowired
    private CourseService courseService;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Folder saveFolder(Folder folder) throws FolderUnableToSaveException {
        try{
            return folderRepository.save(folder);
        }catch(Exception ex) {
            throw new FolderUnableToSaveException();
        }
    }
    @Override
    public Folder getFolder(Long folderId) throws FolderNotFoundException{

        Folder folderOpt =  folderRepository.findById(folderId).get();
        if(folderOpt != null) {
            return folderOpt;
        } else {
            throw new FolderNotFoundException("Folder cannot be found");
        }
    }

    @Override
    public void deleteFolder(Long folderId) throws FolderNotFoundException{
        try {
            folderRepository.deleteById(folderId);
        } catch (Exception ex) {
            throw new FolderNotFoundException(ex.getMessage());
        }

    }

    // for subfolder
    @Override
    public Folder saveFolder(Long courseId, Long parentFolderId, Folder folder) throws FolderUnableToSaveException {
        try {
            Course course = courseService.getCourseById(courseId);
            Folder parentFolder = getFolder(parentFolderId);

            // out direction
            folder.setParentFolder(parentFolder);
            folder.setCourse(course);

            // persist folder
            Folder newFolder = folderRepository.save(folder);

            // the other direction
            course.getFolders().add(newFolder);
            parentFolder.getChildFolders().add(newFolder);

            // save the course & parentFolder
            folderRepository.save(parentFolder);
            courseService.saveCourse(course);


            return newFolder;
        } catch(CourseNotFoundException | FolderNotFoundException ex) {
            throw new FolderUnableToSaveException(ex.getMessage());
        }

    }

    // for parent folder
    @Override
    public Folder saveFolder(Long courseId, Folder folder) throws FolderUnableToSaveException {
        try {
            Course course = courseService.getCourseById(courseId);
            // out direction
            folder.setCourse(course);
            // persist folder
            Folder newFolder = folderRepository.save(folder);
            // the other direction
            course.getFolders().add(newFolder);
            // save the course
            courseService.saveCourse(course);


            return newFolder;
        } catch(CourseNotFoundException  ex) {
            throw new FolderUnableToSaveException(ex.getMessage());
        }

    }


    @Override
    public List<Folder> getAllFolders() {
        return folderRepository.findAll();
    }
    public List<Folder> getFoldersByCourseCode(String courseCode) throws FolderNotFoundException {
        Course c = courseService.getCourseByCourseCode(courseCode);
        if(c != null) {
            return retrieveParentFolders(c);
        } else {
            throw new FolderNotFoundException("Course cannot be found. ");
        }
    }

    public List<Folder> getFoldersByCourseId(Long courseId) throws FolderNotFoundException {
        try {
            Course c = courseService.getCourseById(courseId);
            return retrieveParentFolders(c);

        } catch(CourseNotFoundException ex) {
            throw new FolderNotFoundException("Course doesn't exist.");
        }

    }

    public void renameFolderByFolderId(String folderName, Long folderId) throws FolderNotFoundException {
        Folder f = folderRepository.getReferenceById(folderId);
        if(f != null) {
            f.setFolderName(folderName);
            folderRepository.save(f);
        } else {
            throw new FolderNotFoundException("Folder cannot be found. ");
        }


    }

    private List<Folder> retrieveParentFolders(Course c) {

        List<Folder> folders = c.getFolders();
        List<Folder> parentFolders = new ArrayList<>();
        for (Folder f: folders) {
            if (f.getParentFolder() == null) {
                parentFolders.add(f);
            }
        }

        return parentFolders;
    }


}
