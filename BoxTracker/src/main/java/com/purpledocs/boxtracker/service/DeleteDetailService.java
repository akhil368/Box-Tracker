package com.purpledocs.boxtracker.service;

import java.util.List;

import com.purpledocs.boxtracker.dto.DeleteDetailsDTO;


public interface DeleteDetailService {

	public List<DeleteDetailsDTO> getAllDeleteDetails();
	
	public DeleteDetailsDTO getDeleteDetailsById(Integer id);
	
	public DeleteDetailsDTO updateDeleteDetails(DeleteDetailsDTO deleteDetailsDTO);
	
	public Boolean deleteDeleteDetails(Integer id);
}
