package com.trimble_cars.service;


import java.util.List;

import com.trimble_cars.dto.AdminDto;
import com.trimble_cars.dto.CarDto;


public interface AdminService {

	AdminDto createAdmin(AdminDto adminDto);

	AdminDto updateAdmin(Long id, AdminDto adminDto);

	List<CarDto> getAllcars();


}
