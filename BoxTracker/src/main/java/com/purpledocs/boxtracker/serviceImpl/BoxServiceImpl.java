package com.purpledocs.boxtracker.serviceImpl;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.purpledocs.boxtracker.Exception.DuplicateException;
import com.purpledocs.boxtracker.config.GetUser;
import com.purpledocs.boxtracker.dto.BoxDTO;
import com.purpledocs.boxtracker.entity.Box;
import com.purpledocs.boxtracker.entity.DeleteDetails;
import com.purpledocs.boxtracker.entity.Location;
import com.purpledocs.boxtracker.repository.BoxRepository;
import com.purpledocs.boxtracker.repository.DeleteDetailsRepository;
import com.purpledocs.boxtracker.repository.LocationRepository;
import com.purpledocs.boxtracker.service.BoxService;
import com.purpledocs.boxtracker.util.ObjectMapperUtils;

@Service
public class BoxServiceImpl implements BoxService {

	@Autowired
	private GetUser getUser;
	
	@Autowired
	private DeleteDetailsRepository deleteDetailsRepository;
	
	@Autowired
	private BoxRepository boxRepository;

	@Autowired
	private LocationRepository locationRepo;
	
	
	@Override
	public BoxDTO addBox(BoxDTO boxDTO) {
		try
		{
			Integer locationId=Integer.parseInt (boxDTO.getBoxDetailsList().get(0).getLocation());
			Optional<Location> opt=locationRepo.findById(locationId);
			
			if(opt.isPresent())
			{
				Location location=opt.get();
				boxDTO.getBoxDetailsList().get(0).setLocation(location.getLocationName());
				boxDTO.getBoxDetailsList().get(0).setBy(getUser.getUser().getUserId());	
				Box box=ObjectMapperUtils.map(boxDTO, Box.class);

				if(!boxDTO.getBoxDetailsList().get(0).getStatus().toString().equals("CREATED"))
				{
					throw new DuplicateException("Please Add Box Status as Created");
				}
				box=boxRepository.save(box);

				return ObjectMapperUtils.map(box, BoxDTO.class);
			}
			return null;
		}catch(NumberFormatException ex)
		{
			throw ex;
		}
		

	}

	@Override
	public BoxDTO updateBox(BoxDTO boxDTO) {
		Optional<Box> opt=boxRepository.findById(boxDTO.getBoxId());
		
		if(opt.isPresent())
		{
			Box box=opt.get();
			box.setBoxScanId(boxDTO.getBoxScanId());

			try {
				Integer locationId=Integer.parseInt (boxDTO.getBoxDetailsList().get(0).getLocation());
				Optional<Location> opt1=locationRepo.findById(locationId);
			
				
				if(opt1.isPresent())
				{
					Location location=opt1.get();			
					box.getBoxDetailsList().get(0).setBy(getUser.getUser().getUserId());	
					box.getBoxDetailsList().get(0).setLocation(location.getLocationName());
					box=boxRepository.save(box);
					System.out.println(box);
					return ObjectMapperUtils.map(box, BoxDTO.class);
				}
			}catch (NumberFormatException ex) {
				
				throw ex;
			}
			
			
		}
		return null;
	}

	@Override
	public Boolean deleteBox(Integer id) {
		Optional<Box> opt=boxRepository.findById(id);
		if(opt.isPresent())
		{
			Box box=opt.get();
			DeleteDetails deleteDetails=new DeleteDetails();
			deleteDetails.setBoxScanId(box.getBoxScanId());
			deleteDetails.setUserId(getUser.getUser().getUserId());
			
			System.out.println(deleteDetails);
			deleteDetailsRepository.saveAndFlush(deleteDetails);
			boxRepository.delete(box);
			return true;
		}
		return false;
	}

	@Override
	public BoxDTO getBoxById(Integer id) {
		Optional<Box> opt=boxRepository.findById(id);
		if(opt.isPresent())
		{
			Box box=opt.get();
			return ObjectMapperUtils.map(box, BoxDTO.class);
		}
		return null;
	}

	@Override
	public List<BoxDTO> getAllBoxes() {
		
		return ObjectMapperUtils.mapAll(boxRepository.findAll(), BoxDTO.class);
	}
	@Override
	public BoxDTO getBoxByScanId(String boxScanId)
	{
		Optional<Box> opt= boxRepository.findByBoxScanId(boxScanId);
		if(opt.isPresent())
		{
			return ObjectMapperUtils.map(opt.get(), BoxDTO.class);
		}
		return null;
	}

	
	

	
}
