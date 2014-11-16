package com.example.privatelecture;
/**
 * 
 * @author Hani Fakhouri
 *
 */

public class Person {
	private long id;
	private String firstName, lastName, country;
	public Person(long id, String firstName, String lastName, String country) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.country = country;
	}
	public long getID() { return id; }
	public String getFirstName() { return firstName; }
	public String getLastName() { return lastName; }
	public String getCountryName() { return country; }
	@Override
	public String toString() { return firstName + " " + lastName + " " + country; }
}
