package com.thomascantie.insa;

import java.util.ArrayList;
import java.util.List;

public class ContactsManager {

	private List<Contact> contacts = new ArrayList<Contact>();

	public void addContact(String name, String email, String phoneNumber) throws InvalidContactNameException, InvalidEmailException {
		if (name == null || name.isEmpty())
			throw new InvalidContactNameException();
		if (email != null && !email.matches("[A-Za-z0-9._-]+@[a-z0-9._-]{2,}.[a-z]{2,4}"))
			throw new InvalidEmailException();
		this.contacts.add(new Contact(name, email, phoneNumber));
	}

	public void printAllContacts() {
		for (Contact aContact : this.contacts)
			System.out.println(aContact);
	}

	public void searchContactByName(String name) {
		int i;
		for (i = 0; isIndexInTheList(i) && isNotIndexForAContactWithName(name, i); i++) {
		}
		if (isIndexInTheList(i))
			System.out.println(this.contacts.get(i));
	}

	private boolean isNotIndexForAContactWithName(String name, int i) {
		return !this.contacts.get(i).name.toLowerCase().contains(name.toLowerCase());
	}

	private boolean isIndexInTheList(int i) {
		return i < this.contacts.size();
	}

	private class Contact {

		private String name;
		private String email;
		private String phoneNumber;

		public Contact(String name, String email, String phoneNumber) {
			this.name = name;
			this.email = email;
			this.phoneNumber = phoneNumber;
		}

		@Override
		public String toString() {
			return ContactStringBuilder.aContactString()
					.with(this.name, this.email, this.phoneNumber)
					.build();
		}
	}
}

