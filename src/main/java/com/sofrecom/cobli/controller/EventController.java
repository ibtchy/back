package com.sofrecom.cobli.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sofrecom.cobli.controller.service.IServiceCollaborateur;
import com.sofrecom.cobli.controller.service.IServiceEvent;
import com.sofrecom.cobli.models.Collaborateur;
import com.sofrecom.cobli.models.Event;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/event")

public class EventController {
	
	@Autowired
    IServiceEvent serviceEvent;
    
    @Autowired
    IServiceCollaborateur serviceCollaborateur;

    @GetMapping("/getAllEvents")
    public List<Event> getAllEvents() {
        return serviceEvent.getAllEvents();
    }

    @GetMapping("/getEventById/{eventId}")
    public Event getEventById(@PathVariable Long eventId) {
        return serviceEvent.getEventById(eventId);
    }
    @PostMapping("/getAllEventsByCollaborateur")
    public List<Event> findAllByCollaborateur(@RequestBody Collaborateur collaborateur) {
        return serviceEvent.findAllByCollaborateur(collaborateur);
    }

    @PostMapping("/addEvent")
    public ResponseEntity<Object> addEvent(@RequestBody Event event) {
        try {
            Event savedEvent = serviceEvent.addEvent(event);
            return ResponseEntity.ok(savedEvent);
        } catch (IllegalArgumentException e) {
            String errorMessage = e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
    }


    @PostMapping("/test")
    public  boolean test(@RequestBody Event event) {
       return serviceEvent.test(event);
    }


    @PutMapping("/updateEvent")
    public Event updateEvent(@RequestBody Event event) {
        return serviceEvent.updateEvent(event);
    }

    @DeleteMapping("/deleteEvent/{eventId}")
    public void deleteEvent(@PathVariable Long eventId) {
        serviceEvent.deleteEvent(eventId);
    }

    @GetMapping("/getEventsBetweenDates/{startDate}/{endDate}")
    public ResponseEntity<List<Event>> getEventsBetweenDates(
            @PathVariable("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @PathVariable("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    ) {
        List<Event> events = serviceEvent.findEventsBetweenDates(startDate, endDate);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }


    @PostMapping("/getEventsBetweenDatesByCollaborateur")
    public List<Event> getEventsBetweenDatesByCollaborateur(
            @RequestBody Map<String, Object> requestBody) {

        LocalDateTime startDate = LocalDateTime.parse(requestBody.get("startDate").toString());
        LocalDateTime endDate = LocalDateTime.parse(requestBody.get("endDate").toString());
        String CUID = requestBody.get("CUID").toString();

        Collaborateur collaborateur = serviceCollaborateur.getCollaborateurById(CUID);

        return serviceEvent.findEventsBetweenDatesByCollaborateur(startDate, endDate, collaborateur);
    }

    @PutMapping("/validate-week")
    public ResponseEntity<Object> updateAndValidateWeekEvents(@RequestBody List<Event> eventsToUpdate) {
        try {
            serviceEvent.validateEvents(eventsToUpdate);
            return ResponseEntity.ok().body("Les événements sont validés avec succès.");
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            return ResponseEntity.badRequest().body(errorMessage);
        }
    }

}
