package com.proquest.interview.phonebook;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.proquest.interview.util.DatabaseUtil;

public class PhoneBookImpl implements PhoneBook {
	private static final String SQL_ADD_PERSON = "INSERT INTO PHONEBOOK (NAME, PHONENUMBER, ADDRESS) VALUES(?,?,?)";
	private static final String SQL_FETCH_PERSON = "SELECT * FROM PHONEBOOK WHERE NAME = ?";
	private static final String SQL_FETCH_ALL = "SELECT * FROM PHONEBOOK";

	private Connection connection = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	@Override
	public void addPerson(Person newPerson) {
		try {
			connection = DatabaseUtil.getConnection();
			preparedStatement = connection.prepareStatement(SQL_ADD_PERSON);
			preparedStatement.setString(1, newPerson.getName());
			preparedStatement.setString(2, newPerson.getPhoneNumber());
			preparedStatement.setString(3, newPerson.getAddress());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DatabaseUtil.clear(null, preparedStatement, connection);
		}
	}
	
	@Override
	public Person findPerson(String firstName,String lastName) {
		Person person = null;
		try {
			connection = DatabaseUtil.getConnection();
			preparedStatement = connection.prepareStatement(SQL_FETCH_PERSON);
			preparedStatement.setString(1, firstName + " " + lastName);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				person = new Person();
				person.setName(resultSet.getString(1));
				person.setPhoneNumber(resultSet.getString(2));
				person.setAddress(resultSet.getString(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.clear(resultSet, preparedStatement, connection);
		}
		return person;
	}

	public List fetchPhoneBook(){
		List<Person> phoneBook = new ArrayList();
		try {
			connection = DatabaseUtil.getConnection();
			preparedStatement = connection.prepareStatement(SQL_FETCH_ALL);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Person p = new Person(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3) );
				phoneBook.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DatabaseUtil.clear(resultSet, preparedStatement, connection);
		}
		return phoneBook;
	}
	
	public static void main(String[] args) {
		DatabaseUtil.initDB();  //You should not remove this line, it creates the in-memory database

		/* TODO: create person objects and put them in the PhoneBook and database
		 * John Smith, (248) 123-4567, 1234 Sand Hill Dr, Royal Oak, MI
		 * Cynthia Smith, (824) 128-8758, 875 Main St, Ann Arbor, MI
		 */

		PhoneBook phoneBook = new PhoneBookImpl();
		Person person = new Person("John Smith", "(248) 123-4567", "1234 Sand Hill Dr, Royal Oak, MI");
		phoneBook.addPerson(person);
		person = new Person("Cynthia Smith", "(824) 128-8758", "875 Main St, Ann Arbor, MI");
		phoneBook.addPerson(person);

		// TODO: print the phone book out to System.out
		List<Person> people = ((PhoneBookImpl)phoneBook).fetchPhoneBook();
		if (!people.isEmpty()) {
			System.out.println("--------- Phone Book ---------");
			for (int i = 0; i < people.size(); i++) {
				Person p = (Person) people.get(i);
				System.out.println("Name: " + p.getName());
				System.out.println("Phone Number: " + p.getPhoneNumber());
				System.out.println("Address : " + p.getAddress());
			}
		}else{
			System.out.println("Phone Book is Empty!");
		}
		System.out.println();

		// TODO: find Cynthia Smith and print out just her entry

		person = phoneBook.findPerson("Cynthia", "Smith");
		System.out.println("Details of Person:");
		System.out.println("Name: " +  person.getName());
		System.out.println("Phone Number: " +  person.getPhoneNumber());
		System.out.println("Address : " +  person.getAddress());
		System.out.println();

		// TODO: insert the new person objects into the database
		/*already added the new person objects to the database in the above steps*/
		/*Placeholder till confirmed with the interviewer*/
	}
}
