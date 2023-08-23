package com.sofrecom.cobli.controller.service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.sofrecom.cobli.models.Collaborateur;
import com.sofrecom.cobli.models.Event;

public interface IServiceEvent {
    public Event getEventById(Long eventId);

    public  boolean test(Event event);
    public List<Event> getAllEvents();

    public Event addEvent(Event event);

    public Event updateEvent(Event event);

    public void deleteEvent(Long eventId);

    public List<Event> findEventsBetweenDates(LocalDateTime startDate, LocalDateTime endDate);

    public List<Event> findAllByCollaborateur(Collaborateur collaborateur);
    public List<Event> findEventsBetweenDatesByCollaborateur(LocalDateTime startDate, LocalDateTime endDate, Collaborateur collaborateur);
    public void validateEvents(List<Event> eventsToValidate);
}