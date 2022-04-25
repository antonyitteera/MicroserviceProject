package com.tus.farecalculation.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_travel_history")
@Getter
@Setter
public class TravelHistoryEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private Integer id ;
    
    @Column(name = "user_id")
    private Integer userId ;
    
    @Column(name = "trip_start_time")
    private LocalDateTime tripStartTime;

    @Column(name = "trip_end_time")
    private LocalDateTime tripEndTime;

    @Column(name = "route_number")
    private String routeNumber;

    @Column(name = "boarding_point")
    private String boardingPoint;

    @Column(name = "drop_off_point")
    private String dropOffPoint;
    
    @Column(name = "cost")
    private Double cost;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    private String busNum;

}