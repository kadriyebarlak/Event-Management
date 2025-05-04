package com.eventmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PerformerRequest {
	
	@NotBlank(message = "Name is required")
    private String name;
	
	@NotBlank(message = "Role is required")
    private String role;

	@NotBlank(message = "Biography is required")
    private String biography;

}
