package com.purpledocs.boxtracker.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class DeleteDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int deleteId;
	
	
	private int userId;
	
	
	@NotBlank(message = "BoxScanId cannot be blank")
	@NotNull(message = "BoxScanId  cannot be Null")
	@NotEmpty(message = "BoxScanId  cannot be empty")
	private String boxScanId;
}
