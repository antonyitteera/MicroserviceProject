package com.tus.farecalculation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tus.farecalculation.entity.StopDetailsEntity;

@Repository
public interface StopDetailsRepository extends JpaRepository<StopDetailsEntity, Integer> {

	@Query(value = "select sum(u.price) from stopEntity u where u.stopNumber between ?1 and ?2")
	Double findStopsBetweenStartAndStop(Integer startPoint, Integer endPoint);
	
	List<StopDetailsEntity> findByStopName(String stopName);
}
