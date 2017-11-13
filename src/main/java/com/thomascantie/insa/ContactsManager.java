package com.thomascantie.insa;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ContactsManager {
	
	private List<Contact> contacts = new ArrayList<Contact>();
	
	public void addContact(String name, String email, String phoneNumber) throws InvalidContactNameException, InvalidEmailException {
		if (name == null || name.isEmpty())
			throw new InvalidContactNameException(name);
		if (email != null && !email.matches("[A-Za-z0-9._-]+@[a-z0-9._-]{2,}.[a-z]{2,4}"))
			throw new InvalidEmailException(email);
		this.contacts.add(new Contact(name, email, phoneNumber));
	}
	
	public void printAllContacts() {
		for (Contact aContact : this.contacts)
			System.out.println(aContact);
	}
	
	public void searchContactByName(String name) {
		int i;
		for (i = 0; isIndexInTheList(i); i++) {
			if (isIndexForAContactWithName(name, i))
				System.out.println(this.contacts.get(i));
		}
	}

	public void updateContact(String name, String updatedName, String updatedEmail, String updatedPhoneNumber) throws InvalidContactNameException, InvalidEmailException {
		if (updatedName == null)
			throw new InvalidContactNameException(updatedName);
		if (updatedEmail != null && !updatedEmail.isEmpty() && !updatedEmail.matches("[A-Za-z0-9._-]+@[a-z0-9._-]{2,}.[a-z]{2,4}"))
			throw new InvalidEmailException(updatedEmail);

		int pos = getIndexForContactWithName(name);
		if (isIndexInTheList(pos)) {
			Contact theContact = this.contacts.get(pos);
			this.contacts.set(pos, new Contact(
					(updatedName.isEmpty() ? theContact.getName() : updatedName),
					(updatedEmail.isEmpty() ? theContact.getEmail() : updatedEmail),
					(updatedPhoneNumber.isEmpty() ? theContact.getPhoneNumber() : updatedPhoneNumber)));
		}
	}

	public boolean hasContactWithName(String name) {
		return isIndexInTheList(getIndexForContactWithName(name));
	}

	public List<String[]> getAllContacts() {
		List<String[]> list = new ArrayList<String[]>();
		for (Contact aContact: this.contacts) {
			list.add(aContact.toString().split(", "));
		}
		return list;
	}

	private int getIndexForContactWithName(String name) {
		int i;
		for (i = 0; isIndexInTheList(i) && !isIndexForAContactWithName(name, i); i++) {
		}
		return i;
	}
	
	private boolean isIndexForAContactWithName(String name, int i) {
		return this.contacts.get(i).hasNameLike(name);
	}
	
	private boolean isIndexInTheList(int i) {
		return i < this.contacts.size();
	}
	
}

