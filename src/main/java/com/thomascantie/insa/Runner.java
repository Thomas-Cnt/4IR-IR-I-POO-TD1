package com.thomascantie.insa;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.*;
import java.util.*;

import com.thomascantie.insa.ManagerDAO;

public class Runner {

	private Scanner sc;
	private ContactsManager manager;
	private ManagerDAO managerDAO;

	public Runner(ManagerDAO managerDAO) {
		this.managerDAO = managerDAO;
	}

	public void run() {

		sc = new Scanner(System.in);
		String command;

		printTitle();
		manager = managerDAO.readData();

		System.out.println("Command : add | list | find | update | delete | help | exit");

		do {
			System.out.println();
			System.out.print("--> ");
			command = sc.nextLine().toLowerCase().trim();
			executeCommand(command);
		} while (!command.toLowerCase().trim().equals("exit"));

		managerDAO.writeData(manager);

		sc.close();

		printExit();

	}

	private void executeCommand(String command) {
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
				break;
			case "delete":
				processDelete();
				break;
			case "help":
				printUsage();
				break;
			case "exit":
				break;
			default:
				System.out.printf(">>> Error ! Command not found. (\"%s\")\n", command);
				System.out.println("Command : add | list | find | update | delete | help | exit");
		}
	}

	private void processDelete() {
		System.out.println("----------------------");
		System.out.println("-- Delete a contact --");
		System.out.println("----------------------\n");

		if (!manager.hasContacts()) {
			System.out.println("There is no contact who can be deleted.\n");
		} else {

			System.out.print("name : ");
			String name = sc.nextLine();

			if (!manager.hasContactWithName(name)) {
				System.out.printf("There is no contact with a name like \"%s\"\n", name);
			} else {
				manager.deleteContact(name);
				System.out.println(">>> Contact deleted !");
			}
		}

		System.out.println(">>> Finish !\n");

	}

	private void processUpdate() {
		System.out.println("----------------------");
		System.out.println("-- Update a contact --");
		System.out.println("----------------------\n");

		if (!manager.hasContacts()) {
			System.out.println("There is no contact who can be updated.\n");
		} else {
			try {
				System.out.print("name of the contact to update : ");
				String name = sc.nextLine();

				if (!manager.hasContactWithName(name)) {
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
					manager.searchContactByName(newName);
				}

			} catch (InvalidContactNameException | InvalidEmailException e) {
				System.out.println(">>> Error when trying to update this contact");
				System.out.printf(">>> Fail ! (%s)\n", e.getMessage());
			}

		}

		System.out.println(">>> Finish !\n");

	}

	private void processFind() {
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

	private void processList() {
		System.out.println("-----------------------");
		System.out.println("-- List all contacts --");
		System.out.println("-----------------------\n");
		manager.printAllContacts();
		System.out.println(">>> Finish !\n");
	}

	private void processAdd() {
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
			manager.searchContactByName(name);
		} catch (InvalidContactNameException | InvalidEmailException e) {
			System.out.println(">>> Error when trying to add this contact");
			System.out.printf(">>> Fail ! (%s)\n", e.getMessage());
		} finally {
			System.out.println(">>> Finish !\n");
		}

	}

	private void printTitle() {
		System.out.println();
		System.out.println("****************************");
		System.out.println("***** Contacts manager *****");
		System.out.println("****************************");
		System.out.println();
	}

	private void printExit() {
		System.out.println();
		System.out.println("***********");
		System.out.println("*** BYE ***");
		System.out.println("***********");
		System.out.println();
	}

	private void printUsage() {
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
		System.out.println("\t - help");
		System.out.println("\t\t Print available commands.");
		System.out.println("\t - exit");
		System.out.println("\t\t Stop the ContactsManager Runner.");
		System.out.println();
	}

}
