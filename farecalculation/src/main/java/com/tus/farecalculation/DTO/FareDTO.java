package com.tus.farecalculation.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FareDTO {

	private String source;
	private String destination;
	private double fare;
}
