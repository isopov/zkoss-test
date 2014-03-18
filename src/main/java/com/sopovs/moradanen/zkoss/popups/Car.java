package com.sopovs.moradanen.zkoss.popups;

import java.util.Set;

public class Car {

	private String carId;
	private String model;
	private String picture;
	private String make;
	private String country;
	private String type;
	private double cost;
	private int engineDisplacement;
	private boolean autoTransmission;
	private Accessories accessories;
	private Set<String> salesmen;

	public Car() {
	}

	public String getCarId() {
		return carId;
	}

	public void setCarId(String carId) {
		this.carId = carId;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public int getEngineDisplacement() {
		return engineDisplacement;
	}

	public void setEngineDisplacement(int engineDisplacement) {
		this.engineDisplacement = engineDisplacement;
	}

	public boolean isAutoTransmission() {
		return autoTransmission;
	}

	public void setAutoTransmission(boolean autoTransmission) {
		this.autoTransmission = autoTransmission;
	}

	public Accessories getAccessories() {
		return accessories;
	}

	public void setAccessories(Accessories accessories) {
		this.accessories = accessories;
	}

	public Set<String> getSalesmen() {
		return salesmen;
	}

	public void setSalesmen(Set<String> salesmen) {
		this.salesmen = salesmen;
	}

	public String toString() {
		return model;
	}
}
