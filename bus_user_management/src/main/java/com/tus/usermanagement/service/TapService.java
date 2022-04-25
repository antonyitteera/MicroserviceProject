package com.tus.usermanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tus.usermanagement.DTO.PassengerDTO;
import com.tus.usermanagement.DTO.ResponseTapDTO;
import com.tus.usermanagement.DTO.TapDTO;
import com.tus.usermanagement.entity.UserEntity;
import com.tus.usermanagement.exception.InvalidBusException;
import com.tus.usermanagement.repository.UserRepository;

import io.netty.handler.codec.http.HttpHeaderDateFormat;

@Service
public class TapService {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Value("${fare-calculation-service}")
	String fleetService;
	
	public ResponseEntity<ResponseTapDTO> tapUser(TapDTO tappedUser) {
		PassengerDTO passObj= new PassengerDTO(tappedUser.getUserId(), tappedUser.getSourcePoint(), tappedUser.getRouteNumber(), tappedUser.getBusNumber());
		HttpHeaders reqHeader= new HttpHeaders();
		reqHeader.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<PassengerDTO> reqEntity= new HttpEntity<>(passObj,reqHeader);
		ResponseEntity<ResponseTapDTO> respEntity=restTemplate.exchange("http://"+fleetService+"/api/v2/passenger",HttpMethod.POST,reqEntity,ResponseTapDTO.class);
		System.out.println(respEntity.getBody()+" Call successfull");
		//return restObj;
		if(respEntity.getStatusCode()==HttpStatus.OK) {
			return respEntity;
		}else {
			throw new InvalidBusException("Invalid Bus or Route Number");
		}
	}
}
