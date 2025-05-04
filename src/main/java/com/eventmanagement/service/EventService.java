package com.eventmanagement.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.eventmanagement.dto.EventRequest;
import com.eventmanagement.entity.Event;
import com.eventmanagement.entity.Performer;
import com.eventmanagement.repository.EventRepository;

@Service
public class EventService {
	
	private static final Logger log = LoggerFactory.getLogger(EventService.class);

	private final EventRepository eventRepository;
	private final PerformerService performerService;

    public EventService(EventRepository eventRepository, PerformerService performerService) {
        this.eventRepository = eventRepository;
        this.performerService = performerService;
    }

    public Event createEvent(EventRequest request) {
    	log.info("Saving new event");
        Event event = new Event();
        event.setName(request.getName());
        event.setSummary(request.getSummary());
        event.setDescription(request.getDescription());
        event.setStartDateTime(request.getStartDateTime());
        event.setEndDateTime(request.getEndDateTime());
        event.setEventType(request.getEventType());
        event.setCreatedAt(LocalDateTime.now());
        
        Integer createdByUser = (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        event.setCreatedByUser(createdByUser);
        
        Set<Performer> performers = performerService.getPerformersByIds(request.getPerformerIds());
        event.setPerformers(performers);
        
        event = eventRepository.save(event);
        log.info("Saved new event id {}", event.getId());
		return event;
    }

    public Event updateEvent(Long id, EventRequest request) {
    	log.info("Finding and updating event id {}", id);
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event with id " + id + " not found."));

        event.setName(request.getName());
        event.setSummary(request.getSummary());
        event.setDescription(request.getDescription());
        event.setStartDateTime(request.getStartDateTime());
        event.setEndDateTime(request.getEndDateTime());
        event.setEventType(request.getEventType());
        event.setUpdatedAt(LocalDateTime.now());
        
        Integer updatedByUser = (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        event.setUpdatedByUser(updatedByUser);
        
        Set<Performer> performers = performerService.getPerformersByIds(request.getPerformerIds());
        event.setPerformers(performers);

        Event updatedEvent = eventRepository.save(event);
        log.info("Updated event id {}", id);
		return updatedEvent;
    }

    public List<Event> getEventsByDateRange(LocalDate startDate, LocalDate endDate) {
    	log.info("Retrieving events between {} and {}", startDate, endDate);
    	List<Event> events;

        if (startDate == null || endDate == null) {
            events = eventRepository.findAll();
        } else {
            LocalDateTime start = startDate.atStartOfDay();
            LocalDateTime end = endDate.atTime(LocalTime.MAX);
            events = eventRepository.findByStartDateTimeBetween(start, end);
        }

        return events.stream()
                     .filter(Event::getIsActive)
                     .collect(Collectors.toList());
    }
    
    public void deleteEvent(Long id) {
    	log.info("Deleting event with id {}", id);
    	Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event with id " + id + " not found."));
		event.setIsActive(false); //soft delete
        log.info("Deleted event with id {}", id);
    }
    
}
