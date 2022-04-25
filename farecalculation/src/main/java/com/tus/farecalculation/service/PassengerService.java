package com.tus.farecalculation.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tus.farecalculation.DTO.FareDTO;
import com.tus.farecalculation.DTO.PassengerDTO;
import com.tus.farecalculation.DTO.ResponseDTO;
import com.tus.farecalculation.entity.PassengerDetailsEntity;
import com.tus.farecalculation.entity.StopDetailsEntity;
import com.tus.farecalculation.entity.TravelHistoryEntity;
import com.tus.farecalculation.exception.StopNotPresentException;
import com.tus.farecalculation.repository.PassengerRepository;
import com.tus.farecalculation.repository.StopDetailsRepository;
import com.tus.farecalculation.repository.TravelHistoryRepository;

@Service
public class PassengerService {

	@Autowired
	private PassengerRepository passRepo;

	@Autowired
	private StopDetailsRepository stopRepo;

	@Autowired
	private TravelHistoryRepository travelRepo;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${user-service}")
	private String userService;

	public ResponseDTO checkPassenger(PassengerDTO passenger) {
		// TODO Auto-generated method stub
		Integer userid = passenger.getUserId();
		Optional<PassengerDetailsEntity> currentPassenger = passRepo.findById(userid);
		ResponseDTO respObj = new ResponseDTO();
		if (passRepo.existsById(userid)) {
			passRepo.deleteById(userid);
			LocalDateTime startTime = currentPassenger.get().getStartTime();
			LocalDateTime localDateTime = LocalDateTime.parse(startTime.toString());
			Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
			Date startTimeDate = Date.from(instant);
			System.out.println("Start time is " + startTimeDate);
			String startPoint = currentPassenger.get().getStart_point();
			String dropPoint = passenger.getSourcePoint();
			Double fare = CalculateFare(startPoint, dropPoint);
			TravelHistoryEntity travelDetails = new TravelHistoryEntity();
			travelDetails.setBoardingPoint(startPoint);
			travelDetails.setBusNum(passenger.getBusNumber());
			travelDetails.setCost(fare);
			travelDetails.setCreateTime(LocalDateTime.now());
			travelDetails.setDropOffPoint(dropPoint);
			travelDetails.setRouteNumber(passenger.getRouteNumber());
			travelDetails.setTripEndTime(LocalDateTime.now());
			travelDetails.setTripStartTime(currentPassenger.get().getStartTime());
			travelDetails.setUserId(passenger.getUserId());
			travelRepo.save(travelDetails);
			String message=DeductFare(startPoint,dropPoint,fare,passenger.getUserId());
			respObj.setMessage(message);
			respObj.setFare(fare);
			return respObj;
		} else {
			System.out.println("User not present in table");
			LocalDateTime startTime = LocalDateTime.now();
			PassengerDetailsEntity passEntity = new PassengerDetailsEntity(passenger.getUserId(), startTime,
					passenger.getSourcePoint(), "", passenger.getRouteNumber(), passenger.getBusNumber());
			passRepo.save(passEntity);
			respObj.setMessage("User boarded the bus from " + passenger.getSourcePoint());
			respObj.setFare(0.0);
			return respObj;
		}
	}
	
	public List<TravelHistoryEntity> getUserTravelHistory(Integer userid) {
		List<TravelHistoryEntity> travelDetailsObj=travelRepo.findByUserId(userid);
		return travelDetailsObj;
	}

	private String DeductFare(String startPoint, String dropPoint, Double fare, Integer userId) {
		HttpHeaders reqHeader= new HttpHeaders();
		reqHeader.setContentType(MediaType.APPLICATION_JSON);
		FareDTO passObj= new FareDTO(startPoint, dropPoint, fare);
		HttpEntity<FareDTO> reqEntity= new HttpEntity<>(passObj,reqHeader);
		ResponseEntity<String> respEntity=restTemplate.exchange("http://"+userService+"/api/v1/user/"+userId+"/deduct",HttpMethod.PUT,reqEntity,String.class);
		return respEntity.getBody();
	}

	private Double CalculateFare(String startPoint, String dropPoint) {
		// TODO Auto-generated method stub
		System.out.println("startpoint: " + startPoint);
		System.out.println("dropPoint: " + dropPoint);
		List<StopDetailsEntity> startStopList = stopRepo.findByStopName(startPoint);
		System.out.println(startStopList);
		List<StopDetailsEntity> dropStopList = stopRepo.findByStopName(dropPoint);
		if (!startStopList.isEmpty() && !dropStopList.isEmpty()) {
			Integer startStopNumber = startStopList.get(0).getStopNumber();
			Integer endStopNumber = dropStopList.get(0).getStopNumber();
			Double calculatedFare = stopRepo.findStopsBetweenStartAndStop(startStopNumber + 1, endStopNumber);
			System.out.println("stopInformationList" + calculatedFare);
			return calculatedFare;

		} else {
			throw new StopNotPresentException("Stop not available");
		}

	}

}
