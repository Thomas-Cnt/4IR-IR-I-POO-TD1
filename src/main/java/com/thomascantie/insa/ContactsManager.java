package com.thomascantie.insa;

public class ContactsManager {

    private Contact theContact;

    public void addContact(String name, String email, String phoneNumber) {
		this.theContact = new Contact(name, email, phoneNumber);
    }

    public void printAllContacts() {
		System.out.println(this.theContact);
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
