package com.purpledocs.boxtracker.entity;

import java.sql.Timestamp;

import com.purpledocs.boxtracker.type.BoxStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class BoxDetails {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int boxDetailId;
	
	@Positive(message = "Value must be positive")
	 @Column(name = "`by`")
	private int by;
	

	@Enumerated(EnumType.STRING)
	private BoxStatus status;

	

	
	@NotBlank(message = "Location Id cannot be blank")
	@NotNull(message = "Location Id cannot be Null")
	@NotEmpty(message = "Location Id cannot be empty")
	private String location;
	
	private Timestamp timestamp;
	
	
	
	
	
}
