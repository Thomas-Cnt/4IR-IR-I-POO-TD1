package com.thomascantie.insa;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.*;
import java.util.*;

public class Runner {

	private static final String FILENAME = "manager-data.csv";
	private static Scanner sc;
	private static ContactsManager manager;

	public static void main(String[] args) {

		sc = new Scanner(System.in);
		String command;

		printTitle();
		readData();

		do {
			printMenu();
			System.out.print("--> ");
			command = sc.nextLine().toLowerCase().trim();
			executeCommand(command);
		} while (!command.toLowerCase().trim().equals("exit"));

		writeData();

		sc.close();

		printExit();

	}

	private static void executeCommand(String command) {
		switch (command) {
			case "add":
				processAdd();
				break;
			case "list":
				processList();
				break;
			case "find":
				processFind();
				break;
			case "update":
				processUpdate();
			case "exit":
				break;
			case "delete":
				processDelete();
				break;
			default:
				System.out.printf(">>> Error ! Command not found. (\"%s\")\n", command);
		}
	}

	private static void processDelete() {
		System.out.println("----------------------");
		System.out.println("-- Delete a contact --");
		System.out.println("----------------------\n");

		if (manager.getAllContacts().isEmpty()) {
			System.out.println("There is no contact who can be deleted.\n");
		} else {

			System.out.print("name : ");
			String name = sc.nextLine();

			if (manager.getAllContacts().isEmpty()) {
				System.out.println("There is no contacts.\n");
			} else if (!manager.hasContactWithName(name)) {
				System.out.printf("There is no contact with a name like \"%s\"\n", name);
			} else {
				manager.deleteContact(name);
			}
		}

		System.out.println(">>> Finish !\n");

	}

	private static void processUpdate() {
		System.out.println("----------------------");
		System.out.println("-- Update a contact --");
		System.out.println("----------------------\n");

		if (manager.getAllContacts().isEmpty()) {
			System.out.println("There is no contact who can be updated.\n");
		} else {
			try {
				System.out.print("name of the contact to update : ");
				String name = sc.nextLine();

				if (manager.getAllContacts().isEmpty()) {
					System.out.println("There is no contcat.\n");
				} else if (!manager.hasContactWithName(name)) {
					System.out.printf("There is no contact with a name like \"%s\"\n", name);
				} else {
					System.out.print("updated name (empty if no change) : ");
					String newName = sc.nextLine();
					System.out.print("updated email (empty if no change) : ");
					String newEmail = sc.nextLine();
					System.out.print("updated phone number (empty if no change) : ");
					String newPhone = sc.nextLine();
					manager.updateContact(name, newName, newEmail, newPhone);
					System.out.println(">>> Contact updated !");
				}

			} catch (InvalidContactNameException | InvalidEmailException e) {
				System.out.println(">>> Error when trying to update this contact");
				System.out.printf(">>> Fail ! (%s)\n", e.getMessage());
			}

		}

		System.out.println(">>> Finish !\n");

	}

	private static void processFind() {
		System.out.println("--------------------");
		System.out.println("-- Find a contact --");
		System.out.println("--------------------\n");
		System.out.print("name : ");
		String name = sc.nextLine();
		if (!manager.hasContactWithName(name)) {
			System.out.printf("There is no contact with a name like \"%s\"\n", name);
		} else {
			manager.searchContactByName(name);
		}
		System.out.println(">>> Finish !\n");
	}

	private static void processList() {
		System.out.println("-----------------------");
		System.out.println("-- List all contacts --");
		System.out.println("-----------------------\n");
		manager.printAllContacts();
		System.out.println(">>> Finish !\n");
	}

	private static void processAdd() {
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

	}

	private static void printTitle() {
		System.out.println();
		System.out.println("****************************");
		System.out.println("***** Contacts manager *****");
		System.out.println("****************************");
		System.out.println();
	}

	private static void printExit() {
		System.out.println();
		System.out.println("***********");
		System.out.println("*** BYE ***");
		System.out.println("***********");
		System.out.println();
	}

	private static void printMenu() {
		System.out.println();
		System.out.println("Commands available :");
		System.out.println("\t - add");
		System.out.println("\t\t Add a contact specified by his name, email and phone number. Notice that the email must be a valid one.");
		System.out.println("\t - list");
		System.out.println("\t\t List all contact who are registered.");
		System.out.println("\t - find");
		System.out.println("\t\t Retrieve a contact from its name. Retrieve the first one found.");
		System.out.println("\t - update");
		System.out.println("\t\t Update a contact from its name. Allow to change both name, email and phone number. Update the first one found.");
		System.out.println("\t - delete");
		System.out.println("\t\t Delete a contact from its name. Delete the first one found.");
		System.out.println();
	}

	private static void readData() {

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
