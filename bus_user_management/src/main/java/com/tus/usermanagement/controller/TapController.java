package com.tus.usermanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.tus.usermanagement.DTO.PassengerDTO;
import com.tus.usermanagement.DTO.ResponseDTO;
import com.tus.usermanagement.DTO.ResponseTapDTO;
import com.tus.usermanagement.DTO.TapDTO;
import com.tus.usermanagement.DTO.TestDTO;
import com.tus.usermanagement.exception.UserNotFoundException;
import com.tus.usermanagement.service.TapService;
import com.tus.usermanagement.validation.UserValidation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1")
public class TapController {
	@Autowired
	TapService tapService;
	
	@Autowired
	WebClient.Builder webclientBuilder;
	
	@Autowired
	UserValidation userValidattion;
	
	@RequestMapping(value = "/tap",method = RequestMethod.POST)
	public ResponseEntity<ResponseTapDTO> tapCard(@RequestBody TapDTO tappedUser) {
		Integer userId=tappedUser.getUserId();
		System.out.println("UserId:"+userId);
		ResponseDTO respObj;
		ResponseEntity<ResponseTapDTO> resp;
		if(userValidattion.validateUserAndBalance(userId)) {
			System.out.println("Validation successful");
			resp= tapService.tapUser(tappedUser);
		}else {
			throw new UserNotFoundException(userId);
		}
		return resp;
	}
	
}

