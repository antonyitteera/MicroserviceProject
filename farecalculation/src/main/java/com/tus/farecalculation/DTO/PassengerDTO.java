package com.tus.farecalculation.DTO;

import lombok.Data;

@Data
public class PassengerDTO {
	private Integer userId;
	private String sourcePoint;
	private String routeNumber;
	private String busNumber;
}
