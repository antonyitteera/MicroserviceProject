package com.tus.farecalculation.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "passenger_details")
@Getter
@Setter
@NoArgsConstructor
public class PassengerDetailsEntity {

	@Id
	@Column(name = "user_id")
	private Integer userid;
	@Column(name="start_time")
	private LocalDateTime startTime;
	@Column(name = "start_point")
	private String start_point;
	@Column(name="route_number")
	private String routeNumber;
	@Column(name="bus_number")
	private String busNumber;
	public PassengerDetailsEntity(Integer userid, LocalDateTime startTime, String start_point, String end_point, String routeNumber,
			String busNumber) {
		super();
		this.userid = userid;
		this.startTime = startTime;
		this.start_point = start_point;
		this.routeNumber = routeNumber;
		this.busNumber = busNumber;
	}
}