package com.tus.usermanagement.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PassengerDTO {
	private Integer userId;
	private String sourcePoint;
	private String routeNumber;
	private String busNumber;
		
}
