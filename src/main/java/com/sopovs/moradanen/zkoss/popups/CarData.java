package com.sopovs.moradanen.zkoss.popups;

import java.util.Arrays;
import java.util.List;

public class CarData {

	public static List<String> getAccessories() {
		return Arrays.asList(new String[]{"ABS", "Airbag", "GPS", "Keyless"});
	}

	public static List<String> getCountries() {
		return Arrays.asList(new String[]{"China", "France", "Germany", "Italy", "Japan", "Korea", "Sweden",
				"Taiwan", "United Kingdom", "United States"});
	}

	public static List<String> getSalesmen() {
		return Arrays.asList(new String[]{"Adam", "Brian", "Cary", "Danny", "Edward", "Franklin", "Geroge"});
	}

	public static List<String> getTypes() {
		return Arrays.asList(new String[]{"MPV", "SUV", "Sedan", "Sport", "Supercar", "Van"});
	}

}
