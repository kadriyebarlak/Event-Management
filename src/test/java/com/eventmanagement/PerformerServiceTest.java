package com.eventmanagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import com.eventmanagement.dto.PerformerRequest;
import com.eventmanagement.entity.Performer;
import com.eventmanagement.repository.PerformerRepository;
import com.eventmanagement.service.PerformerService;

class PerformerServiceTest {

    @Mock
    private PerformerRepository performerRepository;

    @InjectMocks
    private PerformerService performerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPerformer_shouldSaveAndReturnPerformer() {
        PerformerRequest request = new PerformerRequest();
        request.setName("The Weeknd");
        request.setRole("Singer");
        request.setBiography("A great singer");

        Performer savedPerformer = new Performer();
        savedPerformer.setId(1L);
        savedPerformer.setName("The Weeknd");
        savedPerformer.setRole("Singer");
        savedPerformer.setBiography("A great singer");

        when(performerRepository.save(any())).thenReturn(savedPerformer);

        Performer result = performerService.createPerformer(request);

        assertEquals("The Weeknd", result.getName());
        assertEquals("Singer", result.getRole());
        assertEquals("A great singer", result.getBiography());
    }

    @Test
    void updatePerformer_shouldUpdateAndReturnPerformer() {
        Long id = 1L;
        Performer existing = new Performer();
        existing.setId(id);
        existing.setName("Old Name");

        PerformerRequest update = new PerformerRequest();
        update.setName("New Name");
        update.setRole("Guitarist");
        update.setBiography("Updated bio");

        when(performerRepository.findById(id)).thenReturn(Optional.of(existing));
        when(performerRepository.save(any())).thenReturn(existing);

        Performer result = performerService.updatePerformer(id, update);

        assertEquals("New Name", result.getName());
        assertEquals("Guitarist", result.getRole());
        assertEquals("Updated bio", result.getBiography());
    }

    @Test
    void updatePerformer_shouldThrowIfNotFound() {
        Long id = 1L;
        when(performerRepository.findById(id)).thenReturn(Optional.empty());

        PerformerRequest update = new PerformerRequest();
        update.setName("X");

        assertThrows(RuntimeException.class, () -> performerService.updatePerformer(id, update));
    }

    @Test
    void getAllPerformers_shouldReturnList() {
        Performer performer = new Performer();
        performer.setName("Someone");
        when(performerRepository.findAll()).thenReturn(Arrays.asList(performer));

        List<Performer> list = performerService.getAllPerformers();
        assertEquals(1, list.size());
        assertEquals("Someone", list.get(0).getName());
    }

    @Test
    void deletePerformer_shouldDeleteWhenExists() {
        Long id = 1L;
        Performer performer = new Performer();
        performer.setId(id);
        when(performerRepository.findById(id)).thenReturn(Optional.of(performer));

        performerService.deletePerformer(id);

        verify(performerRepository, times(1)).delete(performer);
    }

    @Test
    void deletePerformer_shouldThrowIfNotFound() {
        Long id = 1L;
        when(performerRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> performerService.deletePerformer(id));
    }

    @Test
    void getPerformersByIds_shouldReturnSet() {
        Performer performer = new Performer();
        performer.setId(1L);
        when(performerRepository.findAllById(Set.of(1L))).thenReturn(List.of(performer));

        Set<Performer> result = performerService.getPerformersByIds(Set.of(1L));
        assertEquals(1, result.size());
        assertTrue(result.stream().anyMatch(p -> p.getId().equals(1L)));
    }

    @Test
    void getPerformersByIds_shouldReturnEmptyIfInputEmpty() {
        Set<Performer> result = performerService.getPerformersByIds(Collections.emptySet());
        assertTrue(result.isEmpty());
        verify(performerRepository, never()).findAllById(any());
    }
}

