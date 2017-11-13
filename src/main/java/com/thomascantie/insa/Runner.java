package com.thomascantie.insa;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.*;
import java.util.*;

public class Runner {

	private static final String FILENAME = "test__manager-data.csv";
	private static Scanner sc;
	private static ContactsManager manager;

	public static void main(String[] args) {

		sc = new Scanner(System.in);
		String command;

		System.out.println();
		System.out.println("****************************");
		System.out.println("***** Contacts manager *****");
		System.out.println("****************************");
		System.out.println();

		readData();

		do {
			System.out.println();
			System.out.println("Commands available :");
			System.out.println("\t - add");
			System.out.println("\t\t Add a contact specified by his name, email and phone number. Notice that the email must be a valid one.");
			System.out.println("\t - list");
			System.out.println("\t\t List all contact who are registered.");
			System.out.println("\t - find");
			System.out.println("\t\t Retrieve a contact from its name.");
			System.out.println();

			System.out.print("--> ");
			command = sc.nextLine().toLowerCase().trim();

			switch (command) {
				case "add":
					System.out.println("-------------------");
					System.out.println("-- Add a contact --");
					System.out.println("-------------------\n");
					try {
						System.out.print("name : ");
						String name = sc.nextLine();
						System.out.print("email : ");
						String email = sc.nextLine();
						System.out.print("phone number : ");
						String phone = sc.nextLine();
						manager.addContact(name, email, phone);
						System.out.println(">>> Contact added !");
					} catch (InvalidContactNameException | InvalidEmailException e) {
						System.out.println(">>> Error when trying to add this contact");
						System.out.printf(">>> Fail ! (%s)\n", e.getMessage());
					} finally {
						System.out.println(">>> Finish !\n");
					}
					break;
				case "list":
					System.out.println("-----------------------");
					System.out.println("-- List all contacts --");
					System.out.println("-----------------------\n");
					manager.printAllContacts();
					System.out.println(">>> Finish !\n");
					break;
				case "find":
					System.out.println("--------------------");
					System.out.println("-- Find a contact --");
					System.out.println("--------------------\n");
					System.out.print("name : ");
					String name = sc.nextLine();
					manager.searchContactByName(name);
					System.out.println(">>> Finish !\n");
					break;
				case "exit":
					break;
				default:
					System.out.printf(">>> Error ! Command not found. (%s)\n", command);
			}
		} while (!command.toLowerCase().trim().equals("exit"));

		writeData();

		System.out.println();
		System.out.println("***********");
		System.out.println("*** BYE ***");
		System.out.println("***********");
		System.out.println();

	}

	private static void readData() {

		if (!new File(FILENAME).exists()) {
			try {
				System.out.printf("\n>>> Creating file %s ...\n", FILENAME);

				new File(FILENAME).createNewFile();

				System.out.println(">>> Success !\n");
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

	private static void writeData() {

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
