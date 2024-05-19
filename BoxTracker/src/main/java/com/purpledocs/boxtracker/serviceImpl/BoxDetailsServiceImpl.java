package com.purpledocs.boxtracker.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.purpledocs.boxtracker.Exception.DuplicateException;
import com.purpledocs.boxtracker.config.GetUser;
import com.purpledocs.boxtracker.dto.BoxDetailsDTO;
import com.purpledocs.boxtracker.dto.BoxDetailsRequest;
import com.purpledocs.boxtracker.entity.Box;
import com.purpledocs.boxtracker.entity.BoxDetails;
import com.purpledocs.boxtracker.entity.Location;
import com.purpledocs.boxtracker.repository.BoxDetailsRepository;
import com.purpledocs.boxtracker.repository.BoxRepository;
import com.purpledocs.boxtracker.repository.LocationRepository;
import com.purpledocs.boxtracker.service.BoxDetailsService;
import com.purpledocs.boxtracker.util.ObjectMapperUtils;

@Service
public class BoxDetailsServiceImpl implements BoxDetailsService {

	@Autowired
	private GetUser getUser;

	@Autowired
	private BoxRepository boxRepository;
	@Autowired
	private LocationRepository locationRepo;

	@Autowired
	private BoxDetailsRepository boxDetailsRepository;

	public BoxDetailsDTO addBoxDetailsDTO(BoxDetailsRequest boxDetailsDTO) {

		BoxDetailsDTO dto = new BoxDetailsDTO();
		dto.setBy(boxDetailsDTO.getBy());
		dto.setLocation(boxDetailsDTO.getLocation());
		dto.setStatus(boxDetailsDTO.getStatus());
		dto.setTimestamp(boxDetailsDTO.getTimestamp());
		BoxDetails boxDetails = ObjectMapperUtils.map(boxDetailsDTO, BoxDetails.class);
		Optional<Box> opt = boxRepository.findById(boxDetailsDTO.getCurrentBoxId());
		if (opt.isPresent()) {
			Box box = opt.get();
			List<BoxDetails> list = box.getBoxDetailsList();
			for (BoxDetails boxDetailsList : list) {
				System.out.println("Inside BoxDetails List");
				if (boxDetailsList.getStatus().equals(boxDetails.getStatus())
						|| boxDetails.getStatus().toString().equals("CHECKEDIN")) {
					throw new DuplicateException("Please Add CHECKOUT before CHECKEDIN");
				}
			}
			try {
				Integer locationId = Integer.parseInt(boxDetailsDTO.getLocation());
				Optional<Location> opt1 = locationRepo.findById(locationId);
				if (opt1.isPresent()) {
					Location location = opt1.get();
					boxDetails.setLocation(location.getLocationName());

					boxDetails.setLocation(location.getLocationName());
					boxDetails.setBy(getUser.getUser().getUserId());
					list.add(boxDetails);
				}

				box.setBoxDetailsList(list);

				boxDetailsRepository.save(boxDetails);
				return ObjectMapperUtils.map(boxDetails, BoxDetailsDTO.class);
			} catch (NumberFormatException ex) {
				throw ex;
			}

		}
		return null;
	}

	@Override
	public BoxDetailsDTO getBoxDetailsByBoxIdAndStatus(Integer id, String Status) {
		Optional<Box> opt = boxRepository.findById(id);
		if (opt.isPresent()) {
			Box box = opt.get();
			List<BoxDetails> list = box.getBoxDetailsList();
			for (BoxDetails boxDetails : list) {
				if (boxDetails.getStatus().toString().equals(Status)) {
					return ObjectMapperUtils.map(boxDetails, BoxDetailsDTO.class);
				}
			}
		}
		return null;
	}

	@Override
	public BoxDetailsDTO updateBoxDetails(BoxDetailsDTO boxDetialsDto) {
		Optional<BoxDetails> opt = boxDetailsRepository.findById(boxDetialsDto.getBoxDetailId());
		if (opt.isPresent()) {
			BoxDetails boxDetails = opt.get();
			boxDetails.setBy(getUser.getUser().getUserId());
			try {
				Integer locationId = Integer.parseInt(boxDetialsDto.getLocation());
				Optional<Location> opt1 = locationRepo.findById(locationId);
				if (opt1.isPresent()) {
					Location location = opt1.get();

					boxDetails.setLocation(location.getLocationName());
					boxDetails.setBy(getUser.getUser().getUserId());
					boxDetailsRepository.save(boxDetails);

					return ObjectMapperUtils.map(boxDetails, BoxDetailsDTO.class);
				}
			} catch (NumberFormatException ex) {
				throw ex;
			}

		}

		return null;
	}

	@Override
	public List<BoxDetailsDTO> getAllBoxDetails() {
		List<Box> list = boxRepository.findAll();
		if (list != null) {
			List<BoxDetails> boxDetailsList = new ArrayList<>();
			for (Box box : list) {
				boxDetailsList.addAll(box.getBoxDetailsList());
			}
			return ObjectMapperUtils.mapAll(boxDetailsList, BoxDetailsDTO.class);
		}
		return null;

	}

	@Override
	public Boolean deleteBoxDetails(Integer id) {
		// TODO Auto-generated method stub
		Optional<BoxDetails> opt = boxDetailsRepository.findById(id);
		if (opt.isPresent()) {
			BoxDetails boxDetials = opt.get();
			boxDetailsRepository.delete(boxDetials);
			return true;
		}

		return false;
	}

	@Override
	public BoxDetailsDTO getBoxDetailsByBoxDetailsId(Integer id) {
		Optional<BoxDetails> opt = boxDetailsRepository.findById(id);
		if (opt.isPresent()) {
			BoxDetails boxDetails = opt.get();
			return ObjectMapperUtils.map(boxDetails, BoxDetailsDTO.class);
		}
		return null;
	}

}
