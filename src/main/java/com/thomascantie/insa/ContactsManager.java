package com.thomascantie.insa;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class ContactsManager {

    private List<Contact> contacts = new ArrayList<Contact>();

    public void addContact(String name, String email, String phoneNumber) {
		this.contacts.add(new Contact(name, email, phoneNumber));
    }

    public void printAllContacts() {
    	for (Contact aContact : this.contacts)
			System.out.println(aContact);
    }

    public void searchContactByName(String name) {
		int i;
		for (i = 0; i < this.contacts.size() && !this.contacts.get(i).name.toLowerCase().contains(name.toLowerCase()); i++) {}
		if (i < this.contacts.size())
			System.out.println(this.contacts.get(i));
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
			if (this.email == null && this.phoneNumber == null)
				return this.name;
			else if (this.email == null)
				return new StringJoiner(", ").add(this.name).add(this.phoneNumber).toString();
			else if (this.phoneNumber == null)
				 return new StringJoiner(", ").add(this.name).add(this.email).toString();
			else
				return new StringJoiner(", ").add(this.name).add(this.email).add(this.phoneNumber).toString();
		}
	}
}
