package com.trimble_cars.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trimble_cars.entity.HistoryEntity;

public interface HistoryRepository extends JpaRepository<HistoryEntity, Long> {

}
