package com.eventmanagement.dto;

import lombok.Getter;

@Getter
public class PerformerResponse {

	private Long id;
	private String name;
    private String role;
    private String biography;
    
	public PerformerResponse(Long id, String name, String role, String biography) {
		this.id = id;
		this.name = name;
		this.role = role;
		this.biography = biography;
	}
    
}
