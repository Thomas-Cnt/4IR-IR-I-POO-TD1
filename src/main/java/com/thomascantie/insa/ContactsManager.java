package com.thomascantie.insa;

public class ContactsManager {

    private Contact theContact;

    public void addContact(String name, String email, String phoneNumber) {
		this.theContact = new Contact(name, email, phoneNumber);
    }

    public void printAllContacts() {
		System.out.println(this.theContact.name);
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
	}
}
