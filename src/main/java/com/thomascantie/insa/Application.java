package com.thomascantie.insa;

public class Application {

	private static final String FILENAME = "manager-data.csv";

	public static void main(String[] args) {

		new Runner(new ManagerDAO(FILENAME)).run();

	}

}
