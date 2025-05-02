package com.eventmanagement.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventmanagement.dto.PerformerRequest;
import com.eventmanagement.entity.Performer;
import com.eventmanagement.service.PerformerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/performers")
public class PerformerController {

    private static final Logger log = LoggerFactory.getLogger(PerformerController.class);

    private final PerformerService performerService;

    public PerformerController(PerformerService performerService) {
        this.performerService = performerService;
    }

    @PostMapping
    public Performer createPerformer(@RequestBody @Valid PerformerRequest performer) {
        log.info("Creating performer: {}", performer.getName());
        return performerService.createPerformer(performer);
    }

    @PutMapping("/{id}")
    public Performer updatePerformer(@PathVariable Long id, @RequestBody @Valid PerformerRequest performer) {
        log.info("Updating performer id {}: {}", id, performer.getName());
        if (performer.getId() == null || performer.getId() <= 0) {
            throw new IllegalArgumentException("Performer ID is required");
        }
        return performerService.updatePerformer(id, performer);
    }

    @GetMapping
    public List<Performer> listPerformers() {
        log.info("Listing all performers");
        return performerService.getAllPerformers();
    }

    @DeleteMapping("/{id}")
    public void deletePerformer(@PathVariable Long id) {
        log.info("Deleting performer with id {}", id);
        performerService.deletePerformer(id);
    }
}
