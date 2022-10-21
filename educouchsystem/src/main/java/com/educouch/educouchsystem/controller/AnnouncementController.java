package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.dto.AnnouncementDTO;
import com.educouch.educouchsystem.dto.ForumDTO;
import com.educouch.educouchsystem.model.Announcement;
import com.educouch.educouchsystem.model.Course;
import com.educouch.educouchsystem.model.Forum;
import com.educouch.educouchsystem.service.AnnouncementService;
import com.educouch.educouchsystem.service.CourseService;
import com.educouch.educouchsystem.service.EducatorService;
import com.educouch.educouchsystem.util.exception.InstructorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/announcement")
@CrossOrigin
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private EducatorService educatorService;

    @PostMapping("/addAnnouncementToCourseByCourseId/{courseId}")
    public ResponseEntity<Announcement> addAnnouncementToCourse(@PathVariable(value="courseId") Long courseId, @RequestBody AnnouncementDTO announcementDTO) {
        try {
            Course course = courseService.retrieveCourseById(courseId);
            Announcement newAnnouncement = new Announcement();
            newAnnouncement.setAnnouncementTitle(announcementDTO.getAnnouncementTitle());
            newAnnouncement.setAnnouncementBody(announcementDTO.getAnnouncementBody());
            newAnnouncement.setTimestamp(LocalDateTime.now());
            //Ensure that announcement can only be created by Instructors
            if (announcementDTO.getCreatedByUserType().equals("INSTRUCTOR")) {
                newAnnouncement.setCreatedByInstructor(educatorService.findInstructorById(announcementDTO.getCreatedByUserId()));
            }

            course.getAnnouncements().add(newAnnouncement);

            Announcement announcement = announcementService.saveAnnouncement(newAnnouncement);
            return new ResponseEntity<>(announcement, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (InstructorNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAllAnnouncementsByCourseId/{courseId}")
    public ResponseEntity<List<AnnouncementDTO>> getAllAnnouncementsByCourseId (@PathVariable(value="courseId") Long courseId) {
        try {
            Course course = courseService.retrieveCourseById(courseId);
            List<Announcement> announcements = new ArrayList<Announcement>();
            announcements.addAll(course.getAnnouncements());

            DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.MEDIUM);

            List<AnnouncementDTO> announcementDTOs = new ArrayList<>();
            for (Announcement announcement : announcements) {
                AnnouncementDTO announcementDTO = new AnnouncementDTO();
                announcementDTO.setAnnouncementId(announcement.getAnnouncementId());
                announcementDTO.setAnnouncementTitle(announcement.getAnnouncementTitle());
                announcementDTO.setAnnouncementBody(announcement.getAnnouncementBody());
                announcementDTO.setCreatedDateTime(announcement.getTimestamp().format(formatter));
                if (announcement.getCreatedByInstructor() != null) {
                    announcementDTO.setCreatedByUserId(announcement.getCreatedByInstructor().getInstructorId());
                    announcementDTO.setCreatedByUserName(announcement.getCreatedByInstructor().getName());
                    announcementDTO.setCreatedByUserType("INSTRUCTOR");
                }
                announcementDTOs.add(announcementDTO);
            }
            return new ResponseEntity<>(announcementDTOs, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAnnouncementById/{announcementId}")
    public ResponseEntity<Announcement> getAnnouncementById(@PathVariable(value = "announcementId") Long announcementId) {
        try {
            Announcement existingAnnouncement = announcementService.retrieveAnnouncementById(announcementId);
            return new ResponseEntity<Announcement>(existingAnnouncement, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateAnnouncementById/{announcementId}")
    public ResponseEntity<Announcement> updateAnnouncementById(@PathVariable("announcementId") Long announcementId, @RequestBody AnnouncementDTO announcementDTO) {
        try {
            Announcement existingAnnouncement = announcementService.retrieveAnnouncementById(announcementId);
            existingAnnouncement.setAnnouncementTitle(announcementDTO.getAnnouncementTitle());
            existingAnnouncement.setAnnouncementBody(announcementDTO.getAnnouncementBody());
            return new ResponseEntity<Announcement>(announcementService.saveAnnouncement(existingAnnouncement), HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteAnnouncementById/{announcementId}")
    public ResponseEntity<HttpStatus> deleteAnnouncementById(@PathVariable("announcementId") Long announcementId) {
        announcementService.deleteAnnouncement(announcementId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
