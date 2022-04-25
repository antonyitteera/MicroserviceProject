package com.tus.farecalculation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tus.farecalculation.entity.TravelHistoryEntity;

public interface TravelHistoryRepository extends JpaRepository<TravelHistoryEntity, Integer>{

	List<TravelHistoryEntity> findByUserId(Integer userId);
}
