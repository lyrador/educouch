package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Announcement;

import java.util.List;

public interface AnnouncementService {

    public Announcement saveAnnouncement(Announcement announcement);

    public List<Announcement> getAllAnnouncements();

    public Announcement retrieveAnnouncementById(Long id);

    public void deleteAnnouncement(Long id);
}
