package com.purpledocs.boxtracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.purpledocs.boxtracker.dto.LocationDTO;
import com.purpledocs.boxtracker.dto.Request;
import com.purpledocs.boxtracker.dto.Response;
import com.purpledocs.boxtracker.dto.ResponseCode;
import com.purpledocs.boxtracker.service.LocationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/location")
public class LocationController {

	@Autowired
	private LocationService locationService;
	
	@Autowired
	private Environment env;
	@PostMapping("/addLocation")
	public Response<LocationDTO> addLocation( @Valid @RequestBody Request<LocationDTO> req)
	{
		Response<LocationDTO> response=new Response<>();
		LocationDTO locationDto=req.getQuery();
		response.setTimestamp(req.getTimestamp());
			response.setRequestId(req.getRequestId());
			try
			{
				locationDto=locationService.addLocation(locationDto);
				response.setResult(locationDto);
				response.setMessage(env.getProperty("create.location.ok"));
				response.setCode(ResponseCode.OK);
				
				return response;
			}
			catch (DataIntegrityViolationException  e) {
				response.setResult(null);
				response.setMessage(env.getProperty("create.location.duplicate"));
				response.setCode(ResponseCode.OK);
				
				return response;
			}
			
			
		
		
		
		
	}
	
	@PutMapping("/updateLocation")
	public Response<LocationDTO> updateLocation(@RequestBody @Valid Request<LocationDTO> req)
	{
		
		LocationDTO locationDto=req.getQuery();
		Response<LocationDTO> response=new Response<>();
		response.setRequestId(req.getRequestId());
		try
		{
			locationDto=locationService.updateLocation(locationDto);
			response.setTimestamp(req.getTimestamp());
			if(locationDto!=null)
			{
				response.setResult(locationDto);
				response.setMessage(env.getProperty("update.location.ok"));
				response.setCode(ResponseCode.OK);
				return response;
			}
			response.setResult(locationDto);
			response.setMessage(env.getProperty("update.location.error"));
			response.setCode(ResponseCode.BAD_REQUEST);
			
			return response;
		}
		catch (DataIntegrityViolationException  e) {
			response.setResult(null);
			response.setMessage(env.getProperty("create.location.duplicate"));
			response.setCode(ResponseCode.OK);
			
			return response;
		}
		
	}
	@GetMapping("/getLocationById/{id}")
	public Response<LocationDTO> getLocationById(@PathVariable Integer id)
	{
		LocationDTO locationDto=locationService.getLocationById(id);
		Response<LocationDTO> response=new Response<>();
		if(locationDto!=null)
		{
			response.setCode(ResponseCode.OK);
			response.setMessage(env.getProperty("fetch.location.ok"));
			response.setResult(locationDto);
			return response;
		}
		response.setCode(ResponseCode.BAD_REQUEST);
		response.setMessage(env.getProperty("fetch.location.error"));
		response.setResult(locationDto);
		return response;
	}
	
	@DeleteMapping("/deleteLocation/{id}")
	public Response<String> deleteLocationById(@PathVariable Integer id)
	{
		Response<String> response=new Response<>();
		Boolean isDeleted=locationService.deleteLocation(id);
		if(isDeleted)
		{
			response.setCode(ResponseCode.OK);
			response.setMessage(env.getProperty("delete.location.ok"));
			response.setResult(null);
			return response;
		}
		response.setCode(ResponseCode.BAD_REQUEST);
		response.setMessage(env.getProperty("delete.location.error"));
		response.setResult(null);
		return response;
	}
	
	@GetMapping("/getAllLocations")
	public Response<List<LocationDTO>> getAllLocations()
	{
		Response<List<LocationDTO>> response=new Response<>();
		List<LocationDTO> list=locationService.getAllLocation();
		if(list!=null)
		{
			response.setCode(ResponseCode.OK);
			response.setResult(list);
			response.setMessage(env.getProperty("fetch.location.ok"));
			return response;
		}
		response.setCode(ResponseCode.BAD_REQUEST);
		response.setMessage(env.getProperty("fetch.location.error"));
		return response;
	}
}
