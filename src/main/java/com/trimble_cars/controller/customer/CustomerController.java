package com.trimble_cars.controller.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trimble_cars.dto.BookCarDto;
import com.trimble_cars.dto.CustomerDto;
import com.trimble_cars.service.customer.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	CustomerService customerService;

	@PostMapping("/create")
	public ResponseEntity<?> createCustomer(@RequestBody CustomerDto customerDto) {
		logger.debug("CustomerController :: creatCustomer :: Entered");
		CustomerDto createCarOwner = customerService.createCustomer(customerDto);
		if (createCarOwner != null) {
			logger.debug("CustomerController :: creatCustomer :: Exit");
			return ResponseEntity.ok(createCarOwner);
		}
		logger.debug("CustomerController :: creatCustomer :: Error");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer MobileNumber Already Exists Try New One");
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateCustomer(@PathVariable Long id, @RequestBody CustomerDto customerDto) {
		logger.debug("CustomerController :: updateCustomer :: Entered");

		CustomerDto updateDto = customerService.updateCustomer(id, customerDto);
		if (updateDto != null) {
			logger.error("CustomerController :: updateCustomer :: Exit");
			return ResponseEntity.ok(updateDto);
		}
		logger.debug("CustomerController :: updateCustomer :: Error");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer MobileNumber Already Exists Try New One");

	}

	@PostMapping("/book_car/{id}")
	public ResponseEntity<?> bookLeaseCar(@PathVariable Long id, @RequestBody BookCarDto bookCarDto) {
		logger.debug("Admincontroller :: bookLeaseCar :: Entered");
		BookCarDto bookCar = customerService.bookCar(bookCarDto,id);
		if (bookCar != null) {
			logger.error("Admincontroller :: bookLeaseCar :: Exit");
			return ResponseEntity.ok(bookCar);
		}
		logger.debug("Admincontroller :: bookLeaseCar :: Error");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Booking Failed");
	}

}
