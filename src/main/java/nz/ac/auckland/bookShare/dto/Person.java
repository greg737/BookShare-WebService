package nz.ac.auckland.bookShare.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import nz.ac.auckland.bookShare.domain.Gender;

@XmlTransient
public abstract class Person {
	@XmlElement(name="id")
	private long _id;

	@XmlElement(name="first-name")
	private String _firstName;
	
	@XmlElement(name="last-name")
	private String _lastName;
	
	@XmlElement(name="gender")
	private Gender _gender;

	public Person() {
		this(0, null, null);
	}
	
	public Person(String firstName, String lastName) {
		this(0, firstName, lastName);
	}
	
	/**
	 * Constructs a DTO Parolee instance. This method should NOT be called by 
	 * Web Service clients. It is intended to be used by the Web Service 
	 * implementation when creating a DTO Parolee from a domain-model Parolee 
	 * object.
	 */
	public Person(long id, String firstName, String lastName){
		_id = id;
		_firstName = firstName;
		_lastName = lastName;
	}

	public Gender getGender() {
		return _gender;
	}

	public void setGender(Gender gender) {
		_gender = gender;
	}

	public long getId() {
		return _id;
	}

	public String getFirstName() {
		return _firstName;
	}

	public String getLastName() {
		return _lastName;
	}
}
