package com.eventmanagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;

import com.eventmanagement.dto.EventRequest;
import com.eventmanagement.entity.Event;
import com.eventmanagement.entity.Performer;
import com.eventmanagement.enums.EventType;
import com.eventmanagement.repository.EventRepository;
import com.eventmanagement.service.EventService;
import com.eventmanagement.service.PerformerService;

class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private PerformerService performerService;

    @InjectMocks
    private EventService eventService;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getPrincipal()).thenReturn(42); // Dummy user id
    }

    @Test
    void createEvent_shouldSaveEventWithPerformers() {
        EventRequest request = new EventRequest();
        request.setName("Test Event");
        request.setSummary("Summary");
        request.setDescription("Description");
        request.setEventType(EventType.CONCERT);
        request.setStartDateTime(LocalDateTime.now());
        request.setEndDateTime(LocalDateTime.now().plusHours(2));
        request.setPerformerIds(Set.of(1L));

        Set<Performer> performers = new HashSet<>();
        performers.add(new Performer());

        when(performerService.getPerformersByIds(Set.of(1L))).thenReturn(performers);
        when(eventRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Event result = eventService.createEvent(request);

        assertEquals("Test Event", result.getName());
        assertEquals(performers, result.getPerformers());
        assertEquals(42, result.getCreatedByUser());
    }

    @Test
    void updateEvent_shouldUpdateAndSaveEvent() {
        Long eventId = 1L;
        Event existing = new Event();
        existing.setId(eventId);
        existing.setIsActive(true);

        EventRequest request = new EventRequest();
        request.setName("Updated Event");
        request.setSummary("New Summary");
        request.setDescription("New Description");
        request.setEventType(EventType.THEATER);
        request.setStartDateTime(LocalDateTime.now());
        request.setEndDateTime(LocalDateTime.now().plusHours(2));
        request.setPerformerIds(Set.of(2L));

        Set<Performer> performers = Set.of(new Performer());

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(existing));
        when(performerService.getPerformersByIds(Set.of(2L))).thenReturn(performers);
        when(eventRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Event updated = eventService.updateEvent(eventId, request);

        assertEquals("Updated Event", updated.getName());
        assertEquals(EventType.THEATER, updated.getEventType());
        assertEquals(42, updated.getUpdatedByUser());
    }

    @Test
    void updateEvent_shouldThrowIfNotFound() {
        when(eventRepository.findById(1L)).thenReturn(Optional.empty());
        EventRequest request = new EventRequest();
        assertThrows(ResponseStatusException.class, () -> eventService.updateEvent(1L, request));
    }

    @Test
    void getEventsByDateRange_shouldReturnAllWhenNoParams() {
        Event e1 = new Event(); e1.setIsActive(true);
        Event e2 = new Event(); e2.setIsActive(false);

        when(eventRepository.findAll()).thenReturn(List.of(e1, e2));

        List<Event> result = eventService.getEventsByDateRange(null, null);

        assertEquals(1, result.size());
        assertTrue(result.contains(e1));
    }

    @Test
    void getEventsByDateRange_shouldFilterByDates() {
        Event e1 = new Event(); e1.setStartDateTime(LocalDateTime.now()); e1.setIsActive(true);

        when(eventRepository.findByStartDateTimeBetween(any(), any())).thenReturn(List.of(e1));

        List<Event> result = eventService.getEventsByDateRange(LocalDate.now(), LocalDate.now());

        assertEquals(1, result.size());
    }

    @Test
    void deleteEvent_shouldSetIsActiveFalse() {
        Event event = new Event();
        event.setId(1L);
        event.setIsActive(true);

        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

        eventService.deleteEvent(1L);

        assertFalse(event.getIsActive());
    }

    @Test
    void deleteEvent_shouldThrowIfNotFound() {
        when(eventRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> eventService.deleteEvent(1L));
    }
}

