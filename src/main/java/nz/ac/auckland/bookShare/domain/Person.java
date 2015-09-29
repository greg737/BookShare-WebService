package nz.ac.auckland.bookShare.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.builder.EqualsBuilder;

import nz.ac.auckland.bookShare.dto.Author;
import nz.ac.auckland.bookShare.dto.Location;

@MappedSuperclass
public abstract class Person {
	@Id
	@GeneratedValue(generator="ID_GENERATOR")
	@Column(name="PERSON_ID")
	private long _id;

	@Column(name="FIRSTNAME", nullable =false, length=30)
	private String _firstName;
	
	@Column(name="LASTNAME", nullable = false, length=30)
	private String _lastName;
	
	@Column(name="GENDER")
	private Gender _gender;

	public Person(){
		this(null, null);
	}
	
	public Person(Long id, String firstName, String lastName){
		this(firstName, lastName);
		_id = id;
	}
	
	public Person(String firstName, String lastName) {
		_firstName = firstName;
		_lastName = lastName;
	}

	public Gender getGender() {
		return _gender;
	}

	public void setGender(Gender gender) {
		_gender = gender;
	}

	public Long getId() {
		return _id;
	}

	public String getFirstName() {
		return _firstName;
	}

	public String getLastName() {
		return _lastName;
	}
}
