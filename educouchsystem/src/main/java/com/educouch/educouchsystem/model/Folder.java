package com.educouch.educouchsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long folderId;
    @NotNull
    private String folderName;

    @OneToMany(mappedBy="parentFolder")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Folder> childFolders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Folder parentFolder;

    @OneToMany(cascade = {CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private List<Attachment> attachments;

    @ManyToOne
    @JoinColumn
    @NotNull
    private Course course;

    public Folder() {
        childFolders = new ArrayList<>();
        attachments = new ArrayList<>();
    }

    public Folder(String folderName) {
        new Folder();
        this.folderName = folderName;

    }

    public Long getFolderId() {
        return folderId;
    }

    public void setFolderId(Long folderId) {
        this.folderId = folderId;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }
    @JsonIgnore
    public List<Folder> getChildFolders() {
        return childFolders;
    }

    public void setChildFolders(List<Folder> childFolders) {
        this.childFolders = childFolders;
    }

    public Folder getParentFolder() {
        return parentFolder;
    }

    public void setParentFolder(Folder parentFolder) {
        this.parentFolder = parentFolder;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    @JsonIgnore
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
