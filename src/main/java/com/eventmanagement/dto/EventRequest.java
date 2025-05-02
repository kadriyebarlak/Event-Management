package com.eventmanagement.dto;

import java.time.LocalDateTime;
import java.util.Set;

import com.eventmanagement.enums.EventType;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EventRequest {
	
	private Long eventId;
	
	@NotBlank(message = "Name is required")
    private String name;

	@NotBlank(message = "Summary is required")
    private String summary;

	@NotBlank(message = "Description is required")
    private String description;
    
	@NotNull(message = "Event Type is required")
    private EventType eventType;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@NotNull(message = "Start Time is required")
    private LocalDateTime startDateTime;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@NotNull(message = "End Time is required")
    private LocalDateTime endDateTime;
	
	private Set<Long> performerIds;

}
