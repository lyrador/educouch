package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.model.ClassRun;
import com.educouch.educouchsystem.model.Course;
import com.educouch.educouchsystem.model.Event;
import com.educouch.educouchsystem.model.Instructor;
import com.educouch.educouchsystem.service.ClassRunService;
import com.educouch.educouchsystem.service.CourseService;
import com.educouch.educouchsystem.service.EducatorService;
import com.educouch.educouchsystem.service.EventService;
import com.educouch.educouchsystem.util.exception.EventNotFoundException;
import com.educouch.educouchsystem.util.exception.InstructorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/event")
@CrossOrigin
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private ClassRunService classRunService;

    @Autowired
    private EducatorService educatorService;

    @Autowired
    private CourseService courseService;

    @PostMapping("/classRun/{classRunId}/events")
    public ResponseEntity<Event> addEvent(@PathVariable(value="classRunId") Long classRunId, @RequestBody Event eventRequest) {

        ClassRun classRun = classRunService.retrieveClassRunById(classRunId);
        eventRequest.setClassRun(classRun);
        if (classRun.getEvents() != null) {
            classRun.getEvents().add(eventRequest);
        } else {
            List<Event> classRunEvents = new ArrayList<>();
            classRunEvents.add(eventRequest);
            classRun.setEvents(classRunEvents);
        }
        Event event = eventService.saveEvent(eventRequest);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @GetMapping("/events")
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> allEvents = new ArrayList<>();
        if (!eventService.getAllEvents().isEmpty()) {
            allEvents = eventService.getAllEvents();
            return new ResponseEntity<>(allEvents, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("{eventId}/events")
    public ResponseEntity<Event> getEventById(@PathVariable("eventId") Long eventId) {
        try {
            Event existingEvent = eventService.getEventById(eventId);
            return new ResponseEntity<Event>(existingEvent, HttpStatus.OK);
        } catch (EventNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/events/{eventId}")
    public ResponseEntity<Event> deleteEvent(@PathVariable("eventId") Long eventId) {
        try {
            Event existingEvent = eventService.getEventById(eventId);
            ClassRun classRun = existingEvent.getClassRun();
            classRun.getEvents().remove(existingEvent);
            existingEvent.setClassRun(null);
            eventService.deleteEvent(eventId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EventNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/events/{eventId}")
    public ResponseEntity<Event> updateEvent(@RequestBody Event event, @PathVariable("eventId") Long eventId) {
        try {
            Event existingEvent = eventService.getEventById(eventId);
            existingEvent.setTitle(event.getTitle());
            existingEvent.setNotes(event.getNotes());
            existingEvent.setStartDate(event.getStartDate());
            existingEvent.setEndDate(event.getEndDate());
            existingEvent.setAllDay(event.getAllDay());
            //existingEvent.setClassRun(event.getClassRun());
            eventService.saveEvent(existingEvent);
            return new ResponseEntity<Event>(existingEvent, HttpStatus.OK);
        } catch (EventNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<Event>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/classRuns/{classRunId}/events")
    public ResponseEntity<List<Event>> getAllEventsByClassRunId (@PathVariable(value="classRunId") Long classRunId) {
        ClassRun classRun = classRunService.retrieveClassRunById(classRunId);
        List<Event> events = new ArrayList<>();
        events.addAll(classRun.getEvents());
        for (Event event : events) {
            Date eventStartDate = event.getStartDate();
            LocalDateTime ldtStart = LocalDateTime.ofInstant(eventStartDate.toInstant(), ZoneId.systemDefault());
            ldtStart = ldtStart.plusHours(8);
            event.setStartDate(Date.from(ldtStart.atZone(ZoneId.systemDefault()).toInstant()));

            Date eventEndDate = event.getEndDate();
            LocalDateTime ldtEnd = LocalDateTime.ofInstant(eventEndDate.toInstant(), ZoneId.systemDefault());
            ldtEnd = ldtEnd.plusHours(8);
            event.setEndDate(Date.from(ldtEnd.atZone(ZoneId.systemDefault()).toInstant()));
        }
        return new ResponseEntity<>(events, HttpStatus.OK);

    }

    @GetMapping("/instructors/{instructorId}/events")
    public ResponseEntity<List<Event>> getAllEventsByInstructorId (@PathVariable(value="instructorId") Long instructorId) {
        try {
            Instructor instructor = educatorService.findInstructorById(instructorId);
            List<Event> events = new ArrayList<>();
            List<ClassRun> classRunListInstructor = instructor.getClassRuns();
            for (ClassRun classRun : classRunListInstructor) {
                events.addAll(classRun.getEvents());
            }
            return new ResponseEntity<>(events, HttpStatus.OK);
        } catch (InstructorNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/courses/{courseId}/events")
    public ResponseEntity<List<Event>> getAllEventsByCourseId (@PathVariable(value="courseId") Long courseId) {
        Course course = courseService.retrieveCourseById(courseId);
        List<Event> events = new ArrayList<>();
        List<ClassRun> classRunListCourse = course.getClassRuns();
        for (ClassRun classRun : classRunListCourse) {
            events.addAll(classRun.getEvents());
        }
        return new ResponseEntity<>(events, HttpStatus.OK);
    }


}
