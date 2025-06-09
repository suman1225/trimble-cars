package com.trimble_cars.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import com.trimble_cars.dto.AdminDto;
import com.trimble_cars.dto.CarDto;
import com.trimble_cars.entity.AdminEntity;
import com.trimble_cars.entity.CarEntity;
import com.trimble_cars.repository.AdminRepository;
import com.trimble_cars.repository.CarRespository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AdminServiceImplTest {

    private AdminServiceImpl adminService;
    private AdminRepository adminRepository;
    private CarRespository carRepository;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup() {
        adminRepository = mock(AdminRepository.class);
        carRepository = mock(CarRespository.class);
        modelMapper = new ModelMapper();
        passwordEncoder = mock(PasswordEncoder.class);

        adminService = new AdminServiceImpl();
        adminService.adminRepository = adminRepository;
        adminService.carRespository = carRepository;
        adminService.modelMapper = modelMapper;
        adminService.passwordEncoder = passwordEncoder;
    }

    @Test
    public void testCreateAdmin_Positive() {
        AdminDto adminDto = new AdminDto();
        adminDto.setPassword("admin123");

        AdminEntity entity = new AdminEntity();
        entity.setPassword("encodedPassword");

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(adminRepository.save(any(AdminEntity.class))).thenReturn(entity);

        AdminDto result = adminService.createAdmin(adminDto);

        assertNotNull(result);
        assertEquals("encodedPassword", result.getPassword());
    }

    @Test
    public void testCreateAdmin_Exception_ReturnsNull() {
        AdminDto adminDto = new AdminDto();
        adminDto.setPassword("admin123");

        when(passwordEncoder.encode(anyString())).thenThrow(new RuntimeException("Encoding failed"));

        AdminDto result = adminService.createAdmin(adminDto);

        assertNull(result);
    }

    
    @Test
    public void testUpdateAdmin_Positive() {
        Long adminId = 1L;

        AdminDto updateDto = new AdminDto();
        updateDto.setAdminName("suman");
        updateDto.setMobileNumber("1234567890");
        updateDto.setUserName("updatedUser");
        updateDto.setPassword("newPass");

        AdminEntity existingAdmin = new AdminEntity();
        existingAdmin.setId(adminId);

        when(adminRepository.findById(adminId)).thenReturn(Optional.of(existingAdmin));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedNewPass");
        when(adminRepository.save(any(AdminEntity.class))).thenReturn(existingAdmin);

        AdminDto result = adminService.updateAdmin(adminId, updateDto);

        assertNotNull(result);
    }

   
    @Test
    public void testUpdateAdmin_AdminNotFound_ReturnsNull() {
        Long adminId = 2L;
        AdminDto updateDto = new AdminDto();

        when(adminRepository.findById(adminId)).thenReturn(Optional.empty());

        AdminDto result = adminService.updateAdmin(adminId, updateDto);

        assertNull(result);
    }

    
    @Test
    public void testGetAllCars_Positive() {
        CarEntity car1 = new CarEntity();
        car1.setId(1L);
        car1.setCarName("Swift");

        CarEntity car2 = new CarEntity();
        car2.setId(2L);
        car2.setCarName("Innovo");

        List<CarEntity> cars = Arrays.asList(car1, car2);
        when(carRepository.findAll()).thenReturn(cars);

        List<CarDto> result = adminService.getAllcars();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testGetAllCars_Exception_ReturnsEmptyList() {
        when(carRepository.findAll()).thenThrow(new RuntimeException("DB Error"));

        List<CarDto> result = adminService.getAllcars();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
