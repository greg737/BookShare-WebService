package nz.ac.auckland.bookShare.dto;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import javax.xml.bind.annotation.XmlAccessType;

/**
 * DTO class to represent a User.
 * 
 * @author Greggory Tan
 *
 */
@XmlRootElement
@XmlType(name="user")
@XmlAccessorType(XmlAccessType.FIELD)
public class User extends Person {
	@XmlElement(name="username")
	protected String _userName;

	@XmlElement(name = "city")
	private String _city;

	public User(){
		this(null, null, null, null);
	}
	
	/**
	 * Constructs a DTO User instance. This method is intended to be called
	 * by Web service clients when creating new User. 
	 */
	public User(String userName, String firstName, String lastName, String city) {
		super(firstName, lastName);
		_userName = userName;
		_city = city;
	}
	
	/**
	 * Constructs a DTO User instance. This method should NOT be called by 
	 * Web Service clients. It is intended to be used by the Web Service 
	 * implementation when creating a DTO User from a domain-model User 
	 * object.
	 */
	public User(Long id, String userName, String firstName, String lastName, String city) {
		super(id, firstName, lastName);
		_userName = userName;
		_city = city;
	}

	public String getCity() {
		return _city;
	}

	public void setCity(String city) {
		_city = city;
	}

	public String getUserName() {
		return _userName;
	}
}
