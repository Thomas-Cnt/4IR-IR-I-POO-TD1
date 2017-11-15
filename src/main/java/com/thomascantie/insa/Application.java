package com.thomascantie.insa;

import com.thomascantie.insa.ManagerDAO;

public class Application {

	private static final String FILENAME = "manager-data.csv";

	public static void main(String[] args) {

		new Runner(new ManagerDAO(FILENAME)).run();

	}

}
