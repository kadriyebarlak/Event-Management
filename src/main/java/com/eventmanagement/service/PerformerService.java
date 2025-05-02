package com.eventmanagement.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.eventmanagement.dto.PerformerRequest;
import com.eventmanagement.entity.Performer;
import com.eventmanagement.repository.PerformerRepository;

@Service
public class PerformerService {
	
	private static final Logger log = LoggerFactory.getLogger(PerformerService.class);
	
	private final PerformerRepository performerRepository;

    public PerformerService(PerformerRepository performerRepository) {
        this.performerRepository = performerRepository;
    }
    
    public Set<Performer> getPerformersByIds(Set<Long> performerIds) {
        if (performerIds == null || performerIds.isEmpty()) {
            return Collections.emptySet();
        }
        return performerRepository.findAllById(performerIds).stream().collect(Collectors.toSet());
    }
    
    public Performer createPerformer(PerformerRequest request) {
    	log.info("Saving new performer");
    	Performer performer = new Performer();
    	performer.setName(request.getName());
    	performer.setRole(request.getRole());
    	performer.setBiography(request.getBiography());
        
    	performer = performerRepository.save(performer);
        log.info("Saved new performer id {}", performer.getId());
		return performer;
    }

    public Performer updatePerformer(Long id, PerformerRequest updatedPerformer) {
        Optional<Performer> existing = performerRepository.findById(id);
        if (existing.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Performer with id " + id + " not found.");
        }

        Performer performer = existing.get();
        performer.setName(updatedPerformer.getName());
        performer.setRole(updatedPerformer.getRole());
        performer.setBiography(updatedPerformer.getBiography());

        return performerRepository.save(performer);
    }

    public List<Performer> getAllPerformers() {
        return performerRepository.findAll();
    }

    public void deletePerformer(Long id) {
    	log.info("Deleting performer with id {}", id);
		Performer performer = performerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Performer with id " + id + " not found."));
		performerRepository.delete(performer);
        log.info("Deleted performer with id {}", id);
    }

}
