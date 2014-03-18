package com.sopovs.moradanen.zkoss.popups;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CarServiceImpl implements CarService {

	private List<Car> cars = new ArrayList<Car>(20);
	private Map<String, List<Car>> queryResults;
	private Category rootCategory;

	public CarServiceImpl() {

		Random r = new Random(0L); // random but same data
		String[][] raw = {{"China", "Geely", "Panda", "Sedan"}, {"France", "Peugeot", "RCZ", "Sport"},
				{"France", "Renault", "Megane", "Sedan"}, {"Germany", "Audi", "TT", "Sport"},
				{"Germany", "BMW", "X3", "SUV"}, {"Germany", "Mercedes-Benz", "Sprinter", "Van"},
				{"Germany", "Volkswagen", "Transporter", "Van"}, {"Germany", "Porsche", "Cayenne", "SUV"},
				{"Italy", "Ferrari", "F430", "Supercar"}, {"Italy", "Lamborghini", "Gallardo", "Supercar"},
				{"Japan", "Toyota", "Camry", "Sedan"}, {"Japan", "Mitsubishi", "Pajero", "SUV"},
				{"Japan", "Nissan", "GT-R", "Sport"}, {"Japan", "Subaru", "Legacy", "MPV"},
				{"Korea", "Hyundai", "Tucson", "SUV"}, {"Korea", "Kia", "Rio", "Sedan"},
				{"Sweden", "Volvo", "S40", "Sedan"}, {"Taiwan", "LUXGEN", "Luxgen7", "MPV"},
				{"United States", "Ford", "Focus", "Sedan"},
				{"United Kingdom", "Land Rover", "Discovery", "SUV"}};
		int[] disp = new int[]{1600, 1800, 2000, 2400, 3000, 3200, 3500}; // engine displacement
		double[] cost = new double[]{19999.9, 24999.9, 29999.9, 34999.9, 39999.9}; // cost

		// create in-memory data
		for(int i = 0; i < 20; ++i) {
			Car car = new Car();
			car.setCountry(raw[i][0]);
			car.setMake(raw[i][1]);
			car.setModel(raw[i][2]);
			car.setType(raw[i][3]);
			cars.add(car);
		}
		Collections.shuffle(cars, r);
		for(int i = 0; i < 20; ++i) {
			// generate random data set
			Accessories accessories = new Accessories(r.nextFloat() > 0.3f, r.nextFloat() > 0.3f,
					r.nextFloat() > 0.3f, r.nextFloat() > 0.3f);
			List<String> salesmen = new ArrayList<String>(CarData.getSalesmen());
			Collections.shuffle(salesmen);
			salesmen = salesmen.subList(0, r.nextInt(2) + 1);
			// modify a car
			Car car = cars.get(i);
			car.setCarId(String.valueOf(i));
			car.setPicture("car" + (r.nextInt(6) + 1));
			car.setCost(cost[r.nextInt(cost.length)]);
			car.setEngineDisplacement(disp[r.nextInt(disp.length)]);
			car.setAutoTransmission(r.nextBoolean());
			car.setAccessories(accessories);
			car.setSalesmen(new HashSet<String>(salesmen));
		}

		// create categories and query results
		rootCategory = new Category("", "All cars");
		queryResults = new HashMap<String, List<Car>>();
		for(String type : CarData.getTypes()) {
			String typeFilter = "type = " + type;
			String typeLowFilter = typeFilter + " & displacement <= 2000";
			String typeHighFilter = typeFilter + " & displacement > 2000";
			Category typeCategory = new Category(typeFilter, type);
			typeCategory.addChild(new Category(typeLowFilter, "< 2.0L(inc.)"));
			typeCategory.addChild(new Category(typeHighFilter, "> 2.0L"));
			rootCategory.addChild(typeCategory);
			queryResults.put(typeFilter, new LinkedList<Car>());
			queryResults.put(typeLowFilter, new LinkedList<Car>());
			queryResults.put(typeHighFilter, new LinkedList<Car>());
		}
		for(Car car : findAll()) {
			String typeFilter = "type = " + car.getType();
			queryResults.get(typeFilter).add(car);
			String typeDispFilter = car.getEngineDisplacement() > 2000 ? " & displacement > 2000"
					: " & displacement <= 2000";
			queryResults.get(typeFilter + typeDispFilter).add(car);
		}
	}

	public List<Car> findAll() {
		return cars;
	}

	public void store(Car car) {
		// process data
		// ...
	}

//	public void store(InventoryItem inventoryItem) {
//		// process data
//		// ...
//	}

	public void order(List<OrderItem> orderItems) {
		// process data
		// ...
	}

	public Category getCarCategoriesRoot() {
		return rootCategory;
	}

	public int countByFilter(String filter) {
		// fake count
		return queryByFilter(filter).size();
	}

	public List<Car> queryByFilter(String filter) {
		// fake query
		return (filter == null || filter.length() <= 0) ? findAll() : queryResults.get(filter);
	}
}
