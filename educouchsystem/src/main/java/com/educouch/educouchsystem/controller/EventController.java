package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.model.*;
import com.educouch.educouchsystem.service.*;
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

    @Autowired
    private LearnerService learnerService;

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

            //unmarshalling
            for (Event event : allEvents) {
                processClassRun(event.getClassRun());
            }

            return new ResponseEntity<>(allEvents, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("{eventId}/events")
    public ResponseEntity<Event> getEventById(@PathVariable("eventId") Long eventId) {
        try {
            Event existingEvent = eventService.getEventById(eventId);

            //unmarshalling
            processClassRun(existingEvent.getClassRun());

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

            processClassRun(existingEvent.getClassRun());

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
//        for (Event event : events) {
//            Date eventStartDate = event.getStartDate();
//            LocalDateTime ldtStart = LocalDateTime.ofInstant(eventStartDate.toInstant(), ZoneId.systemDefault());
//            ldtStart = ldtStart.plusHours(8);
//            event.setStartDate(Date.from(ldtStart.atZone(ZoneId.systemDefault()).toInstant()));
//
//            Date eventEndDate = event.getEndDate();
//            LocalDateTime ldtEnd = LocalDateTime.ofInstant(eventEndDate.toInstant(), ZoneId.systemDefault());
//            ldtEnd = ldtEnd.plusHours(8);
//            event.setEndDate(Date.from(ldtEnd.atZone(ZoneId.systemDefault()).toInstant()));
//        }

        //unmarshalling
        for (Event event : events) {
            event.getClassRun().setEvents(null);
            event.getClassRun().setEnrolledLearners(null);
            event.getClassRun().setEnrolmentStatusTrackers(null);
            event.getClassRun().setLearnerTransactions(null);
            event.getClassRun().setCourse(null);
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

            //unmarshalling
            for (Event event : events) {
                processClassRun(event.getClassRun());
            }

            return new ResponseEntity<>(events, HttpStatus.OK);
        } catch (InstructorNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/learner/{learnerId}/events")
    public ResponseEntity<List<Event>> getAllEventsByLearnerId (@PathVariable(value="learnerId") Long learnerId) {
        try {
            Learner learner = learnerService.getLearnerById(learnerId);
            List<Event> events = new ArrayList<>();
            List<ClassRun> classRunListLearner= learner.getClassRuns();
            for (ClassRun classRun : classRunListLearner) {
                events.addAll(classRun.getEvents());
            }

            //unmarshalling
            for (Event event : events) {
                processClassRun(event.getClassRun());
            }

            return new ResponseEntity<>(events, HttpStatus.OK);
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

        //unmarshalling
        for (Event event : events) {
            processClassRun(event.getClassRun());
        }

        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    private void processClassRun(ClassRun c) {
//        List<EnrolmentStatusTracker> enrolmentStatusTrackers = c.getEnrolmentStatusTrackers();
//        for(EnrolmentStatusTracker e: enrolmentStatusTrackers) {
//            e.setClassRun(null);
//            e.setLearner(null);
//        }

        c.setCalendar(null);
        c.setEvents(null);
        c.setTriviaQuizzes(null);
//        Instructor i = c.getInstructor();
//        if(i != null) {
//            i.setClassRuns(null);
//            i.setOrganisation(null);
//            i.setCourses(null);
//        }


        Course course = c.getCourse();
        course.setFolders(null);
        course.setClassRuns(null);
        course.setFolders(null);
        course.setAssessments(null);
//        course.setInstructors(null);
        course.setOrganisation(null);

        c.setEnrolledLearners(null);
        c.setEnrolmentStatusTrackers(null);
        c.setLearnerTransactions(null);
    }


}
