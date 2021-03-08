package com.proquest.interview.phonebook;

import com.proquest.interview.util.DatabaseUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class PhoneBookImplTest {

	public PhoneBook phoneBook = null;

	@Test
	public void shouldAddFindPerson()	 {
		phoneBook = new PhoneBookImpl();
		DatabaseUtil.initDB();
		Person person = new Person("Saravan Prathi","07000000000","London, United Kingdom");
		phoneBook.addPerson(person);
		Person foundPerson = phoneBook.findPerson("Saravan", "Prathi");
		assertEquals("Saravan Prathi", foundPerson.getName());
		assertEquals("07000000000", foundPerson.getPhoneNumber());
		assertEquals("London, United Kingdom", foundPerson.getAddress());
	}

}
