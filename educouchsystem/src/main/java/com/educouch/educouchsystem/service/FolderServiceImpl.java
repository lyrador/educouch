package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.dto.FolderData;
import com.educouch.educouchsystem.dto.ParentFolder;
import com.educouch.educouchsystem.model.Attachment;
import com.educouch.educouchsystem.model.Course;
import com.educouch.educouchsystem.model.Folder;
import com.educouch.educouchsystem.repository.AttachmentRepository;
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
import java.util.*;

@Service
public class FolderServiceImpl implements FolderService{

    @Autowired
    private FolderRepository folderRepository;
    @Autowired
    private CourseService courseService;

    @Autowired
    private AttachmentRepository attachmentRepository;

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
            Folder folderToDelete = getFolder(folderId);
            List<Folder> listOfFolders = folderToDelete.getChildFolders();
            List<Attachment> listOfAttachments = folderToDelete.getAttachments();

            Folder parentFolder = folderToDelete.getParentFolder();
            Course course = folderToDelete.getCourse();

            //remove from course
            course.getFolders().remove(folderToDelete);
            courseService.saveCourse(course);

            // remove from parent
            if(parentFolder != null) {
                parentFolder.getChildFolders().remove(folderToDelete);
                folderRepository.save(parentFolder);
            }



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
            List<Folder> listOfSubFolders = parentFolder.getChildFolders();
            String newFolderName = folder.getFolderName();
            for(Folder f: listOfSubFolders) {
                if(f.getFolderName().equals(newFolderName)) {
                    throw new FolderUnableToSaveException("Duplicate name detected within the same directory.");
                }
            }

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

            List<Folder> listOfParentFolders = course.getFolders();
            String newFolderName = folder.getFolderName();
            for(Folder f: listOfParentFolders) {
                if(f.getFolderName().equals(newFolderName)) {
                    throw new FolderUnableToSaveException("Duplicate name detected within the same directory.");
                }
            }
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

    public void renameFolderByFolderId(String folderName, Long folderId) throws FolderNotFoundException,FolderUnableToSaveException {
        Folder f = folderRepository.getReferenceById(folderId);
        if(f != null) {
            if(f.getParentFolder() != null) {
                // is child folder
                Folder parentFolder = folderRepository.getReferenceById(f.getParentFolder().getFolderId());
                List<Folder> listOfChildFolders = parentFolder.getChildFolders();
                for(Folder childFolder: listOfChildFolders) {
                    if(childFolder.getFolderName().equals(folderName)) {
                        throw new FolderUnableToSaveException("Duplicate name detected. ");
                    }
                }

                f.setFolderName(folderName);
                folderRepository.save(f);
            } else {
                // is parent folder
                try {
                    Course c = courseService.getCourseById(f.getCourse().getCourseId());
                    List<Folder> listOfChildFolders = c.getFolders();
                    for(Folder childFolder: listOfChildFolders) {
                        if(childFolder.getFolderName().equals(folderName)) {
                            throw new FolderUnableToSaveException("Duplicate name detected. ");
                        }
                    }
                    f.setFolderName(folderName);
                    folderRepository.save(f);
                } catch(CourseNotFoundException ex) {
                    throw new FolderNotFoundException("Unable to find folder within the course.");
                }
            }
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

    @Override
    public List<FolderData> retrieveParentFolders(Long folderId) {
        List<FolderData> listOfFolderData = new ArrayList<>();
        Folder folder = folderRepository.getReferenceById(folderId);
        listOfFolderData.add(new FolderData(folder.getFolderId().toString(), folder.getFolderName()));
        if(folder != null) {
            Folder parentFolder = folder.getParentFolder();
            while(parentFolder != null) {
                listOfFolderData.add(new FolderData(parentFolder.getFolderId().toString(), parentFolder.getFolderName()));
                parentFolder = parentFolder.getParentFolder();
            }
            List<FolderData> swapped = new ArrayList<>();
            for(int i = listOfFolderData.size() - 1; i >= 0; i--) {
                swapped.add(listOfFolderData.get(i));
            }
            return swapped;
        } else {
            return listOfFolderData;
        }


    }


}
