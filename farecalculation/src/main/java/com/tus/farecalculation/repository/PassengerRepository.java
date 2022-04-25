package com.tus.farecalculation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tus.farecalculation.entity.PassengerDetailsEntity;

public interface PassengerRepository extends JpaRepository<PassengerDetailsEntity, Integer>{

}
