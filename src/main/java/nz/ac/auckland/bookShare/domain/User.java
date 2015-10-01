package nz.ac.auckland.bookShare.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Bean class to represent a Parolee.
 * 
 * For this first Web service, a Parolee is simply represented by a unique id, a
 * name, gender and date of birth.
 * 
 * @author Ian Warren
 *
 */

@Entity
@Table(name = "USER", uniqueConstraints = 
{@UniqueConstraint(columnNames = { "FIRSTNAME", "LASTNAME" }) })
public class User extends Person {
	@Column(name = "USERNAME", nullable = false, unique = true, length=30)
	private String _userName;

	@Column(name="CITY")
	private String _city;

	@ManyToMany
	@JoinTable(name="OWNED_BOOKS",
	      joinColumns={@JoinColumn(name="OWNER_ID", referencedColumnName="PERSON_ID")},
	      inverseJoinColumns={@JoinColumn(name="BOOK_ID", referencedColumnName="BOOK_ID")})
	private Collection<Book> _ownedBooks;
	
	@OneToMany
	@JoinTable(name="REQUEST_LIST",
	      joinColumns={@JoinColumn(name="USER_ID", referencedColumnName="PERSON_ID")},
	      inverseJoinColumns={@JoinColumn(name="REQUEST_ID", referencedColumnName="REQUEST_ID")})
	private Collection<Request> _requests;

	// Default constructor is required by JAXB.
	public User() {
		this(null, null, null, null);
	}
	
	public User(long id, String userName, String firstName, String lastName, String city) {
		super(id, firstName, lastName);
		_userName = userName;
		_city = city;
		_ownedBooks = new ArrayList<Book>();
		_requests = new ArrayList<Request>();
	}

	public User(String userName, String firstName, String lastName, String city) {
		super(firstName, lastName);
		_userName = userName;
		_city = city;
		_ownedBooks = new ArrayList<Book>();
		_requests = new ArrayList<Request>();
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

	public Collection<Book> getOwnedBooks() {
		return _ownedBooks;
	}

	public void addNewOwned(Book newBook) {
		_ownedBooks.add(newBook);
	}
	
	public Collection<Request> getRequests() {
		return _requests;
	}

	public void addRequest(Request request) {
		_requests.add(request);
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
