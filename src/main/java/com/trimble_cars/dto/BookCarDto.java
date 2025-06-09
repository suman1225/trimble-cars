package com.trimble_cars.dto;

import java.time.LocalDateTime;

public class BookCarDto {

	private Long id;

	private Long carId;

	private LocalDateTime startDate;

	private LocalDateTime endDate;
	
	private Double depositAmount;

	private Double balanceAmount;
	
	private Double fuelFilled;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

}
