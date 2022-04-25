package com.tus.farecalculation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tus.farecalculation.DTO.PassengerDTO;
import com.tus.farecalculation.DTO.ResponseDTO;
import com.tus.farecalculation.entity.TravelHistoryEntity;
import com.tus.farecalculation.service.PassengerService;
import com.tus.farecalculation.utils.JwtUtil;

@RestController
@RequestMapping(path = "/api/v2")
public class TapController {

	@Autowired
	private PassengerService passService;
	
	@Autowired
	private JwtUtil jwt;
	
	@RequestMapping(value = "passenger", method = RequestMethod.POST)
	public ResponseEntity<ResponseDTO> checkPassengerDetails(@RequestBody PassengerDTO passenger) {
		ResponseDTO result=passService.checkPassenger(passenger);
		return new ResponseEntity<ResponseDTO>(result, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "traveldetails", method = RequestMethod.GET)
	public ResponseEntity<List<TravelHistoryEntity>> getTravelDetails(@RequestHeader("Authorization") String authToken) {
		String[] token=authToken.split(" ");
		Integer userid=Integer.parseInt(jwt.extractusername(token[1]));
		System.out.println("Calling travel history method");
		List<TravelHistoryEntity> result=passService.getUserTravelHistory(userid);
		return new ResponseEntity<List<TravelHistoryEntity>>(result, HttpStatus.OK);
	}
}
