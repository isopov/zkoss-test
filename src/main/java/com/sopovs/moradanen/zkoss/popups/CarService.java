package com.sopovs.moradanen.zkoss.popups;

import java.util.List;

public interface CarService {

	/**
	 * Retrieve all cars in the car store.
	 * @return all cars.
	 */
	public List<Car> findAll();

	/**
	 * Store or modify a car in car store.
	 */
	void store(Car car);

//	/**
//	 * Store or modify a inventory item in car store.
//	 */
//	void store(InventoryItem inventoryItem);

	/**
	 * Order cars.
	 */
	void order(List<OrderItem> orderItems);

	/**
	 * Retrieve the root of car categories.
	 */
	Category getCarCategoriesRoot();

	/**
	 * Count cars by filter.
	 */
	int countByFilter(String filter);

	/**
	 * Query cars by filter.
	 */
	List<Car> queryByFilter(String filter);
}
