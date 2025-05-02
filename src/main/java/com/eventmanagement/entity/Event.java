package com.eventmanagement.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.eventmanagement.enums.EventType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "event")
@Getter
@Setter
public class Event {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    private String name;
    private String summary;
    private String description;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Integer createdByUser;
    private LocalDateTime createdAt;
    private Integer updatedByUser;
    private LocalDateTime updatedAt;
    
    //TODO: Add to the DB via Flyway migration.
    private Boolean isActive = true;
    
    @ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "event_performer", joinColumns = @JoinColumn(name = "event_id"), inverseJoinColumns = @JoinColumn(name = "performer_id"))
	private Set<Performer> performers = new HashSet<>();
    
}
