package com.trimble_cars.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.trimble_cars.dto.CarDto;
import com.trimble_cars.dto.CarOwnerDto;
import com.trimble_cars.entity.CarEntity;
import com.trimble_cars.entity.CarOwnerEntity;
import com.trimble_cars.repository.CarRespository;
import com.trimble_cars.repository.CarownerRepository;

class CarOwnerServiceImplTest {

    @InjectMocks
    private CarOwnerServiceImpl carOwnerService;

    @Mock
    private CarownerRepository carOwnerRepository;

    @Mock
    private CarRespository carRespository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    private CarOwnerDto carOwnerDto;
    private CarOwnerEntity carOwnerEntity;

    private CarDto carDto;
    private CarEntity carEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        carOwnerDto = new CarOwnerDto();
        carOwnerDto.setOwnerName("John");
        carOwnerDto.setMobileNumber("1234567890");
        carOwnerDto.setUserName("john123");
        carOwnerDto.setPassword("password");

        carOwnerEntity = new CarOwnerEntity();
        carOwnerEntity.setOwnerName("John");
        carOwnerEntity.setMobileNumber("1234567890");
        carOwnerEntity.setUserName("john123");
        carOwnerEntity.setPassword("encodedPassword");

        carDto = new CarDto();
        carDto.setCarName("Honda");

        carEntity = new CarEntity();
        carEntity.setCarName("Honda");
    }

   
    @Test
    void testCreateCarOwner_Positive() {
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(modelMapper.map(carOwnerDto, CarOwnerEntity.class)).thenReturn(carOwnerEntity);
        when(carOwnerRepository.save(carOwnerEntity)).thenReturn(carOwnerEntity);
        when(modelMapper.map(carOwnerEntity, CarOwnerDto.class)).thenReturn(carOwnerDto);

        CarOwnerDto result = carOwnerService.createCarOwner(carOwnerDto);

        assertNotNull(result);
        assertEquals("John", result.getOwnerName());
    }

    @Test
    void testCreateCar_Positive() {
        when(modelMapper.map(carDto, CarEntity.class)).thenReturn(carEntity);
        when(carRespository.save(carEntity)).thenReturn(carEntity);
        when(modelMapper.map(carEntity, CarDto.class)).thenReturn(carDto);

        CarDto result = carOwnerService.createCar(carDto);

        assertNotNull(result);
        assertEquals("Honda", result.getCarName());
    }

    @Test
    void testUpdateCarOwner_Positive() {
        Long id = 1L;

        when(carOwnerRepository.findById(id)).thenReturn(Optional.of(carOwnerEntity));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(carOwnerRepository.save(any(CarOwnerEntity.class))).thenReturn(carOwnerEntity);
        when(modelMapper.map(carOwnerEntity, CarOwnerDto.class)).thenReturn(carOwnerDto);

        CarOwnerDto result = carOwnerService.updateCarOwner(id, carOwnerDto);

        assertNotNull(result);
        assertEquals("John", result.getOwnerName());
    }

    @Test
    void testGetCars_Positive() {
        Long ownerId = 1L;
        List<CarEntity> carEntities = List.of(carEntity);

        when(carOwnerRepository.findOwnerByid(ownerId)).thenReturn(carEntities);
        when(modelMapper.map(any(CarEntity.class), eq(CarDto.class))).thenReturn(carDto);

        List<CarDto> result = carOwnerService.getCars(ownerId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Honda", result.get(0).getCarName());
    }

    @Test
    void testCreateCarOwner_Exception() {
        when(modelMapper.map(any(), any())).thenThrow(RuntimeException.class);

        CarOwnerDto result = carOwnerService.createCarOwner(carOwnerDto);

        assertNull(result);
    }

    @Test
    void testCreateCar_Exception() {
        when(carRespository.save(any())).thenThrow(RuntimeException.class);

        CarDto result = carOwnerService.createCar(carDto);

        assertNull(result);
    }

    @Test
    void testUpdateCarOwner_NotFound() {
        when(carOwnerRepository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () ->
                carOwnerService.updateCarOwner(99L, carOwnerDto));

        assertEquals("CarOwner Not Found", thrown.getMessage());
    }

    @Test
    void testGetCars_Exception() {
        when(carOwnerRepository.findOwnerByid(anyLong())).thenThrow(RuntimeException.class);

        List<CarDto> result = carOwnerService.getCars(1L);

        assertTrue(result.isEmpty());
    }
}
