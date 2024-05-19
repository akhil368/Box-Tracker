package com.purpledocs.boxtracker.service;

import java.util.List;

import com.purpledocs.boxtracker.dto.BoxDTO;

public interface BoxService {

	public BoxDTO addBox(BoxDTO boxDTO);

	public BoxDTO updateBox(BoxDTO boxDTO);

	public Boolean deleteBox(Integer id);

	public BoxDTO getBoxById(Integer id);

	public List<BoxDTO> getAllBoxes();

	public BoxDTO getBoxByScanId(String boxScanId);

}
