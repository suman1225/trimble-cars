package com.trimble_cars.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trimble_cars.controller.customer.CustomerController;
import com.trimble_cars.dto.CarDto;
import com.trimble_cars.dto.CarOwnerDto;
import com.trimble_cars.service.CarOwnerService;

@RestController
@RequestMapping("/owner")
public class CarOwnercontroller {

	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	CarOwnerService carOwnerService;

	@PostMapping("/create")
	public ResponseEntity<?> createCarOwner(@RequestBody CarOwnerDto carOwnerDto) {
		logger.debug("CarOwnercontroller :: createOwner :: Entered");

		CarOwnerDto ownerDto = carOwnerService.createCarOwner(carOwnerDto);

		if (ownerDto != null) {
			logger.error("CarOwnercontroller :: createOwner :: Exit");
			return ResponseEntity.ok(ownerDto);
		}
		logger.debug("CarOwnercontroller :: createOwner :: Error");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CarOwner MobileNumber Already Exists Try New One");
	}

	@PostMapping("/create/car")
	public ResponseEntity<?> createCar(@RequestBody CarDto carDto) {
		logger.debug("CarOwnercontroller :: createCar :: Entered");

		CarDto createCar = carOwnerService.createCar(carDto);
		if (createCar != null) {
			logger.error("CarOwnercontroller :: createCar :: Exit");
			return ResponseEntity.ok(createCar);
		}
		logger.debug("CarOwnercontroller :: createCar :: Error");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Car Details Already Exists Try New One");

	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateCarOwner(@PathVariable Long id, @RequestBody CarOwnerDto carOwnerDto) {
		logger.debug("CarOwnercontroller :: updateAdmin :: Entered");

		CarOwnerDto updateDto = carOwnerService.updateCarOwner(id, carOwnerDto);
		if (updateDto != null) {
			logger.error("CarOwnercontroller :: updateAdmin :: Exit");
			return ResponseEntity.ok(updateDto);
		}
		logger.debug("CarOwnercontroller :: updateAdmin :: Error");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CarOwner MobileNumber Already Exists Try New One");

	}

	@GetMapping("/cars/{id}")
	public ResponseEntity<?> getCars(@PathVariable Long id) {
		logger.debug("CarOwnercontroller :: getCars :: Entered");

		List<CarDto> cars = carOwnerService.getCars(id);
		if (cars != null) {
			logger.error("CarOwnercontroller :: getCars :: Exit");
			return ResponseEntity.ok(cars);
		}
		logger.debug("CarOwnercontroller :: getCars :: Error");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Car details not avaiable");
	}

}
