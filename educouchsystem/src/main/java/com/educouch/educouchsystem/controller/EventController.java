package com.educouch.educouchsystem.controller;

import com.educouch.educouchsystem.model.Event;
import com.educouch.educouchsystem.service.EventService;
import com.educouch.educouchsystem.util.exception.EventNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/event")
@CrossOrigin
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping("/events")
    public ResponseEntity<Event> addEvent(@RequestBody Event eventRequest) {

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
        eventService.deleteEvent(eventId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/events/{eventId}")
    public ResponseEntity<Event> updateEvent(@RequestBody Event event, @PathVariable("eventId") Long eventId) {
        try {
            Event existingEvent = eventService.getEventById(eventId);
            existingEvent.setEventName(event.getEventName());
            existingEvent.setEventDescription(event.getEventDescription());
            existingEvent.setEventStartDate(event.getEventStartDate());
            existingEvent.setEventEndDate(event.getEventEndDate());
            eventService.saveEvent(existingEvent);
            return new ResponseEntity<Event>(existingEvent, HttpStatus.OK);
        } catch (EventNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<Event>(HttpStatus.NOT_FOUND);
        }
    }


}
