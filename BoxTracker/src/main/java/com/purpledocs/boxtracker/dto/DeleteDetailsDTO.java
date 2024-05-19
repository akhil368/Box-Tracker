package com.purpledocs.boxtracker.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class DeleteDetailsDTO {

	private int deleteId;
	
	private int userId;
	
	private String boxScanId;
}
