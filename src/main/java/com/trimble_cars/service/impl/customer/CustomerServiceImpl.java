package com.trimble_cars.service.impl.customer;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.trimble_cars.dto.BookCarDto;
import com.trimble_cars.dto.CustomerDto;
import com.trimble_cars.entity.BookCarEntity;
import com.trimble_cars.entity.CarEntity;
import com.trimble_cars.entity.CustomerEntity;
import com.trimble_cars.entity.HistoryEntity;
import com.trimble_cars.repository.BookCarRepository;
import com.trimble_cars.repository.CarRespository;
import com.trimble_cars.repository.HistoryRepository;
import com.trimble_cars.repository.customer.CustomerRepository;
import com.trimble_cars.service.customer.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	BookCarRepository bookCarRepository;

	@Autowired
	CarRespository carRespository;

	@Autowired
	HistoryRepository historyRepository;

	@Override
	public CustomerDto createCustomer(CustomerDto customerDto) {
		logger.debug("CustomerServiceImpl :: createCarOwner :: Entered");
		CustomerDto customer = null;
		CustomerEntity customerEntity = null;
		try {
			customerDto.setPassword(passwordEncoder.encode(customerDto.getPassword()));
			customerEntity = modelMapper.map(customerDto, CustomerEntity.class);

			CustomerEntity save = customerRepository.save(customerEntity);
			customer = modelMapper.map(save, CustomerDto.class);
		} catch (Exception e) {
			logger.error("CustomerServiceImpl :: createCarOwner :: Error" + e.getMessage());
			return null;
		}
		logger.debug("CustomerServiceImpl :: createCarOwner :: Entered");
		return customer;
	}

	@Override
	public CustomerDto updateCustomer(Long id, CustomerDto customerDto) {
		logger.debug("CustomerServiceImpl :: updateCustomer :: Entered");
		CustomerEntity customerEntity = null;
		CustomerDto customer = null;
		try {
			customerEntity = customerRepository.findById(id)
					.orElseThrow(() -> new RuntimeException("Customer Not Found"));

			customerEntity.setCustomerName(customerDto.getCustomerName());
			customerEntity.setMobileNumber(customerDto.getMobileNumber());
			customerEntity.setUserName(customerDto.getUserName());
			customerEntity.setPassword(passwordEncoder.encode(customerDto.getPassword()));
			customerEntity.setDrivingLicence(customerDto.getDrivingLicence());

			CustomerEntity save = customerRepository.save(customerEntity);
			customer = modelMapper.map(save, CustomerDto.class);
			logger.debug("CustomerServiceImpl :: updateCustomer :: Entered");
		} catch (Exception e) {
			logger.error("CustomerServiceImpl :: updateCustomer :: Error" + e.getMessage());
		}
		return customer;
	}

	@Override
	public BookCarDto bookCar(BookCarDto bookCarDto, Long customerId) {
	    logger.debug("AdminServiceImpl :: bookCar :: Entered");

	    BookCarDto responseDto = null;

	    try {
	        CustomerEntity customer = customerRepository.findById(customerId)
	                .orElseThrow(() -> new RuntimeException("Customer not found"));

	        CarEntity car = carRespository.findById(bookCarDto.getCarId())
	                .orElseThrow(() -> new RuntimeException("Car not found"));

	        BookCarEntity bookCarEntity = modelMapper.map(bookCarDto, BookCarEntity.class);
	        bookCarEntity.setCustomer(customer);
	        bookCarEntity.setCarId(car); 

	        BookCarEntity savedBooking = bookCarRepository.save(bookCarEntity);

	        car.setCarStatus("Car On Lease");
	        carRespository.save(car);

	        HistoryEntity history = new HistoryEntity();
	        history.setCar(car);
	        history.setStartDate(savedBooking.getStartDate());
	        history.setEndDate(savedBooking.getEndDate());
	        history.setDepositAmount(savedBooking.getDepositAmount());
	        history.setBalanceAmount(savedBooking.getBalanceAmount());
	        history.setFuelFilled(savedBooking.getFuelFilled());
	        history.setCustomerId(customer);
	        
	        historyRepository.save(history);

	        responseDto = modelMapper.map(savedBooking, BookCarDto.class);
	    } catch (Exception e) {
	        logger.error("AdminServiceImpl :: bookCar :: Error - " + e.getMessage(), e);
	    }

	    return responseDto;
	}


}
