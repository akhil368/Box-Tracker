package com.purpledocs.boxtracker.service;

import java.util.List;

import com.purpledocs.boxtracker.dto.BoxDetailsDTO;
import com.purpledocs.boxtracker.dto.BoxDetailsRequest;

public interface BoxDetailsService {

	public BoxDetailsDTO addBoxDetailsDTO(BoxDetailsRequest boxDetailsDTO);

	public BoxDetailsDTO getBoxDetailsByBoxIdAndStatus(Integer id,String Status);
	
	
	public BoxDetailsDTO updateBoxDetails(BoxDetailsDTO boxDetialsDto);
	
	public List<BoxDetailsDTO> getAllBoxDetails();
	
	public Boolean deleteBoxDetails(Integer id);

	public BoxDetailsDTO getBoxDetailsByBoxDetailsId(Integer id);
}
