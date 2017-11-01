package com.thomascantie.insa;

import java.util.ArrayList;
import java.util.List;

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
				return this.name + ", " + this.phoneNumber;
			else if (this.phoneNumber == null)
				 return this.name + ", " + this.email;
			else
				return this.name + ", " + this.email + ", " + this.phoneNumber;
		}
	}
}
