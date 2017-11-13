package com.thomascantie.insa;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

public class ContactsManagerMust {

	private static final String FIELD_SEPARATOR = ", ";

    private static final String NICOLE_FERRONI_NAME = "Nicole Ferroni";
    private static final String NICOLE_FERRONI_EMAIL = "contact@nicoleferroni.fr";
    private static final String NICOLE_FERRONI_PHONE_NUMBER = "0651387945";

    private static final String GUILLAUME_MEURICE_NAME = "Guillaume Meurice";
    private static final String GUILLAUME_MEURICE_EMAIL = "contact@guillaumemeurice.fr";
    private static final String GUILLAUME_MEURICE_PHONE_NUMBER = "0615389254";
	public static final String EMPTY = "";

	private ByteArrayOutputStream out;

    @Before
    public void setUp() {
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    }

    @Test
    public void list_one_contact_without_email_nor_phone_number() throws InvalidContactNameException, InvalidEmailException {
        ContactsManager contactsManager = new ContactsManager();
        String noEmail = null;
        String noPhoneNumber = null;
        contactsManager.addContact(NICOLE_FERRONI_NAME, noEmail, noPhoneNumber);

        contactsManager.printAllContacts();

        assertThat(standardOutput(), containsString(NICOLE_FERRONI_NAME));
    }

    @Test
    public void list_one_contact_without_email() throws InvalidContactNameException, InvalidEmailException {
        ContactsManager contactsManager = new ContactsManager();
        String noEmail = null;
        contactsManager.addContact(NICOLE_FERRONI_NAME, noEmail, NICOLE_FERRONI_PHONE_NUMBER);

        contactsManager.printAllContacts();

        String expectedOutput = NICOLE_FERRONI_NAME + FIELD_SEPARATOR + NICOLE_FERRONI_PHONE_NUMBER;
        assertThat(standardOutput(), containsString(expectedOutput));
    }

    @Test
    public void list_one_contact_without_phone_number() throws InvalidContactNameException, InvalidEmailException {
        ContactsManager contactsManager = new ContactsManager();
        String noPhoneNumber = null;
        contactsManager.addContact(NICOLE_FERRONI_NAME, NICOLE_FERRONI_EMAIL, noPhoneNumber);

        contactsManager.printAllContacts();

        String expectedOutput = NICOLE_FERRONI_NAME + FIELD_SEPARATOR + NICOLE_FERRONI_EMAIL;
        assertThat(standardOutput(), containsString(expectedOutput));
    }

    @Test
    public void list_one_added_contact() throws InvalidContactNameException, InvalidEmailException {
        ContactsManager contactsManager = new ContactsManager();
        contactsManager.addContact(NICOLE_FERRONI_NAME, NICOLE_FERRONI_EMAIL, NICOLE_FERRONI_PHONE_NUMBER);

        contactsManager.printAllContacts();

        String expectedOutput = NICOLE_FERRONI_NAME + FIELD_SEPARATOR + NICOLE_FERRONI_EMAIL + FIELD_SEPARATOR + NICOLE_FERRONI_PHONE_NUMBER;
        assertThat(standardOutput(), containsString(expectedOutput));
    }

    @Test
    public void list_two_added_contacts() throws InvalidContactNameException, InvalidEmailException {
        ContactsManager contactsManager = new ContactsManager();
        contactsManager.addContact(NICOLE_FERRONI_NAME, NICOLE_FERRONI_EMAIL, NICOLE_FERRONI_PHONE_NUMBER);
        contactsManager.addContact(GUILLAUME_MEURICE_NAME, GUILLAUME_MEURICE_EMAIL, GUILLAUME_MEURICE_PHONE_NUMBER);

        contactsManager.printAllContacts();

        String standardOutput = standardOutput();
        String firstContactInfo = NICOLE_FERRONI_NAME + FIELD_SEPARATOR + NICOLE_FERRONI_EMAIL + FIELD_SEPARATOR + NICOLE_FERRONI_PHONE_NUMBER;
        String secondContactInfo = GUILLAUME_MEURICE_NAME + FIELD_SEPARATOR + GUILLAUME_MEURICE_EMAIL + FIELD_SEPARATOR + GUILLAUME_MEURICE_PHONE_NUMBER;
        assertThat(standardOutput, containsString(firstContactInfo));
        assertThat(standardOutput, containsString(secondContactInfo));
    }

    @Test
    public void retrieve_a_contact_from_its_name() throws InvalidContactNameException, InvalidEmailException {
        ContactsManager contactsManager = new ContactsManager();
        contactsManager.addContact(NICOLE_FERRONI_NAME, NICOLE_FERRONI_EMAIL, NICOLE_FERRONI_PHONE_NUMBER);
        contactsManager.addContact(GUILLAUME_MEURICE_NAME, GUILLAUME_MEURICE_EMAIL, GUILLAUME_MEURICE_PHONE_NUMBER);

        contactsManager.searchContactByName("meuri");

        String expectedOutput = GUILLAUME_MEURICE_NAME + FIELD_SEPARATOR + GUILLAUME_MEURICE_EMAIL + FIELD_SEPARATOR + GUILLAUME_MEURICE_PHONE_NUMBER;
        assertThat(standardOutput(), is(expectedOutput + System.lineSeparator()));
    }

	@Test
	public void update_a_contact_with_a_new_name() throws InvalidContactNameException, InvalidEmailException {
		ContactsManager contactsManager = new ContactsManager();
		contactsManager.addContact(NICOLE_FERRONI_NAME, NICOLE_FERRONI_EMAIL, NICOLE_FERRONI_PHONE_NUMBER);

		contactsManager.updateContact(NICOLE_FERRONI_NAME, GUILLAUME_MEURICE_NAME, NICOLE_FERRONI_EMAIL, NICOLE_FERRONI_PHONE_NUMBER);

		contactsManager.printAllContacts();

		String expectedOutput = GUILLAUME_MEURICE_NAME + FIELD_SEPARATOR + NICOLE_FERRONI_EMAIL + FIELD_SEPARATOR + NICOLE_FERRONI_PHONE_NUMBER;
		assertThat(standardOutput(), containsString(expectedOutput));
	}

	@Test
	public void update_a_contact_with_a_new_email() throws InvalidContactNameException, InvalidEmailException {
		ContactsManager contactsManager = new ContactsManager();
		contactsManager.addContact(NICOLE_FERRONI_NAME, NICOLE_FERRONI_EMAIL, NICOLE_FERRONI_PHONE_NUMBER);

		contactsManager.updateContact(NICOLE_FERRONI_NAME, NICOLE_FERRONI_NAME, GUILLAUME_MEURICE_EMAIL, NICOLE_FERRONI_PHONE_NUMBER);

		contactsManager.printAllContacts();

		String expectedOutput = NICOLE_FERRONI_NAME + FIELD_SEPARATOR + GUILLAUME_MEURICE_EMAIL + FIELD_SEPARATOR + NICOLE_FERRONI_PHONE_NUMBER;
		assertThat(standardOutput(), containsString(expectedOutput));
	}

	@Test
	public void update_a_contact_with_a_new_phone_number() throws InvalidContactNameException, InvalidEmailException {
		ContactsManager contactsManager = new ContactsManager();
		contactsManager.addContact(NICOLE_FERRONI_NAME, NICOLE_FERRONI_EMAIL, NICOLE_FERRONI_PHONE_NUMBER);

		contactsManager.updateContact(NICOLE_FERRONI_NAME, NICOLE_FERRONI_NAME, NICOLE_FERRONI_EMAIL, GUILLAUME_MEURICE_PHONE_NUMBER);

		contactsManager.printAllContacts();

		String expectedOutput = NICOLE_FERRONI_NAME + FIELD_SEPARATOR + NICOLE_FERRONI_EMAIL + FIELD_SEPARATOR + GUILLAUME_MEURICE_PHONE_NUMBER;
		assertThat(standardOutput(), containsString(expectedOutput));
	}

	@Test
	public void update_a_contact_without_a_new_name() throws InvalidContactNameException, InvalidEmailException {
		ContactsManager contactsManager = new ContactsManager();
		contactsManager.addContact(NICOLE_FERRONI_NAME, NICOLE_FERRONI_EMAIL, NICOLE_FERRONI_PHONE_NUMBER);

		contactsManager.updateContact(NICOLE_FERRONI_NAME, EMPTY, GUILLAUME_MEURICE_EMAIL, GUILLAUME_MEURICE_PHONE_NUMBER);

		contactsManager.printAllContacts();

		String expectedOutput = NICOLE_FERRONI_NAME + FIELD_SEPARATOR + GUILLAUME_MEURICE_EMAIL + FIELD_SEPARATOR + GUILLAUME_MEURICE_PHONE_NUMBER;
		assertThat(standardOutput(), containsString(expectedOutput));
	}

	@Test
	public void update_a_contact_without_a_new_email() throws InvalidContactNameException, InvalidEmailException {
		ContactsManager contactsManager = new ContactsManager();
		contactsManager.addContact(NICOLE_FERRONI_NAME, NICOLE_FERRONI_EMAIL, NICOLE_FERRONI_PHONE_NUMBER);

		contactsManager.updateContact(NICOLE_FERRONI_NAME, GUILLAUME_MEURICE_NAME, EMPTY, GUILLAUME_MEURICE_PHONE_NUMBER);

		contactsManager.printAllContacts();

		String expectedOutput = GUILLAUME_MEURICE_NAME + FIELD_SEPARATOR + NICOLE_FERRONI_EMAIL + FIELD_SEPARATOR + GUILLAUME_MEURICE_PHONE_NUMBER;
		assertThat(standardOutput(), containsString(expectedOutput));
	}

	@Test
	public void update_a_contact_without_a_new_phone_number() throws InvalidContactNameException, InvalidEmailException {
		ContactsManager contactsManager = new ContactsManager();
		contactsManager.addContact(NICOLE_FERRONI_NAME, NICOLE_FERRONI_EMAIL, NICOLE_FERRONI_PHONE_NUMBER);

		contactsManager.updateContact(NICOLE_FERRONI_NAME, GUILLAUME_MEURICE_NAME, GUILLAUME_MEURICE_EMAIL, EMPTY);

		contactsManager.printAllContacts();

		String expectedOutput = GUILLAUME_MEURICE_NAME + FIELD_SEPARATOR + GUILLAUME_MEURICE_EMAIL + FIELD_SEPARATOR + NICOLE_FERRONI_PHONE_NUMBER;
		assertThat(standardOutput(), containsString(expectedOutput));
	}

	@Test
	public void list_one_updated_contact() throws InvalidContactNameException, InvalidEmailException {
		ContactsManager contactsManager = new ContactsManager();
		contactsManager.addContact(NICOLE_FERRONI_NAME, NICOLE_FERRONI_EMAIL, NICOLE_FERRONI_PHONE_NUMBER);

		contactsManager.updateContact(NICOLE_FERRONI_NAME, GUILLAUME_MEURICE_NAME, GUILLAUME_MEURICE_EMAIL, GUILLAUME_MEURICE_PHONE_NUMBER);

		contactsManager.printAllContacts();

		String expectedOutput = GUILLAUME_MEURICE_NAME + FIELD_SEPARATOR + GUILLAUME_MEURICE_EMAIL + FIELD_SEPARATOR + GUILLAUME_MEURICE_PHONE_NUMBER;
		assertThat(standardOutput(), containsString(expectedOutput));
	}

	@Test
	public void update_a_contact_with_no_change() throws InvalidContactNameException, InvalidEmailException {
		ContactsManager contactsManager = new ContactsManager();
		contactsManager.addContact(NICOLE_FERRONI_NAME, NICOLE_FERRONI_EMAIL, NICOLE_FERRONI_PHONE_NUMBER);

		contactsManager.updateContact(NICOLE_FERRONI_NAME, EMPTY, EMPTY, EMPTY);

		contactsManager.printAllContacts();

		String expectedOutput = NICOLE_FERRONI_NAME + FIELD_SEPARATOR + NICOLE_FERRONI_EMAIL + FIELD_SEPARATOR + NICOLE_FERRONI_PHONE_NUMBER;
		assertThat(standardOutput(), containsString(expectedOutput));
	}

	@Test
	public void delete_an_unexisting_contact_from_its_name() throws InvalidContactNameException, InvalidEmailException {
		ContactsManager contactsManager = new ContactsManager();
		contactsManager.addContact(NICOLE_FERRONI_NAME, NICOLE_FERRONI_EMAIL, NICOLE_FERRONI_PHONE_NUMBER);

		contactsManager.deleteContact(GUILLAUME_MEURICE_NAME);

		contactsManager.printAllContacts();

		String expectedOutput = NICOLE_FERRONI_NAME + FIELD_SEPARATOR + NICOLE_FERRONI_EMAIL + FIELD_SEPARATOR + NICOLE_FERRONI_PHONE_NUMBER;
		assertThat(standardOutput(), containsString(expectedOutput));
	}

	@Test
	public void delete_a_contact_from_its_name() throws InvalidContactNameException, InvalidEmailException {
		ContactsManager contactsManager = new ContactsManager();
		contactsManager.addContact(NICOLE_FERRONI_NAME, NICOLE_FERRONI_EMAIL, NICOLE_FERRONI_PHONE_NUMBER);
		contactsManager.addContact(GUILLAUME_MEURICE_NAME, GUILLAUME_MEURICE_EMAIL, GUILLAUME_MEURICE_PHONE_NUMBER);

		contactsManager.deleteContact(NICOLE_FERRONI_NAME);

		contactsManager.printAllContacts();

		String expectedOutput = GUILLAUME_MEURICE_NAME + FIELD_SEPARATOR + GUILLAUME_MEURICE_EMAIL + FIELD_SEPARATOR + GUILLAUME_MEURICE_PHONE_NUMBER;
		assertThat(standardOutput(), containsString(expectedOutput));
	}

    private String standardOutput() {
        return out.toString();
    }

}
