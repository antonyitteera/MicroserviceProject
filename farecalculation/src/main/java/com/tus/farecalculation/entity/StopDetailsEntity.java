package com.tus.farecalculation.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "stopEntity")
@Table(name = "stop_details")
@Getter
@Setter
@NoArgsConstructor
public class StopDetailsEntity {


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private Integer id ;
    @Column(name = "route_number")
    private String routeInformationId;
    @Column(name = "stop_number")
    private Integer stopNumber;
    @Column(name = "stop_name")
    private String stopName;
    private Double distance;
    private Double price;

}
