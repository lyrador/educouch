package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Announcement;
import com.educouch.educouchsystem.util.exception.AnnouncementNotFoundException;

import java.util.List;

public interface AnnouncementService {

    public Announcement saveAnnouncement(Announcement announcement) throws AnnouncementNotFoundException;

    public List<Announcement> getAllAnnouncements();

    public Announcement retrieveAnnouncementById(Long id);

    public void deleteAnnouncement(Long id);
}
