package com.thomascantie.insa;

import org.junit.Test;
import com.thomascantie.insa.ManagerDAO;
import org.mockito.InOrder;


import java.io.ByteArrayInputStream;

import static org.mockito.Mockito.*;

public class TestRunnerInteractions {

	private static final String NICOLE_FERRONI_NAME = "Nicole Ferroni";
	private static final String NICOLE_FERRONI_EMAIL = "contact@nicoleferroni.fr";
	private static final String NICOLE_FERRONI_PHONE_NUMBER = "0651387945";

	private static final String GUILLAUME_MEURICE_NAME = "Guillaume Meurice";
	private static final String GUILLAUME_MEURICE_EMAIL = "contact@guillaumemeurice.fr";
	private static final String GUILLAUME_MEURICE_PHONE_NUMBER = "0615389254";

	@Test
	public void When_add_one_contact() throws InvalidEmailException, InvalidContactNameException {
		ContactsManager manager = mock(ContactsManager.class);
		ManagerDAO dao = mock(ManagerDAO.class);

		when(dao.readData()).thenReturn(manager);

		String commands = "add\n" + NICOLE_FERRONI_NAME + "\n" + NICOLE_FERRONI_EMAIL + "\n" + NICOLE_FERRONI_PHONE_NUMBER + "\nexit\n";
		System.setIn(new ByteArrayInputStream(commands.getBytes()));

		new Runner(dao).run();

		InOrder inOrder = inOrder(manager);

		inOrder.verify(manager).addContact(NICOLE_FERRONI_NAME, NICOLE_FERRONI_EMAIL, NICOLE_FERRONI_PHONE_NUMBER);
		inOrder.verify(manager).searchContactByName(NICOLE_FERRONI_NAME);
		inOrder.verifyNoMoreInteractions();
	}

	@Test
	public void When_list_all_contacts_added() {
		ContactsManager manager = mock(ContactsManager.class);
		ManagerDAO dao = mock(ManagerDAO.class);

		when(dao.readData()).thenReturn(manager);

		String commands = "list\nexit\n";
		System.setIn(new ByteArrayInputStream(commands.getBytes()));

		new Runner(dao).run();

		InOrder inOrder = inOrder(manager);

		inOrder.verify(manager).printAllContacts();
		inOrder.verifyNoMoreInteractions();
	}

	@Test
	public void When_find_an_added_contact() {
		ContactsManager manager = mock(ContactsManager.class);
		ManagerDAO dao = mock(ManagerDAO.class);

		when(dao.readData()).thenReturn(manager);
		when(manager.hasContactWithName(anyString())).thenReturn(true);

		String commands = "find\n" + NICOLE_FERRONI_NAME + "\nexit\n";
		System.setIn(new ByteArrayInputStream(commands.getBytes()));

		new Runner(dao).run();

		InOrder inOrder = inOrder(manager);

		inOrder.verify(manager).hasContactWithName(NICOLE_FERRONI_NAME);
		inOrder.verify(manager).searchContactByName(NICOLE_FERRONI_NAME);
		inOrder.verifyNoMoreInteractions();
	}

	@Test
	public void When_find_a_not__added() {
		ContactsManager manager = mock(ContactsManager.class);
		ManagerDAO dao = mock(ManagerDAO.class);

		when(dao.readData()).thenReturn(manager);
		when(manager.hasContactWithName(anyString())).thenReturn(false);

		String commands = "find\n" + NICOLE_FERRONI_NAME + "\nexit\n";
		System.setIn(new ByteArrayInputStream(commands.getBytes()));

		new Runner(dao).run();

		InOrder inOrder = inOrder(manager);

		inOrder.verify(manager).hasContactWithName(NICOLE_FERRONI_NAME);
		inOrder.verify(manager, never()).searchContactByName(anyString());
		inOrder.verifyNoMoreInteractions();
	}

	@Test
	public void When_update_an_added_contact() throws InvalidEmailException, InvalidContactNameException {
		ContactsManager managerWithOneContact = mock(ContactsManager.class);
		ManagerDAO dao = mock(ManagerDAO.class);

		when(dao.readData()).thenReturn(managerWithOneContact);
		when(managerWithOneContact.hasContacts()).thenReturn(true);
		when(managerWithOneContact.hasContactWithName(anyString())).thenReturn(true);

		String commands = "update\n" + NICOLE_FERRONI_NAME + "\n" + GUILLAUME_MEURICE_NAME + "\n" + GUILLAUME_MEURICE_EMAIL + "\n" + GUILLAUME_MEURICE_PHONE_NUMBER + "\nexit\n";
		System.setIn(new ByteArrayInputStream(commands.getBytes()));

		new Runner(dao).run();

		InOrder inOrder = inOrder(managerWithOneContact);

		inOrder.verify(managerWithOneContact).hasContacts();
		inOrder.verify(managerWithOneContact).hasContactWithName(NICOLE_FERRONI_NAME);
		inOrder.verify(managerWithOneContact).updateContact(NICOLE_FERRONI_NAME, GUILLAUME_MEURICE_NAME, GUILLAUME_MEURICE_EMAIL, GUILLAUME_MEURICE_PHONE_NUMBER);
		inOrder.verify(managerWithOneContact).searchContactByName(GUILLAUME_MEURICE_NAME);
		inOrder.verifyNoMoreInteractions();
	}

	@Test
	public void When_update_a_not_added_contact() throws InvalidEmailException, InvalidContactNameException {
		ContactsManager managerWithOneContact = mock(ContactsManager.class);
		ManagerDAO dao = mock(ManagerDAO.class);

		when(dao.readData()).thenReturn(managerWithOneContact);
		when(managerWithOneContact.hasContacts()).thenReturn(true);
		when(managerWithOneContact.hasContactWithName(anyString())).thenReturn(false);

		String commands = "update\n" + NICOLE_FERRONI_NAME + "\nexit\n";
		System.setIn(new ByteArrayInputStream(commands.getBytes()));

		new Runner(dao).run();

		InOrder inOrder = inOrder(managerWithOneContact);

		inOrder.verify(managerWithOneContact).hasContacts();
		inOrder.verify(managerWithOneContact).hasContactWithName(NICOLE_FERRONI_NAME);
		inOrder.verify(managerWithOneContact, never()).updateContact(anyString(), anyString(), anyString(), anyString());
		inOrder.verify(managerWithOneContact, never()).searchContactByName(anyString());
		inOrder.verifyNoMoreInteractions();
	}

	@Test
	public void When_update_while_there_is_no_contact() throws InvalidEmailException, InvalidContactNameException {
		ContactsManager managerWithOneContact = mock(ContactsManager.class);
		ManagerDAO dao = mock(ManagerDAO.class);

		when(dao.readData()).thenReturn(managerWithOneContact);
		when(managerWithOneContact.hasContacts()).thenReturn(false);

		String commands = "update\nexit\n";
		System.setIn(new ByteArrayInputStream(commands.getBytes()));

		new Runner(dao).run();

		InOrder inOrder = inOrder(managerWithOneContact);

		inOrder.verify(managerWithOneContact).hasContacts();
		inOrder.verify(managerWithOneContact, never()).hasContactWithName(anyString());
		inOrder.verify(managerWithOneContact, never()).updateContact(anyString(), anyString(), anyString(), anyString());
		inOrder.verify(managerWithOneContact, never()).searchContactByName(anyString());
		inOrder.verifyNoMoreInteractions();
	}

	@Test
	public void When_delete_an_added_contact() throws InvalidEmailException, InvalidContactNameException {
		ContactsManager managerWithOneContact = mock(ContactsManager.class);
		ManagerDAO dao = mock(ManagerDAO.class);

		when(dao.readData()).thenReturn(managerWithOneContact);
		when(managerWithOneContact.hasContacts()).thenReturn(true);
		when(managerWithOneContact.hasContactWithName(anyString())).thenReturn(true);

		String commands = "delete\n" + NICOLE_FERRONI_NAME + "\nexit\n";
		System.setIn(new ByteArrayInputStream(commands.getBytes()));

		new Runner(dao).run();

		InOrder inOrder = inOrder(managerWithOneContact);

		inOrder.verify(managerWithOneContact).hasContacts();
		inOrder.verify(managerWithOneContact).hasContactWithName(NICOLE_FERRONI_NAME);
		inOrder.verify(managerWithOneContact).deleteContact(NICOLE_FERRONI_NAME);
		inOrder.verifyNoMoreInteractions();
	}

	@Test
	public void When_delete_a_not_added_contact() throws InvalidEmailException, InvalidContactNameException {
		ContactsManager managerWithOneContact = mock(ContactsManager.class);
		ManagerDAO dao = mock(ManagerDAO.class);

		when(dao.readData()).thenReturn(managerWithOneContact);
		when(managerWithOneContact.hasContacts()).thenReturn(true);
		when(managerWithOneContact.hasContactWithName(anyString())).thenReturn(false);

		String commands = "update\n" + NICOLE_FERRONI_NAME + "\nexit\n";
		System.setIn(new ByteArrayInputStream(commands.getBytes()));

		new Runner(dao).run();

		InOrder inOrder = inOrder(managerWithOneContact);

		inOrder.verify(managerWithOneContact).hasContacts();
		inOrder.verify(managerWithOneContact).hasContactWithName(NICOLE_FERRONI_NAME);
		inOrder.verify(managerWithOneContact, never()).deleteContact(anyString());
		inOrder.verifyNoMoreInteractions();
	}

	@Test
	public void When_delete_while_there_is_no_contact() throws InvalidEmailException, InvalidContactNameException {
		ContactsManager managerWithOneContact = mock(ContactsManager.class);
		ManagerDAO dao = mock(ManagerDAO.class);

		when(dao.readData()).thenReturn(managerWithOneContact);
		when(managerWithOneContact.hasContacts()).thenReturn(false);

		String commands = "update\nexit\n";
		System.setIn(new ByteArrayInputStream(commands.getBytes()));

		new Runner(dao).run();

		InOrder inOrder = inOrder(managerWithOneContact);

		inOrder.verify(managerWithOneContact).hasContacts();
		inOrder.verify(managerWithOneContact, never()).hasContactWithName(anyString());
		inOrder.verify(managerWithOneContact, never()).deleteContact(anyString());
		inOrder.verifyNoMoreInteractions();
	}

	@Test
	public void When_another_command() {
		ContactsManager manager = mock(ContactsManager.class);
		ManagerDAO dao = mock(ManagerDAO.class);

		when(dao.readData()).thenReturn(manager);
		when(manager.hasContactWithName(anyString())).thenReturn(false);

		String commands = "help\n\nexit\n";
		System.setIn(new ByteArrayInputStream(commands.getBytes()));

		new Runner(dao).run();

		InOrder inOrder = inOrder(manager);

		inOrder.verifyNoMoreInteractions();
	}

}


