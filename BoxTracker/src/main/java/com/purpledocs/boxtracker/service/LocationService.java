package com.purpledocs.boxtracker.service;

import java.util.List;

import com.purpledocs.boxtracker.dto.LocationDTO;

public interface LocationService {

public LocationDTO addLocation(LocationDTO locationDto);
	
	public LocationDTO updateLocation(LocationDTO locationDto);
	
	public LocationDTO getLocationById(Integer id);
	
	public Boolean deleteLocation(Integer id);
	
	public List<LocationDTO> getAllLocation();
}
