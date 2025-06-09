package com.trimble_cars.service.customer;

import com.trimble_cars.dto.BookCarDto;
import com.trimble_cars.dto.CustomerDto;

public interface CustomerService {

	CustomerDto createCustomer(CustomerDto customerDto);

	CustomerDto updateCustomer(Long id, CustomerDto customerDto);
	
	BookCarDto bookCar(BookCarDto bookCarDto, Long id);

}
