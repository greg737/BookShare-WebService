package nz.ac.auckland.bookShare.dto;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import javax.xml.bind.annotation.XmlAccessType;

/**
 * Bean class to represent a Parolee.
 * 
 * For this first Web service, a Parolee is simply represented by a unique id, a
 * name, gender and date of birth.
 * 
 * @author Ian Warren
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
	
	public User(String userName, String firstName, String lastName, String city) {
		super(firstName, lastName);
		_userName = userName;
		_city = city;
	}
	
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
