package nz.ac.auckland.bookShare.dto;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof User))
            return false;
        if (obj == this)
            return true;

        User rhs = (User) obj;
        return new EqualsBuilder().
            append(this.getId(), rhs.getId()).
            append(this.getFirstName(), rhs.getFirstName()).
            append(this.getLastName(), rhs.getLastName()).
            append(_userName, rhs._userName).
            append(_city, rhs._city).
            isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31). 
				append(this.getId()).
	            append(this.getFirstName()).
	            append(this.getLastName()).
	            append(_userName).
	            append(_city).
	            toHashCode();
	} 	 
}
