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

	private static ManagerDAO instance;
	private String outputFileName;

	private ManagerDAO(String fileName) {
		this.outputFileName = fileName;
	}

	public static synchronized ManagerDAO getInstance(String fileName) {
		if (instance == null)
			instance = new ManagerDAO(fileName);
		return instance;
	}

	public ContactsManager readData() {

		if (!new File(outputFileName).exists()) {
			try {
				System.out.printf("\n>>> Creating file %s ...\n", outputFileName);

				if(new File(outputFileName).createNewFile())
					System.out.println(">>> Success !\n");
				else
					System.out.println(">>> Fail !\n");

			} catch (IOException e) {
				System.out.println(">>> Error when trying to create file : " + outputFileName);
				System.out.printf(">>> Fail ! (%s)\n", e.getMessage());
			}
			return new ContactsManager();
		} else {
			ContactsManager manager = null;
			try {
				System.out.printf("\n>>> Reading data from %s ...\n", outputFileName);
				manager = new ContactsManager();
				for (String[] aContact : new CSVReader(new FileReader(outputFileName)).readAll()) {
					try {
						manager.addContact(aContact[0].trim(), aContact[1].trim(), aContact[2].trim());
					} catch(ArrayIndexOutOfBoundsException e) { // array overflow <-> no phone number
						manager.addContact(aContact[0].trim(), aContact[1].trim(), null);
					}
				}

				System.out.println(">>> Success !\n");

			} catch (IOException e) {
				System.out.println(">>> Error when trying to load file : " + outputFileName);
				System.out.printf(">>> Fail ! (%s)\n", e.getMessage());
			} catch (InvalidContactNameException | InvalidEmailException e) {
				System.out.println(">>> Error when trying to read a contact");
				System.out.printf(">>> Fail ! (%s)\n", e.getMessage());
			}
			return manager;
		}

	}

	public void writeData(ContactsManager manager) {

		System.out.printf("\n>>> Writing data to %s ...\n", outputFileName);

		try {
			CSVWriter writer = new CSVWriter(new FileWriter(outputFileName));
			writer.writeAll(manager.getAllContacts(), false);
			writer.close();

			System.out.println(">>> Success !\n");

		} catch (IOException e) {
			System.out.println("Error when trying to load file : " + outputFileName);
			System.out.printf(">>> Fail ! (%s)\n", e.getMessage());
		}

	}

}
