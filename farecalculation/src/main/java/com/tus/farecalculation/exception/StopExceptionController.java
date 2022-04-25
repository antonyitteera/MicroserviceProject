package com.tus.farecalculation.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;



@ControllerAdvice
public class StopExceptionController {

	@ExceptionHandler(value=StopNotPresentException.class)
	public ResponseEntity<Object> exception(StopNotPresentException exception){
		Map<String, String> response= new HashMap<>();
		response.put("Message", exception.getMessage());
		return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
	}
}
