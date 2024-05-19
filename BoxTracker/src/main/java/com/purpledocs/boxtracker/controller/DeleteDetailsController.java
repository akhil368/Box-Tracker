package com.purpledocs.boxtracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.purpledocs.boxtracker.dto.DeleteDetailsDTO;
import com.purpledocs.boxtracker.dto.Response;
import com.purpledocs.boxtracker.dto.ResponseCode;
import com.purpledocs.boxtracker.service.DeleteDetailService;

@RestController
@RequestMapping("/api/deleteDetails")
public class DeleteDetailsController {

	@Autowired
	private Environment env;
	@Autowired
	private DeleteDetailService deleteDetailService;
	
	@GetMapping("/getAllDeleteDetails")
	public Response<List<DeleteDetailsDTO>> getAllDeleteDetails()
	{
		Response<List<DeleteDetailsDTO>> response=new Response<>();
		List<DeleteDetailsDTO> list=deleteDetailService.getAllDeleteDetails();
		if(list!=null)
		{
			response.setCode(ResponseCode.OK);
			response.setResult(list);
			response.setMessage(env.getProperty("fetch.delete.details.ok"));
			return response;
		}
		response.setCode(ResponseCode.BAD_REQUEST);
		response.setResult(list);
		response.setMessage(env.getProperty("fetch.delete.details.error"));
		return response;
		
	}
	@GetMapping("/getDeleteDetails/{deleteId}")
	public Response<DeleteDetailsDTO> getDeleteDetails(@PathVariable Integer deleteId)
	{
		Response<DeleteDetailsDTO>response=new Response<>();
		DeleteDetailsDTO list=deleteDetailService.getDeleteDetailsById(deleteId);
		if(list!=null)
		{
			response.setCode(ResponseCode.OK);
			response.setResult(list);
			response.setMessage(env.getProperty("fetch.delete.details.ok"));
			return response;
		}
		response.setCode(ResponseCode.BAD_REQUEST);
		response.setResult(list);
		response.setMessage(env.getProperty("fetch.delete.details.error"));
		return response;
		
	}
//	@PutMapping("/updateDeleteDetails")
//	public Response<DeleteDetailsDTO> updateDeleteDetails(@RequestBody Request<DeleteDetailsDTO> req)
//	{
//		Response<DeleteDetailsDTO>response=new Response<>();
//		DeleteDetailsDTO deleteDetailsDto=deleteDetailService.updateDeleteDetails(req.getQuery());
//		response.setTimestamp(req.getTimestamp());
//		if(deleteDetailsDto!=null)
//		{
//			response.setCode(ResponseCode.OK);
//			response.setResult(deleteDetailsDto);
//			response.setMessage(env.getProperty("update.delete.details.ok"));
//			return response;
//		}
//		response.setCode(ResponseCode.BAD_REQUEST);
//		response.setResult(deleteDetailsDto);
//		response.setMessage(env.getProperty("update.delete.details.error"));
//		return response;
//		
//	}
	@DeleteMapping("/deleteDeleteDetails/{deleteId}")
	public Response<String> deleteDeleteDetails(@PathVariable Integer deleteId)
	{
		Response<String>response=new Response<>();
		Boolean isDeleted=deleteDetailService.deleteDeleteDetails(deleteId);
		if(isDeleted)
		{
			response.setCode(ResponseCode.OK);
			response.setResult(null);
			response.setMessage(env.getProperty("delete.delete.details.ok"));
			return response;
		}
		response.setCode(ResponseCode.BAD_REQUEST);
		response.setResult(null);
		response.setMessage(env.getProperty("fetch.delete.details.error"));
		return response;
		
	}
}
