package com.purpledocs.boxtracker.entity;



import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Box {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int boxId;
	
	@NotBlank(message = "Box Scan Id cannot be blank")
	@NotNull(message = "Box Scan Id cannot be Null")
	@NotEmpty(message = "Box Scan id cannot be empty")
	@Column(unique = true)
	private String  boxScanId;


	@OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "box_id")
    private List<BoxDetails> boxDetailsList;


	
	
}

//{
//	boxId,by,status,location,timestamp
//}

