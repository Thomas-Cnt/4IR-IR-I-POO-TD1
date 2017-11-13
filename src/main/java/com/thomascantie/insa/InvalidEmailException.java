package com.thomascantie.insa;

public class InvalidEmailException extends Exception {

	public InvalidEmailException(String email) {
		super("Invalid email : \"" + email + "\"");
	}
}
