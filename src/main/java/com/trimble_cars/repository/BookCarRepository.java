package com.trimble_cars.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.trimble_cars.entity.BookCarEntity;

public interface BookCarRepository extends JpaRepository<BookCarEntity, Long> {

	@Query(value = "SELECT * FROM book_cars WHERE end_date < :now", nativeQuery = true)
	List<BookCarEntity> findByEndDate(@Param("now") LocalDateTime now);


}
