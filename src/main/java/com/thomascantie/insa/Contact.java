package com.thomascantie.insa;

import java.util.StringJoiner;

class Contact {
	
	private String name;
	private String email;
	private String phoneNumber;
	
	public Contact(String name, String email, String phoneNumber) {
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}
	
	public boolean hasNameLike(String str) {
		return this.name.toLowerCase().contains(str.toLowerCase());
	}

	@Override
	public String toString() {
		return ContactStringBuilder.aContactString()
				.with(this.name, this.email, this.phoneNumber)
				.build();
	}
	
}

class ContactStringBuilder {
	
	private final StringJoiner joiner = new StringJoiner(", ");
	
	public static ContactStringBuilder aContactString() {
		return new ContactStringBuilder();
	}
	
	public ContactStringBuilder with(String ... data) {
		for (String str : data) {
			if (str != null && !str.isEmpty())
				this.joiner.add(str);
		}
		return this;
	}
	public String build() {
		return this.joiner.toString();
	}
}