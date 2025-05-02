package com.eventmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventmanagement.entity.Performer;

public interface PerformerRepository extends JpaRepository<Performer, Long> {

}
