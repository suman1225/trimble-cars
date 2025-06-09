package com.trimble_cars.service;

import java.util.List;

import com.trimble_cars.dto.CarDto;
import com.trimble_cars.dto.CarOwnerDto;

public interface CarOwnerService {

	CarOwnerDto createCarOwner(CarOwnerDto carOwnerDto);

	CarDto createCar(CarDto carDto);

	CarOwnerDto updateCarOwner(Long id, CarOwnerDto carOwnerDto);

	List<CarDto> getCars(Long id);

}
