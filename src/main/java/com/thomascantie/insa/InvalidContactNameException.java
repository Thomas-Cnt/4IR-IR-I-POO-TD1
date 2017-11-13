package com.thomascantie.insa;

public class InvalidContactNameException extends Exception {

	public InvalidContactNameException(String name) {
		super("Invalid contact name : \"" + name + "\"");
	}
}
