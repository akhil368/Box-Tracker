package com.purpledocs.boxtracker.dto;

import java.sql.Timestamp;

import com.purpledocs.boxtracker.type.BoxStatus;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoxDetailsDTO {

	private int boxDetailId;

	private int by;
	
	private BoxStatus status;
	

	private String location;
	
	private Timestamp timestamp;
}
