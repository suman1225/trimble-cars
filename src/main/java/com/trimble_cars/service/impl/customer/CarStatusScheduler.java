package com.trimble_cars.service.impl.customer;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.trimble_cars.repository.CarRespository;

import jakarta.transaction.Transactional;

import com.trimble_cars.entity.BookCarEntity;
import com.trimble_cars.entity.CarEntity;
import com.trimble_cars.repository.BookCarRepository;

@Service
public class CarStatusScheduler {

	private static final Logger logger = LoggerFactory.getLogger(CarStatusScheduler.class);

	@Autowired
	private BookCarRepository bookCarRepository;

	@Autowired
	private CarRespository carRespository;

	// scheduler runs every 2 hour for change the car status
	@Scheduled(cron = "0 0 */2 * * *")
	@Transactional
	public void updateCarStatusToIdle() {
		logger.debug("CarStatusScheduler :: updateCarStatusToIdle :: Entered ");
		LocalDateTime now = LocalDateTime.now();
		List<BookCarEntity> expiredBookings = bookCarRepository.findByEndDate(now);
		for (BookCarEntity booking : expiredBookings) {
			CarEntity car = booking.getCarId();
			car.setCarStatus("Idle");
			carRespository.save(car);
		}
		logger.debug("CarStatusScheduler :: updateCarStatusToIdle :: Exit ");
	}

}
