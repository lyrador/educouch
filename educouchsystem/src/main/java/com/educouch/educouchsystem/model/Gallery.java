package com.educouch.educouchsystem.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Gallery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long galleryId;

    @OneToOne(mappedBy = "gallery")
    private Learner learner;

    @OneToMany(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<ItemOwned> itemsOwned;

    public Gallery() {
        this.itemsOwned = new ArrayList<>();
    }

    public Gallery(Long galleryId, Learner learner, List<ItemOwned> itemsOwned) {
        new Gallery();
        this.galleryId = galleryId;
        this.learner = learner;
        this.itemsOwned = itemsOwned;
    }

    public Long getGalleryId() {
        return galleryId;
    }

    public void setGalleryId(Long galleryId) {
        this.galleryId = galleryId;
    }

    public Learner getLearner() {
        return learner;
    }

    public void setLearner(Learner learner) {
        this.learner = learner;
    }

    public List<ItemOwned> getItemsOwned() {
        return itemsOwned;
    }

    public void setItemsOwned(List<ItemOwned> itemsOwned) {
        this.itemsOwned = itemsOwned;
    }
}
