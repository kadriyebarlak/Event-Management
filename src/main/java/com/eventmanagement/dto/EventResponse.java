package com.eventmanagement.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import com.eventmanagement.enums.EventType;

import lombok.Getter;

@SuppressWarnings("serial")
@Getter
public class EventResponse implements Serializable {
	
	private Long id;
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
    private Set<PerformerResponse> performers;
    
	public EventResponse(Long id, EventType eventType, String name, String summary, String description,
			LocalDateTime startDateTime, LocalDateTime endDateTime, Integer createdByUser, LocalDateTime createdAt,
			Integer updatedByUser, LocalDateTime updatedAt, Set<PerformerResponse> performers) {
		super();
		this.id = id;
		this.eventType = eventType;
		this.name = name;
		this.summary = summary;
		this.description = description;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.createdByUser = createdByUser;
		this.createdAt = createdAt;
		this.updatedByUser = updatedByUser;
		this.updatedAt = updatedAt;
		this.performers = performers;
	}
    
    

}
