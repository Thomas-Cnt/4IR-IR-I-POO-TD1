package com.thomascantie.insa;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.thomascantie.insa.ContactsManager;
import com.thomascantie.insa.InvalidContactNameException;
import com.thomascantie.insa.InvalidEmailException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ManagerDAO {

	private static final String FILENAME = "manager-data.csv";

	public static void readData(ContactsManager manager) {

		if (!new File(FILENAME).exists()) {
			try {
				System.out.printf("\n>>> Creating file %s ...\n", FILENAME);

				if(new File(FILENAME).createNewFile())
					System.out.println(">>> Success !\n");
				else
					System.out.println(">>> Fail !\n");

			} catch (IOException e) {
				System.out.println(">>> Error when trying to create file : " + FILENAME);
				System.out.printf(">>> Fail ! (%s)\n", e.getMessage());
			}
			manager = new ContactsManager();
		} else {
			try {
				System.out.printf("\n>>> Reading data from %s ...\n", FILENAME);

				manager = new ContactsManager();
				for (String[] aContact : new CSVReader(new FileReader(FILENAME)).readAll()) {
					try {
						manager.addContact(aContact[0], aContact[1], aContact[2]);
					} catch(ArrayIndexOutOfBoundsException e) { // array overflow <-> no phone number
						manager.addContact(aContact[0], aContact[1], null);
					}
				}

				System.out.println(">>> Success !\n");

			} catch (IOException e) {
				System.out.println(">>> Error when trying to load file : " + FILENAME);
				System.out.printf(">>> Fail ! (%s)\n", e.getMessage());
			} catch (InvalidContactNameException | InvalidEmailException e) {
				System.out.println(">>> Error when trying to read a contact");
				System.out.printf(">>> Fail ! (%s)\n", e.getMessage());
			}
		}

	}

	public static void writeData(ContactsManager manager) {

		System.out.printf("\n>>> Writing data to %s ...\n", FILENAME);

		try {
			CSVWriter writer = new CSVWriter(new FileWriter(FILENAME));
			writer.writeAll(manager.getAllContacts());
			writer.close();

			System.out.println(">>> Success !\n");

		} catch (IOException e) {
			System.out.println("Error when trying to load file : " + FILENAME);
			System.out.printf(">>> Fail ! (%s)\n", e.getMessage());
		}

	}

}
