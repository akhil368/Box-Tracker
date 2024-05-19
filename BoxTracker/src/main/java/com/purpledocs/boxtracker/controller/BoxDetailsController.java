package com.purpledocs.boxtracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.purpledocs.boxtracker.Exception.DuplicateException;
import com.purpledocs.boxtracker.dto.BoxDetailsDTO;
import com.purpledocs.boxtracker.dto.BoxDetailsRequest;
import com.purpledocs.boxtracker.dto.Request;
import com.purpledocs.boxtracker.dto.Response;
import com.purpledocs.boxtracker.dto.ResponseCode;
import com.purpledocs.boxtracker.service.BoxDetailsService;

@RestController
@RequestMapping("api/boxDetails")
public class BoxDetailsController {

	@Autowired
	private BoxDetailsService boxDetailsService;
	
	@Autowired
	private Environment env;
	
	@PostMapping("/addBoxDetails")
	public Response<BoxDetailsDTO> addBoxDetailsDTO( @RequestBody Request<BoxDetailsRequest> req)
	{
		BoxDetailsRequest boxDetailsDto=req.getQuery();
		BoxDetailsDTO dto=new BoxDetailsDTO();
		Response<BoxDetailsDTO > response=new Response<>();
		response.setRequestId(req.getRequestId());
		try
		{
			dto=boxDetailsService.addBoxDetailsDTO(boxDetailsDto);
			response.setTimestamp(req.getTimestamp());
			if(dto!=null)
			{
				
				response.setMessage(env.getProperty("add.box.details.ok"));
				response.setResult(dto);
				response.setCode(ResponseCode.OK);
				return response;
			}
			response.setMessage(env.getProperty("add.box.details.error"));
			response.setResult(dto);
			response.setCode(ResponseCode.BAD_REQUEST);
			return response;
		}
		catch(NumberFormatException ex)
		{
			response.setMessage(env.getProperty("create.box.location.error"));
			response.setResult(null);
			response.setCode(ResponseCode.BAD_REQUEST);
			return response;
		}
		catch (DuplicateException e) {
			response.setMessage(e.getMessage());
			response.setResult(null);
			response.setCode(ResponseCode.BAD_REQUEST);
			return response;
		}
		
	}
	
	@PutMapping("/updateBoxDetails")
	public Response<BoxDetailsDTO> updateBoxDetails(@RequestBody Request<BoxDetailsDTO> req)
	{
		Response<BoxDetailsDTO> response=new Response<>();
		BoxDetailsDTO boxDetailsDTO=req.getQuery();
		try
		{
			boxDetailsDTO=boxDetailsService.updateBoxDetails( boxDetailsDTO);

			response.setTimestamp(req.getTimestamp());
			if(boxDetailsDTO!=null)
			{
				response.setCode(ResponseCode.OK);
				response.setMessage(env.getProperty("update.box.details.ok"));
				response.setResult(boxDetailsDTO);
				return response;
			}
			response.setCode(ResponseCode.BAD_REQUEST);
			response.setMessage(env.getProperty("update.box.details.error"));
			response.setResult(boxDetailsDTO);
			return response;
		}
		catch(NumberFormatException ex)
		{
			response.setMessage(env.getProperty("create.box.location.error"));
			response.setResult(null);
			response.setCode(ResponseCode.BAD_REQUEST);
			return response;
		}
		
	}
	
	@GetMapping("/getAllBoxDetails")
	public Response<List<BoxDetailsDTO>> getAllBoxDetails()
	{
		Response<List<BoxDetailsDTO>> response=new Response<>();
		List<BoxDetailsDTO> list=boxDetailsService.getAllBoxDetails();
		if(list!=null)
		{
			response.setCode(ResponseCode.OK);
			response.setMessage(env.getProperty("fetch.box.details.ok"));
			response.setResult(list);
			return response;
		}
		response.setCode(ResponseCode.BAD_REQUEST);
		response.setMessage(env.getProperty("fetch.box.details.error"));
		response.setResult(list);
		return response;
	}
	

	@GetMapping("/getBoxDetailsById/{id}")
	public Response<BoxDetailsDTO> getBoxDetailsById(@PathVariable Integer id)
	{
		Response<BoxDetailsDTO> response=new Response<>();
		BoxDetailsDTO boxDetailsDTO=boxDetailsService.getBoxDetailsByBoxDetailsId(id);
		if(boxDetailsDTO!=null)
		{
			response.setCode(ResponseCode.OK);
			response.setMessage(env.getProperty("fetch.box.details.ok"));
			response.setResult(boxDetailsDTO);
			return response;
		}
		response.setCode(ResponseCode.BAD_REQUEST);
		response.setMessage(env.getProperty("fetch.box.details.error"));
		response.setResult(boxDetailsDTO);
		return response;
		
		
	}

	
	
	@DeleteMapping("/deleteBoxDetails/{id}")
	public Response<String> deleteBoxDetailsById(@PathVariable Integer id)
	{
		Response<String> response=new Response<>();
		Boolean isDeleted=boxDetailsService.deleteBoxDetails(id);
		if(isDeleted)
		{
			response.setCode(ResponseCode.OK);
			response.setMessage(env.getProperty("delete.box.details.ok"));
			response.setResult(null);
			return response;
		}
		response.setCode(ResponseCode.BAD_REQUEST);
		response.setMessage(env.getProperty("delete.box.details.error"));
		response.setResult(null);
		return response;
		
		
	}

}
