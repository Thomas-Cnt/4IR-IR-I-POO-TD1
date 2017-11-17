package com.thomascantie.insa;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

import static com.sun.org.apache.xerces.internal.util.PropertyState.is;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ManagerDAOMust {

	private static final String FILE_TMP = "tmp.csv";

	private static final String FIELD_OUTPUT_SEPARATOR = ", ";
	private static final String FIELD_CSV_SEPARATOR = ",";

	private static final String NICOLE_FERRONI_NAME = "Nicole Ferroni";
	private static final String NICOLE_FERRONI_EMAIL = "contact@nicoleferroni.fr";
	private static final String NICOLE_FERRONI_PHONE_NUMBER = "0651387945";

	private static final String GUILLAUME_MEURICE_NAME = "Guillaume Meurice";
	private static final String GUILLAUME_MEURICE_EMAIL = "contact@guillaumemeurice.fr";
	private static final String GUILLAUME_MEURICE_PHONE_NUMBER = "0615389254";

	private ByteArrayOutputStream out;

	@Before
	public void setUp() {
		out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
	}

	@After
	public void tearDown() throws Exception {
		new File(FILE_TMP).delete();
	}

	@Test
	public void Read_contacts_from_file() throws IOException {

		String contact1 = new StringJoiner(FIELD_OUTPUT_SEPARATOR)
				.add(NICOLE_FERRONI_NAME)
				.add(NICOLE_FERRONI_EMAIL)
				.add(NICOLE_FERRONI_PHONE_NUMBER).toString();

		String contact2 = new StringJoiner(FIELD_OUTPUT_SEPARATOR)
				.add(GUILLAUME_MEURICE_NAME)
				.add(GUILLAUME_MEURICE_EMAIL).toString();

		new FileOutputStream(new File(FILE_TMP)).write((contact1 + "\n" + contact2).getBytes());

		ContactsManager manager = ManagerDAO.getInstance(FILE_TMP).readData();

		manager.printAllContacts();

		String firstContactInfo = NICOLE_FERRONI_NAME + FIELD_OUTPUT_SEPARATOR + NICOLE_FERRONI_EMAIL + FIELD_OUTPUT_SEPARATOR + NICOLE_FERRONI_PHONE_NUMBER;
		String secondContactInfo = GUILLAUME_MEURICE_NAME + FIELD_OUTPUT_SEPARATOR + GUILLAUME_MEURICE_EMAIL;
		assertThat(standardOutput(), containsString(firstContactInfo));
		assertThat(standardOutput(), containsString(secondContactInfo));

		assertThat(manager.getAllContacts().size(), equalTo(2));
	}

	@Test
	public void Write_contacts_into_file() throws InvalidEmailException, InvalidContactNameException, IOException {
		ContactsManager contactsManager = new ContactsManager();
		contactsManager.addContact(NICOLE_FERRONI_NAME, NICOLE_FERRONI_EMAIL, NICOLE_FERRONI_PHONE_NUMBER);
		contactsManager.addContact(GUILLAUME_MEURICE_NAME, GUILLAUME_MEURICE_EMAIL, GUILLAUME_MEURICE_PHONE_NUMBER);

		ManagerDAO.getInstance(FILE_TMP).writeData(contactsManager);

		StringBuffer buffer = new StringBuffer();

		BufferedReader reader = new BufferedReader(new FileReader(FILE_TMP));
		String entry;
		while ((entry = reader.readLine()) != null) {
			buffer.append(entry);
		}
		reader.close();

		String firstContactInfo = NICOLE_FERRONI_NAME + FIELD_CSV_SEPARATOR + NICOLE_FERRONI_EMAIL + FIELD_CSV_SEPARATOR + NICOLE_FERRONI_PHONE_NUMBER;
		String secondContactInfo = GUILLAUME_MEURICE_NAME + FIELD_CSV_SEPARATOR + GUILLAUME_MEURICE_EMAIL + FIELD_CSV_SEPARATOR + GUILLAUME_MEURICE_PHONE_NUMBER;
		assertThat(buffer.toString(), containsString(firstContactInfo));
		assertThat(buffer.toString(), containsString(secondContactInfo));
	}

	private String standardOutput() {
		return out.toString();
	}

}
