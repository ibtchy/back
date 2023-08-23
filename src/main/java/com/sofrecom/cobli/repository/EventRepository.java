package com.sofrecom.cobli.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.sofrecom.cobli.models.Collaborateur;
import com.sofrecom.cobli.models.Event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
	
        List<Event> findAllByStartDateBetween(LocalDateTime startDate, LocalDateTime endDate);
        List<Event> findByCollaborateurAndStartDateBetween(Collaborateur collaborateur, LocalDateTime startDate, LocalDateTime endDate);
        List<Event> findAllByCollaborateur(Collaborateur collaborateur);
        Event findEventByStartDate(LocalDateTime startDate);
        Optional<Event> findFirstByOrderByStartDateDesc();

}
