package com.trimble_cars.service.impl.customer;

import com.trimble_cars.dto.BookCarDto;
import com.trimble_cars.dto.CustomerDto;
import com.trimble_cars.entity.*;
import com.trimble_cars.repository.*;
import com.trimble_cars.repository.customer.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceImplTest {

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock private CustomerRepository customerRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private BookCarRepository bookCarRepository;
    @Mock private CarRespository carRespository;
    @Mock private HistoryRepository historyRepository;
    @Mock private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

  
    @Test
    void testCreateCustomer_success() {
        CustomerDto dto = new CustomerDto();
        dto.setPassword("rawPass");
        CustomerEntity entity = new CustomerEntity();
        CustomerEntity savedEntity = new CustomerEntity();
        CustomerDto expectedDto = new CustomerDto();

        when(passwordEncoder.encode("rawPass")).thenReturn("encodedPass");
        when(modelMapper.map(dto, CustomerEntity.class)).thenReturn(entity);
        when(customerRepository.save(entity)).thenReturn(savedEntity);
        when(modelMapper.map(savedEntity, CustomerDto.class)).thenReturn(expectedDto);

        CustomerDto result = customerService.createCustomer(dto);

        assertNotNull(result);
        verify(customerRepository).save(entity);
    }

    
    @Test
    void testCreateCustomer_exception() {
        CustomerDto dto = new CustomerDto();
        dto.setPassword("rawPass");

        when(passwordEncoder.encode(anyString())).thenThrow(new RuntimeException("Encryption failed"));

        CustomerDto result = customerService.createCustomer(dto);

        assertNull(result);
    }

    @Test
    void testUpdateCustomer_success() {
        Long customerId = 1L;
        CustomerDto updateDto = new CustomerDto();
        updateDto.setPassword("newPass");
        CustomerEntity existingEntity = new CustomerEntity();

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(existingEntity));
        when(passwordEncoder.encode("newPass")).thenReturn("encodedPass");
        when(customerRepository.save(existingEntity)).thenReturn(existingEntity);
        when(modelMapper.map(existingEntity, CustomerDto.class)).thenReturn(updateDto);

        CustomerDto result = customerService.updateCustomer(customerId, updateDto);

        assertNotNull(result);
        verify(customerRepository).save(existingEntity);
    }

    @Test
    void testUpdateCustomer_customerNotFound() {
        Long customerId = 1L;
        CustomerDto updateDto = new CustomerDto();

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> customerService.updateCustomer(customerId, updateDto));
    }

    @Test
    void testBookCar_success() {
        Long customerId = 1L;
        Long carId = 100L;
        BookCarDto bookCarDto = new BookCarDto();
        bookCarDto.setCarId(carId);

        CustomerEntity customer = new CustomerEntity();
        CarEntity car = new CarEntity();
        BookCarEntity bookEntity = new BookCarEntity();

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(carRespository.findById(carId)).thenReturn(Optional.of(car));
        when(modelMapper.map(bookCarDto, BookCarEntity.class)).thenReturn(bookEntity);
        when(bookCarRepository.save(bookEntity)).thenReturn(bookEntity);
        when(modelMapper.map(bookEntity, BookCarDto.class)).thenReturn(bookCarDto);

        BookCarDto result = customerService.bookCar(bookCarDto, customerId);

        assertNotNull(result);
        verify(bookCarRepository).save(bookEntity);
        verify(historyRepository).save(any());
    }

    @Test
    void testBookCar_customerNotFound() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());
        BookCarDto dto = new BookCarDto();

        assertThrows(RuntimeException.class, () -> customerService.bookCar(dto, 1L));
    }

    @Test
    void testBookCar_carNotFound() {
        BookCarDto dto = new BookCarDto();
        dto.setCarId(99L);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(new CustomerEntity()));
        when(carRespository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> customerService.bookCar(dto, 1L));
    }

    @Test
    void testBookCar_exceptionThrown() {
        BookCarDto dto = new BookCarDto();
        dto.setCarId(1L);

        when(customerRepository.findById(anyLong())).thenThrow(new RuntimeException("DB error"));

        BookCarDto result = customerService.bookCar(dto, 1L);

        assertNull(result);
    }
}
