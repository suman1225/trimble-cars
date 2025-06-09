package com.trimble_cars.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.trimble_cars.dto.CarDto;
import com.trimble_cars.dto.CarOwnerDto;
import com.trimble_cars.entity.CarEntity;
import com.trimble_cars.entity.CarOwnerEntity;
import com.trimble_cars.repository.CarRespository;
import com.trimble_cars.repository.CarownerRepository;
import com.trimble_cars.service.CarOwnerService;

@Service
public class CarOwnerServiceImpl implements CarOwnerService {

	private static final Logger logger = LoggerFactory.getLogger(CarOwnerServiceImpl.class);

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	CarownerRepository carOwnerRepository;

	@Autowired
	CarRespository carRespository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public CarOwnerDto createCarOwner(CarOwnerDto carOwnerDto) {
		logger.debug("CarOwnerServiceImpl :: createCarOwner :: Entered");
		CarOwnerDto carOwner = null;
		CarOwnerEntity carOwnerEntity = null;
		try {
			carOwnerDto.setPassword(passwordEncoder.encode(carOwnerDto.getPassword()));
			carOwnerEntity = modelMapper.map(carOwnerDto, CarOwnerEntity.class);

			CarOwnerEntity save = carOwnerRepository.save(carOwnerEntity);
			carOwner = modelMapper.map(save, CarOwnerDto.class);
		} catch (Exception e) {
			logger.error("CarOwnerServiceImpl :: createCarOwner :: Error" + e.getMessage());
			return null;
		}
		logger.debug("CarOwnerServiceImpl :: createCarOwner :: Exit");
		return carOwner;
	}

	@Override
	public CarDto createCar(CarDto carDto) {
		logger.debug("CarOwnerServiceImpl :: createCar :: Entered");
		CarEntity savedCar = null;
		CarDto savedDto = null;
		try {
			CarEntity car = modelMapper.map(carDto, CarEntity.class);

			savedCar = carRespository.save(car);

			savedDto = modelMapper.map(savedCar, CarDto.class);
		} catch (Exception e) {
			logger.error("CarOwnerServiceImpl :: createCar :: Error" + e.getMessage());
			return null;
		}
		logger.debug("CarOwnerServiceImpl :: createCar :: Exit");
		return savedDto;
	}

	@Override
	public CarOwnerDto updateCarOwner(Long id, CarOwnerDto carOwnerDto) {
		logger.debug("CarOwnerServiceImpl :: updateCarOwner :: Entered");
		CarOwnerEntity carOwnerEntity = null;
		CarOwnerDto admin = null;
		try {
			carOwnerEntity = carOwnerRepository.findById(id)
					.orElseThrow(() -> new RuntimeException("CarOwner Not Found"));

			carOwnerEntity.setOwnerName(carOwnerDto.getOwnerName());
			carOwnerEntity.setMobileNumber(carOwnerDto.getMobileNumber());
			carOwnerEntity.setUserName(carOwnerDto.getUserName());
			carOwnerEntity.setPassword(passwordEncoder.encode(carOwnerDto.getPassword()));

			CarOwnerEntity save = carOwnerRepository.save(carOwnerEntity);
			admin = modelMapper.map(save, CarOwnerDto.class);
			logger.debug("CarOwnerServiceImpl :: updateCarOwner :: Entered");
		} catch (Exception e) {
			logger.error("CarOwnerServiceImpl :: updateCarOwner :: Error" + e.getMessage());
		}
		return admin;
	}

	@Override
	public List<CarDto> getCars(Long id) {
		logger.debug("CarOwnerServiceImpl :: getCars :: Entered");
		List<CarDto> cars = new ArrayList<>();

		try {
			List<CarEntity> allCars = carOwnerRepository.findOwnerByid(id);
			for (CarEntity car : allCars) {
				CarDto map = modelMapper.map(car, CarDto.class);
				cars.add(map);
			}
			logger.debug("CarOwnerServiceImpl :: getCars :: Entered");
		} catch (Exception e) {
			logger.error("CarOwnerServiceImpl :: getCars :: Error" + e.getMessage());
		}
		return cars;
	}

}
