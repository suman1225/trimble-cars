package com.trimble_cars.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trimble_cars.entity.CarEntity;
import com.trimble_cars.entity.CarOwnerEntity;

@Repository
public interface CarownerRepository extends JpaRepository<CarOwnerEntity, Long> {

	@Query(value = " select * from car where owner_id = :id ", nativeQuery = true)
	List<CarEntity> findOwnerByid(@Param("id") Long id);

}
