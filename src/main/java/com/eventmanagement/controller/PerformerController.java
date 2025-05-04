package com.eventmanagement.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventmanagement.dto.PerformerRequest;
import com.eventmanagement.dto.PerformerResponse;
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
    public ResponseEntity<PerformerResponse> createPerformer(@RequestBody @Valid PerformerRequest performer) {
        log.info("Creating performer: {}", performer.getName());
        PerformerResponse response = performerService.createPerformer(performer);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PerformerResponse> updatePerformer(@PathVariable Long id, @RequestBody @Valid PerformerRequest performer) {
        log.info("Updating performer id {}: {}", id, performer.getName());
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Performer ID is required");
        }
        PerformerResponse response = performerService.updatePerformer(id, performer);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<PerformerResponse>> listPerformers() {
        log.info("Listing all performers");
        List<PerformerResponse> response = performerService.getAllPerformers();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePerformer(@PathVariable Long id) {
        log.info("Deleting performer with id {}", id);
        performerService.deletePerformer(id);
        return ResponseEntity.ok("Performer deleted successfully");
    }
}
