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

import com.trimble_cars.dto.AdminDto;
import com.trimble_cars.dto.CarDto;
import com.trimble_cars.service.AdminService;

@RestController
@RequestMapping("/admin")
public class Admincontroller {

	private static final Logger logger = LoggerFactory.getLogger(Admincontroller.class);

	@Autowired
	AdminService adminService;

	@PostMapping("/create")
	public ResponseEntity<?> createAdmin(@RequestBody AdminDto adminDto) {
		logger.debug("Admincontroller :: createAdmin :: Entered");
		AdminDto createAdmin = adminService.createAdmin(adminDto);

		if (createAdmin != null) {
			logger.error("Admincontroller :: createAdmin :: Exit");
			return ResponseEntity.ok(createAdmin);
		}
		logger.debug("Admincontroller :: createAdmin :: Error");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Admin MobileNumber Already Exists Try New One");

	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateAdmin(@PathVariable Long id, @RequestBody AdminDto adminDto) {
		logger.debug("Admincontroller :: updateAdmin :: Entered");

		AdminDto updateDto = adminService.updateAdmin(id, adminDto);
		if (updateDto != null) {
			logger.error("Admincontroller :: updateAdmin :: Exit");
			return ResponseEntity.ok(updateDto);
		}
		logger.debug("Admincontroller :: updateAdmin :: Error");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Admin MobileNumber Already Exists Try New One");

	}

	@GetMapping("/cars")
	public ResponseEntity<?> getAvailableCars() {
		logger.debug("Admincontroller :: getAvailableCars :: Entered");
		List<CarDto> allcars = adminService.getAllcars();
		if (allcars != null) {
			logger.error("Admincontroller :: getAvailableCars :: Exit");
			return ResponseEntity.ok(allcars);
		}
		logger.debug("Admincontroller :: getAvailableCars :: Error");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No Cars Available");

	}

}
