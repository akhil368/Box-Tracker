package com.purpledocs.boxtracker.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.purpledocs.boxtracker.dto.DeleteDetailsDTO;
import com.purpledocs.boxtracker.entity.DeleteDetails;
import com.purpledocs.boxtracker.repository.DeleteDetailsRepository;
import com.purpledocs.boxtracker.service.DeleteDetailService;
import com.purpledocs.boxtracker.util.ObjectMapperUtils;

@Service
public class DeleteDetailsServiceImpl implements DeleteDetailService{

	@Autowired
	private DeleteDetailsRepository deleteDetailsRepository;
	@Override
	public List<DeleteDetailsDTO> getAllDeleteDetails() {
		
		List<DeleteDetails> list=deleteDetailsRepository.findAll();

		if(!list.isEmpty())
		{

			return ObjectMapperUtils.mapAll(list, DeleteDetailsDTO.class);
		
		}
		return null;
	}

	@Override
	public DeleteDetailsDTO getDeleteDetailsById(Integer id) {
		Optional<DeleteDetails> opt=deleteDetailsRepository.findById(id);
		if(opt.isPresent())
		{
			return ObjectMapperUtils.map(opt.get(), DeleteDetailsDTO.class);
		}
		return null;
	}

	@Override
	public DeleteDetailsDTO updateDeleteDetails(DeleteDetailsDTO deleteDetailsDTO) {
		Optional<DeleteDetails> opt=deleteDetailsRepository.findById(deleteDetailsDTO.getDeleteId());
		if(opt.isPresent())
		{
			DeleteDetails deleteDetails=opt.get();
			deleteDetails.setUserId(deleteDetailsDTO.getUserId());
			deleteDetails.setBoxScanId(deleteDetailsDTO.getBoxScanId());
			deleteDetailsRepository.save(deleteDetails);
			return ObjectMapperUtils.map(deleteDetails, DeleteDetailsDTO.class);
		}
		return null;
	}

	@Override
	public Boolean deleteDeleteDetails(Integer id) {
		
		Optional<DeleteDetails> opt=deleteDetailsRepository.findById(id);
		if(opt.isPresent())
		{
			deleteDetailsRepository.delete(opt.get());
			return true;
		}
		return false;
	}

}
