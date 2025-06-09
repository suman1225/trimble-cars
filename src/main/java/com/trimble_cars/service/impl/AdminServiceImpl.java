package com.trimble_cars.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.trimble_cars.dto.AdminDto;
import com.trimble_cars.dto.CarDto;
import com.trimble_cars.entity.AdminEntity;
import com.trimble_cars.entity.CarEntity;
import com.trimble_cars.repository.AdminRepository;
import com.trimble_cars.repository.CarRespository;
import com.trimble_cars.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

	private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	AdminRepository adminRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	CarRespository carRespository;

	@Override
	public AdminDto createAdmin(AdminDto adminDto) {
		logger.debug("AdminServiceImpl :: createAdmin :: Entered");
		AdminDto admin = null;
		AdminEntity adminEntity = null;
		try {
			adminDto.setPassword(passwordEncoder.encode(adminDto.getPassword()));
			adminEntity = modelMapper.map(adminDto, AdminEntity.class);

			AdminEntity save = adminRepository.save(adminEntity);
			admin = modelMapper.map(save, AdminDto.class);
		} catch (Exception e) {
			logger.error("AdminServiceImpl :: createAdmin :: Error" + e.getMessage());
		}
		logger.debug("AdminServiceImpl :: createAdmin :: Exit");
		return admin;
	}

	@Override()
	public AdminDto updateAdmin(Long id, AdminDto adminDto) {
		logger.debug("AdminServiceImpl :: updateAdmin :: Entered");
		AdminEntity adminEntity = null;
		AdminDto admin = null;
		try {
			adminEntity = adminRepository.findById(id).orElseThrow(() -> new RuntimeException("Admin Not Found"));

			adminEntity.setAdminName(adminDto.getAdminName());
			adminEntity.setMobileNumber(adminDto.getMobileNumber());
			adminEntity.setUserName(adminDto.getUserName());
			adminEntity.setPassword(passwordEncoder.encode(adminDto.getPassword()));

			AdminEntity save = adminRepository.save(adminEntity);
			admin = modelMapper.map(save, AdminDto.class);
			logger.debug("AdminServiceImpl :: updateAdmin :: Entered");
		} catch (Exception e) {
			logger.error("AdminServiceImpl :: updateAdmin :: Error" + e.getMessage());
		}
		return admin;
	}

	@Override
	public List<CarDto> getAllcars() {
		logger.debug("AdminServiceImpl :: getAllcars :: Entered");
		List<CarDto> allCars = new ArrayList<>();
		try {
			List<CarEntity> findAll = carRespository.findAll();

			for (CarEntity car : findAll) {
				CarDto map = modelMapper.map(car, CarDto.class);
				allCars.add(map);
				logger.debug("AdminServiceImpl :: getAllcars :: Entered");
			}

		} catch (Exception e) {
			logger.error("AdminServiceImpl :: getAllcars :: Error" + e.getMessage());
		}
		return allCars;
	}

}
