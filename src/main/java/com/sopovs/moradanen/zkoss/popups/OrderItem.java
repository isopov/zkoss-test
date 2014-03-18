package com.sopovs.moradanen.zkoss.popups;

import org.zkoss.bind.annotation.DependsOn;

public class OrderItem {
	private String itemId;
	private int quantity;
	private double unitPrice;
	private Car car;

	public OrderItem(String itemId, int quantity, double unitPrice, Car car) {
		this.itemId = itemId;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.car = car;
	}

	@DependsOn({"unitPrice", "quantity"})
	public double getSubtotalPrice() {
		return unitPrice * quantity;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

}
