package com.purpledocs.boxtracker.dto;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoxDTO {

	private int boxId;

	@NotBlank(message = "Box Scan Id cannot be blank")
	@NotNull(message = "Box Scan Id cannot be Null")
	@NotEmpty(message = "Box Scan id cannot be empty")
	@Column(unique = true)
	private String boxScanId;

	@NotBlank(message = "Box Details Cannot be Blank")
	private List<BoxDetailsDTO> boxDetailsList;

}
