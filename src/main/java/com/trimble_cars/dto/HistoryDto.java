package com.trimble_cars.dto;

import java.time.LocalDateTime;

public class HistoryDto {

	private Long id;

	private String customerName;

	private Long carId;

	private LocalDateTime startDate;

	private LocalDateTime endDate;

	private Double depositAmount;

	private Double balanceAmount;

	private Double fuelFilled;

	private CustomerDto customerId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Long getCarId() {
		return carId;
	}

	public void setCarId(Long carId) {
		this.carId = carId;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public Double getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(Double depositAmount) {
		this.depositAmount = depositAmount;
	}

	public Double getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(Double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public Double getFuelFilled() {
		return fuelFilled;
	}

	public void setFuelFilled(Double fuelFilled) {
		this.fuelFilled = fuelFilled;
	}

	public CustomerDto getCustomerId() {
		return customerId;
	}

	public void setCustomerId(CustomerDto customerId) {
		this.customerId = customerId;
	}

}
