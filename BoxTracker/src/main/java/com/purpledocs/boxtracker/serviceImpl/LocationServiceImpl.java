package com.purpledocs.boxtracker.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.purpledocs.boxtracker.dto.LocationDTO;
import com.purpledocs.boxtracker.entity.Location;
import com.purpledocs.boxtracker.repository.LocationRepository;
import com.purpledocs.boxtracker.service.LocationService;
import com.purpledocs.boxtracker.util.ObjectMapperUtils;

@Service
public class LocationServiceImpl implements LocationService {

	@Autowired
	private LocationRepository locationRepo;
	
	
	@Override
	public LocationDTO addLocation(LocationDTO locationDto) {
		Location location=ObjectMapperUtils.map(locationDto,Location.class);
		System.out.println(location + "location");
		location=locationRepo.save(location);
		return ObjectMapperUtils.map(location,LocationDTO.class);
	}

	@Override
	public LocationDTO updateLocation(LocationDTO locationDto) {
		// TODO Auto-generated method stub
		Optional<Location> opt=locationRepo.findById(locationDto.getLocationId());
		if(opt.isPresent())
		{
			Location location=opt.get();
			location.setLocationName(locationDto.getLocationName());
			location=locationRepo.save(location);
			return ObjectMapperUtils.map(location, LocationDTO.class);
		}
		return null;
	}

	@Override
	public LocationDTO getLocationById(Integer id) {
		Optional<Location> opt=locationRepo.findById(id);
		if(opt.isPresent())
		{
			Location location=opt.get();
		
			return ObjectMapperUtils.map(location, LocationDTO.class);
		}
		return null;
	}

	@Override
	public Boolean deleteLocation(Integer id) {
		Optional<Location> opt=locationRepo.findById(id);
		if(opt.isPresent())
		{
			Location location=opt.get();
			locationRepo.delete(location);
			return true;
		}
		return false;
	}

	@Override
	public List<LocationDTO> getAllLocation() {
		List<Location> list=locationRepo.findAll();
		return ObjectMapperUtils.mapAll(list, LocationDTO.class);
//		return null;
	}

}
