package com.purpledocs.boxtracker.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LocationDTO {

	private int locationId;
	
	
//	@NotBlank(message = "cannot be blank")
//	   @Size(min = 1, max = 50)
	private String locationName;
	
	public LocationDTO() {
		super();
		
	}
	
	

	
	
}
