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

import com.purpledocs.boxtracker.Exception.DuplicateException;
import com.purpledocs.boxtracker.dto.BoxDTO;

import com.purpledocs.boxtracker.dto.Request;
import com.purpledocs.boxtracker.dto.Response;
import com.purpledocs.boxtracker.dto.ResponseCode;

import com.purpledocs.boxtracker.service.BoxService;

@RestController
@RequestMapping("/api/box")
public class BoxController {

	@Autowired
	private Environment env;
	
	@Autowired
	private BoxService boxService;
	
	
	@PostMapping("/addBox")
	public Response<BoxDTO> addBox(@RequestBody Request<BoxDTO> reqBox)
	{
		BoxDTO boxDto=reqBox.getQuery();
		
		Response<BoxDTO> response=new Response<>();
		response.setRequestId(reqBox.getRequestId());
		response.setTimestamp(reqBox.getTimestamp());
		try
		{
			boxDto=boxService.addBox(boxDto);
			response.setCode(ResponseCode.CREATED_OK);
			response.setMessage(env.getProperty("create.box.ok"));
			response.setResult(boxDto);
			
			return response;
		}
		catch(DataIntegrityViolationException e)
		{
			response.setCode(ResponseCode.BAD_REQUEST);
			response.setMessage(env.getProperty("create.box.duplicate.boxScanId"));
			response.setResult(null);
			return response;
		}catch(NumberFormatException ex)
		{
			response.setMessage(env.getProperty("create.box.location.error"));
			response.setResult(null);
			response.setCode(ResponseCode.BAD_REQUEST);
			return response;
		}
		catch(DuplicateException ex)
		{
			response.setMessage(ex.getMessage());
			response.setResult(null);
			response.setCode(ResponseCode.BAD_REQUEST);
			return response;
		}
		
		
	}
	
	@PutMapping("/updateBox")
	public Response<BoxDTO> updateBox( @RequestBody Request<BoxDTO> req)
	{
		BoxDTO boxDto=req.getQuery();
		Response<BoxDTO > response=new Response<>();
		response.setRequestId(req.getRequestId());
		response.setTimestamp(req.getTimestamp());
		try {
			boxDto=boxService.updateBox( boxDto);
			
			if(boxDto!=null)
			{
				response.setMessage(env.getProperty("update.box.details.ok"));
				response.setResult(boxDto);
				response.setCode(ResponseCode.OK);
				return response;
			}
			response.setMessage(env.getProperty("update.box.details.error"));
			response.setResult(boxDto);
			response.setCode(ResponseCode.BAD_REQUEST);
			return response;
		}
		catch(DataIntegrityViolationException e)
		{
			response.setCode(ResponseCode.BAD_REQUEST);
			response.setMessage(env.getProperty("create.box.duplicate.boxScanId"));
			response.setResult(null);
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
	

		
	@GetMapping("/getAllBoxes")
	public Response<List<BoxDTO>> getAllBoxes()
	{
		Response<List<BoxDTO>> response=new Response<>();
		List<BoxDTO> list=boxService.getAllBoxes();
		
		if(list!=null)
		{
			response.setMessage(env.getProperty("fetch.box.ok"));
			response.setResult(list);
			response.setCode(ResponseCode.OK);
			return response;
		}
		response.setMessage(env.getProperty("fetch.box.error"));
		response.setResult(list);
		response.setCode(ResponseCode.BAD_REQUEST);
		return response;
	}
	
	@GetMapping("/getBoxById/{id}")
	public Response<BoxDTO> getBoxById(@PathVariable Integer id)
	{
		Response<BoxDTO> response=new Response<>();
		BoxDTO boxDto=boxService.getBoxById(id);
		if(boxDto!=null)
		{
			response.setCode(ResponseCode.OK);
			response.setMessage(env.getProperty("fetch.box.ok"));
			response.setResult(boxDto);
			return response;
		}
		response.setCode(ResponseCode.BAD_REQUEST);
		response.setMessage(env.getProperty("fetch.box.error"));
		response.setResult(boxDto);
		return response;
		
	}
	@DeleteMapping("/deleteBox/{id}")
	public Response<String> deleteBox(@PathVariable Integer id)
	{
		Response<String> response=new Response<>();
		boolean isDeleted = boxService.deleteBox(id);
		if(isDeleted)
		{
			response.setCode(ResponseCode.OK);
			response.setMessage(env.getProperty("delete.box.ok"));
			response.setResult(null);
			return response;
		}
		response.setCode(ResponseCode.BAD_REQUEST);
		response.setMessage(env.getProperty("delete.box.error"));
		response.setResult(null);
		return response;
	}
	
	@GetMapping("/getBoxByScanId/{id}")
	public Response<BoxDTO> getBoxByScanId(@PathVariable String id)
	{
		Response<BoxDTO> response=new Response<>();
		BoxDTO boxDto=boxService.getBoxByScanId(id);
		if(boxDto!=null)
		{
			response.setCode(ResponseCode.OK);
			response.setMessage(env.getProperty("fetch.box.ok"));
			response.setResult(boxDto);
			return response;
		}
		response.setCode(ResponseCode.BAD_REQUEST);
		response.setMessage(env.getProperty("fetch.box.error"));
		response.setResult(boxDto);
		return response;
		
	}
	
	}
