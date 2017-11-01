package com.thomascantie.insa;

import java.util.StringJoiner;

class ContactStringBuilder {

	private final StringJoiner joiner = new StringJoiner(", ");

	public static ContactStringBuilder aContactString() {
		return new ContactStringBuilder();
	}

	public ContactStringBuilder with(String ... data) {
		for (String str : data) {
			if (str != null)
				this.joiner.add(str);
		}
		return this;
	}
	public String build() {
		return this.joiner.toString();
	}
}

