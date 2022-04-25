package com.tus.usermanagement.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.tus.usermanagement.DTO.EmailDTO;
import com.tus.usermanagement.DTO.FareDTO;
import com.tus.usermanagement.DTO.HateoesRespDTO;
import com.tus.usermanagement.DTO.ResponseDTO;
import com.tus.usermanagement.DTO.SmartCardDTO;
import com.tus.usermanagement.entity.SmartCardEntity;
import com.tus.usermanagement.entity.UserEntity;
import com.tus.usermanagement.exception.PaymentFailedException;
import com.tus.usermanagement.repository.UserRepository;
import com.tus.usermanagement.utils.JwtUtil;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1")
public class SmartCardController {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private JwtUtil jwt;

	//to get smartcard details using userid
	@RequestMapping(value = "user/carddetail", method = RequestMethod.GET)
	public ResponseEntity<?> getCardDetails(@RequestHeader("Authorization") String authToken) {
		String[] token=authToken.split(" ");
		Integer userid=Integer.parseInt(jwt.extractusername(token[1]));
		UserEntity userEntity=userRepo.findById(userid).get();
		SmartCardEntity smartCardDetails=userEntity.getSmartCard();
		Object obj= smartCardDetails;
		Map<String, ArrayList<URI>> objMap = new HashMap<>();
		ArrayList<URI> putList= new ArrayList<>();
		URI location= ServletUriComponentsBuilder.fromCurrentRequest().
				buildAndExpand(smartCardDetails.getCardId()).toUri();
		putList.add(location);
		objMap.put("PUT", putList);
		HateoesRespDTO hateosObj= new HateoesRespDTO(obj, objMap);
		 return new ResponseEntity<>(hateosObj, HttpStatus.OK);
	}
	
	//to update the smartcard details
	@RequestMapping(value = "user/carddetail", method = RequestMethod.PUT)
	public ResponseEntity<?> updateCardDetails(@RequestHeader("Authorization") String authToken,@RequestBody @Valid SmartCardDTO cardDetails) {
		String[] token=authToken.split(" ");
		Integer userid=Integer.parseInt(jwt.extractusername(token[1]));
		UserEntity userEntity=userRepo.findById(userid).get();
		SmartCardEntity smartcardDetails= SmartCardEntity.builder().cardId(userEntity.getSmartCard().getCardId()).cardNum(cardDetails.getCardNum()).cardStatus(cardDetails.getCardStatus()).dateOfReg(cardDetails.getDateOfReg()).dateOfExp(cardDetails.getDateOfExp()).balance(cardDetails.getBalance()).build();
		userEntity.setSmartCard(smartcardDetails);
		UserEntity resp=userRepo.save(userEntity);
		Object obj= resp;
		Map<String, ArrayList<URI>> objMap = new HashMap<>();
		ArrayList<URI> putList= new ArrayList<>();
		URI location= ServletUriComponentsBuilder.fromCurrentRequest().
				buildAndExpand(resp.getSmartCard().getCardId()).toUri();
		putList.add(location);
		objMap.put("PUT", putList);
		HateoesRespDTO hateosObj= new HateoesRespDTO(obj, objMap);
		 return new ResponseEntity<>(hateosObj, HttpStatus.OK);
	}
	
	//to topup the user smartcard
	@RequestMapping(value = "user/topup", method = RequestMethod.PUT)
	public ResponseEntity<ResponseDTO> updateCardDetails(@RequestHeader("Authorization") String authToken,@RequestParam Integer amount) {
		String[] token=authToken.split(" ");
		Integer userid=Integer.parseInt(jwt.extractusername(token[1]));
		System.out.println("userid is "+userid);
		UserEntity userEntity=userRepo.findById(userid).get();
		double currentBalance=userEntity.getSmartCard().getBalance();
		double newBalance=currentBalance+amount;
		userEntity.getSmartCard().setBalance(newBalance);
		UserEntity resp=userRepo.save(userEntity);
		String msg="User "+resp.getFirstName()+ " with user id "+userid+" successfully recharged with "+amount+".Current available balance:"+ resp.getSmartCard().getBalance();
		ResponseDTO respObj= new ResponseDTO(msg);
		return new ResponseEntity<ResponseDTO>(respObj, HttpStatus.OK);
	}
	
	//to deduct the fare from smartcard
	@RequestMapping(value = "user/{userid}/deduct", method = RequestMethod.PUT)
	public ResponseEntity<String> deductFareFromCard(@PathVariable Integer userid,@RequestBody FareDTO fareDTO) {
		UserEntity userEntity=userRepo.findById(userid).get();
		double currentBalance=userEntity.getSmartCard().getBalance();
		String name=userEntity.getFirstName();
		String message;
		double fare=fareDTO.getFare();
		double newBalance=currentBalance-fare;
		if(newBalance>=-1) {
			userEntity.getSmartCard().setBalance(newBalance);
			UserEntity resp=userRepo.save(userEntity);
			message="Payment of "+fare+" done successfully. Available balance is "+resp.getSmartCard().getBalance()+".";
			if(newBalance<=0) {
				message=message+"Available balance is less than 0. Recharge soon!!";
			}
		}else {
			throw new PaymentFailedException();
		}
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}
}
