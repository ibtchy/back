package com.sofrecom.cobli.models;




import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"cuid", "startDate", "endDate"}))
public class Event implements Serializable {

    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean allDay;
    private boolean validated = false;
    
    
    
    
// getters and setters
    public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public boolean isAllDay() {
		return allDay;
	}

	public void setAllDay(boolean allDay) {
		this.allDay = allDay;
	}

	public boolean isValidated() {
		return validated;
	}

	public void setValidated(boolean validated) {
		this.validated = validated;
	}

	public Collaborateur getCollaborateur() {
		return collaborateur;
	}

	public void setCollaborateur(Collaborateur collaborateur) {
		this.collaborateur = collaborateur;
	}

	public Prestation getPrestation() {
		return prestation;
	}

	public void setPrestation(Prestation prestation) {
		this.prestation = prestation;
	}
	
	
	// const with fields
	public Event(Long eventId, LocalDateTime startDate, LocalDateTime endDate, boolean allDay, boolean validated,
			Collaborateur collaborateur, Prestation prestation) {
		super();
		this.eventId = eventId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.allDay = allDay;
		this.validated = validated;
		this.collaborateur = collaborateur;
		this.prestation = prestation;
	}
	
	// const no fields
	
	
	public Event() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	// toString 
	
	  @Override
		public String toString() {
			return "Event [eventId=" + eventId + ", startDate=" + startDate + ", endDate=" + endDate + ", allDay=" + allDay
					+ ", validated=" + validated + ", collaborateur=" + collaborateur + ", prestation=" + prestation + "]";
		}
	

	@ManyToOne
    @JoinColumn(name = "CUID")
    private Collaborateur collaborateur;

 
	@ManyToOne
    @JoinColumn(name = "prestationId")
    private Prestation prestation;

}