package com.sofrecom.cobli.controller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sofrecom.cobli.models.Collaborateur;
import com.sofrecom.cobli.models.Event;
import com.sofrecom.cobli.repository.CollaborateurRepository;
import com.sofrecom.cobli.repository.EventRepository;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ServiceEvent implements IServiceEvent{
    @Autowired
    EventRepository eventRepository;
    @Autowired
    private CollaborateurRepository collaborateurRepository;

    @Override
    public Event getEventById(Long eventId) {
        return eventRepository.findById(eventId).orElse(null);
    }



    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public  boolean test(Event event) {
        LocalDateTime currentDate = event.getStartDate();
        LocalDateTime startOfPreviousWeek = currentDate.minusWeeks(1).with(DayOfWeek.MONDAY);
        LocalDateTime endOfPreviousWeek = currentDate.minusWeeks(1).with(DayOfWeek.FRIDAY).withHour(23).withMinute(59).withSecond(59);
        List<Event> existingEvents = eventRepository.findAll();
        List<Event> listEvents = eventRepository.findAllByStartDateBetween(startOfPreviousWeek,endOfPreviousWeek);

        boolean res = false;
        if(!existingEvents.isEmpty()){


            if (!listEvents.isEmpty()) {
                for (Event evenement : listEvents) {
                    if (evenement.isValidated()) {
                        res = true;
                    } else {
                        res = false;
                    }
                }

            } else if ( isTimeDifferenceLessThan12Days(event)){
                res = true;
            }
            else res = false;

        }else {
            res = true;
        }
        return res;
    }

    private boolean isTimeDifferenceLessThan12Days(Event event) {
        Optional<Event> optionalLatestEvent = eventRepository.findFirstByOrderByStartDateDesc();

        if (optionalLatestEvent.isPresent()) {
            Event latestEvent = optionalLatestEvent.get();

            LocalDateTime newStartDate = event.getStartDate();
            LocalDateTime latestEndDate = latestEvent.getEndDate();
            // Calculate the difference in days
            Duration duration = Duration.between(latestEndDate, newStartDate);
            long daysDifference = duration.toDays();
            return daysDifference < 9;
        } else {
            return false;
        }
    }


    @Override
    public Event addEvent(Event event) {
        if (event == null) {
            throw new IllegalArgumentException("Event cannot be null.");
        }

        if (event.getStartDate().isEqual(event.getEndDate())) {
            throw new IllegalArgumentException("Les dates de début et de fin de l'événement sont les mêmes.");
        }

        List<Event> existingEvents = eventRepository.findAllByCollaborateur(event.getCollaborateur());
        if (existingEvents != null && !existingEvents.isEmpty()) {
            for (Event existingEvent : existingEvents) {
                if (existingEvent.getEndDate().isAfter(event.getStartDate()) && existingEvent.getStartDate().isBefore(event.getEndDate())) {
                    throw new IllegalArgumentException("Le chevauchement des intervalles de temps pour le même collaborateur n'est pas autorisé.");
                }
            }
        }

        if (!test(event)) {
            throw new IllegalArgumentException("L'événement ne satisfait pas les critères de validation.");
        }

        return eventRepository.save(event);

}
    @Override
    public Event updateEvent(Event event) {
        if (event == null) {
            throw new IllegalArgumentException("Event cannot be null.");
        }

        if (event.getStartDate().isEqual(event.getEndDate())) {
            throw new IllegalArgumentException("Les dates de début et de fin de l'événement sont les mêmes.");
        }

        List<Event> existingEvents = eventRepository.findAllByCollaborateur(event.getCollaborateur());
        if (existingEvents != null && !existingEvents.isEmpty()) {
            for (Event existingEvent : existingEvents) {
                // Skip the current event being updated during the check
                if (existingEvent.getEventId().equals(event.getEventId())) {
                    continue;
                }

                if (existingEvent.getEndDate().isAfter(event.getStartDate()) && existingEvent.getStartDate().isBefore(event.getEndDate())) {
                    throw new IllegalArgumentException("Le chevauchement des intervalles de temps pour le même collaborateur n'est pas autorisé.");
                }
            }
        }
        if (!test(event)) {
            throw new IllegalArgumentException("L'événement ne satisfait pas les critères de validation.");
        }

        return eventRepository.save(event);
    }


    @Override
    public void deleteEvent(Long eventId) {
        Event event = eventRepository.findById(eventId).orElse(null);

        // Check if the event is not validated before allowing deletion
        if (event.isValidated()) {
            throw new IllegalArgumentException("Impossible de supprimer un événement validé.");
        }

        eventRepository.deleteById(eventId);
    }

    @Override
    public List<Event> findEventsBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        return eventRepository.findAllByStartDateBetween(startDate, endDate);
    }


    @Override
    public List<Event> findAllByCollaborateur(Collaborateur collaborateur) {
        return eventRepository.findAllByCollaborateur(collaborateur);
    }


    public List<Event> findEventsBetweenDatesByCollaborateur(LocalDateTime startDate, LocalDateTime endDate, Collaborateur collaborateur) {
        List<Event> allEventsBetweenDates = eventRepository.findAllByStartDateBetween(startDate, endDate);
        List<Event> eventsForCollaborateur = new ArrayList<>();
        for (Event event : allEventsBetweenDates) {
            if (event.getCollaborateur().equals(collaborateur)) {
                eventsForCollaborateur.add(event);
            }
        }
        return eventsForCollaborateur;
    }


    public void validateEvents(List<Event> eventsToValidate) {


        if (eventsToValidate.isEmpty()) {
            throw new IllegalArgumentException("Aucun événement à valider pour cette semaine.");
        }else{
        Map<Integer, List<Event>> eventsByWeek = eventsToValidate.stream()
                .collect(Collectors.groupingBy(event -> getWeekNumber(event.getStartDate().toLocalDate())));

        for (List<Event> eventsForWeek : eventsByWeek.values()) {
            long totalDurationInHours = eventsForWeek.stream()
                    .mapToLong(event -> ChronoUnit.HOURS.between(event.getStartDate(), event.getEndDate()))
                    .sum();

            if (totalDurationInHours == 40) {
                eventsForWeek.forEach(event -> {
                    event.setValidated(true);
                    eventRepository.save(event);
                });
            } else {
                throw new IllegalArgumentException("Événements invalides pour cette semaine");
            }
        }
    }}
    private int getWeekNumber(LocalDate date) {
        return date.get(WeekFields.ISO.weekOfWeekBasedYear());
    }


}