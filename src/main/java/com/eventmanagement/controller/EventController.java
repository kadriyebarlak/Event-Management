package com.eventmanagement.controller;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eventmanagement.dto.EventRequest;
import com.eventmanagement.dto.EventResponse;
import com.eventmanagement.service.EventService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/events")
public class EventController {
	
	private static final Logger log = LoggerFactory.getLogger(EventController.class);
	
	@GetMapping("/hello")
    public String hello() {
      return String.format("Event Management Project is up!");
    }
	
	private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<EventResponse> createEvent(@RequestBody @Valid EventRequest request) {
    	log.info("Creating event: {}", request.getName());
    	EventResponse response = eventService.createEvent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventResponse> updateEvent(@PathVariable Long id, @RequestBody @Valid EventRequest request) {
    	log.info("Updating event id {}: {}", id, request.getName());
    	if (id == null || id <= 0) {
            throw new IllegalArgumentException("Event ID is required");
        }
    	EventResponse response = eventService.updateEvent(id, request);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    public ResponseEntity<List<EventResponse>> getEventsByDateRange(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy.MM.dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy.MM.dd") LocalDate endDate) {
    	log.info("Retrieving events from {} to {}", startDate, endDate);
    	List<EventResponse> events = eventService.getEventsByDateRange(startDate, endDate);
    	return ResponseEntity.ok(events);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long id) {
    	log.info("Deleting event with id {}", id);
        eventService.deleteEvent(id);
        return ResponseEntity.ok("Event deleted successfully");
    }

}
