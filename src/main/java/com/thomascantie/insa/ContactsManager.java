package com.thomascantie.insa;

public class ContactsManager {

    private Contact theContact;

    public void addContact(String name, String email, String phoneNumber) {
		this.theContact = new Contact(name, email, phoneNumber);
    }

    public void printAllContacts() {
		if (this.theContact.email == null && this.theContact.phoneNumber == null)
			System.out.println(this.theContact.name);
		else if (this.theContact.email == null)
			System.out.println(this.theContact.name + ", " + this.theContact.phoneNumber);
		else
			System.out.println(this.theContact.name + ", " + this.theContact.email);
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
