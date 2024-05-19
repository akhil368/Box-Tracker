package com.purpledocs.boxtracker.Exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.purpledocs.boxtracker.dto.Response;
import com.purpledocs.boxtracker.dto.ResponseCode;

import jakarta.validation.ConstraintViolationException;


@RestControllerAdvice
public class GlobalExceptionHandler {
 
    @ExceptionHandler(MethodArgumentNotValidException.class)
	public Response<String> myMANVExceptionHandler(MethodArgumentNotValidException me)  {

    	Response<String> response=new Response<>();
        response.setCode(ResponseCode.BAD_REQUEST);
        response.setResult(null);
        response.setMessage(me.getBindingResult().getFieldError().getDefaultMessage());
        return response;
	
			
	}
    @ExceptionHandler(ConstraintViolationException.class)
    public Response<String> handleConstraintViolationException(ConstraintViolationException ex) {
        // Extract constraint violation messages
        List<String> errorMessages = ex.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.toList());

        String message=String.join(", ", errorMessages);
        Response<String> response=new Response<>();
        response.setCode(ResponseCode.BAD_REQUEST);
        response.setResult(null);
        response.setMessage(errorMessages.get(0));
        response.setTimestamp(null);
        return response;
    }

}