package com.trimble_cars.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trimble_cars.entity.CarEntity;

@Repository
public interface CarRespository extends JpaRepository<CarEntity, Long>{

}
