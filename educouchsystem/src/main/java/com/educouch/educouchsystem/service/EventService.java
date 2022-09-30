package com.educouch.educouchsystem.service;

import com.educouch.educouchsystem.model.Event;
import com.educouch.educouchsystem.util.exception.EventNotFoundException;

import java.util.List;

public interface EventService {

    public Event saveEvent(Event event);

    public List<Event> getAllEvents();

    public Event getEventById(Long eventId) throws EventNotFoundException;

    public void deleteEvent(Long id);

}
