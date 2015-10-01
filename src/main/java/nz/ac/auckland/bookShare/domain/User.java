package nz.ac.auckland.bookShare.domain;

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
 * Bean class to represent a User.
 * 
 * 
 * A user extends the Person class and must have a unique username,
 * a first and last name and city the user is near to.
 * 
 * @author Greggory Tan
 *
 */
@Entity
@Table(name = "USER", uniqueConstraints = { @UniqueConstraint(columnNames = { "FIRSTNAME", "LASTNAME" }) })
public class User extends Person {
	@Column(name = "USERNAME", nullable = false, unique = true, length = 30)
	private String _userName;

	@Column(name = "CITY")
	private String _city;

	@ManyToMany
	@JoinTable(name = "OWNED_BOOKS", joinColumns = {
			@JoinColumn(name = "OWNER_ID", referencedColumnName = "PERSON_ID") }, inverseJoinColumns = {
					@JoinColumn(name = "BOOK_ID", referencedColumnName = "BOOK_ID") })
	private Collection<Book> _ownedBooks;

	// Default constructor is required by JAXB.
	public User() {
		this(null, null, null, null);
	}

	public User(long id, String userName, String firstName, String lastName, String city) {
		super(id, firstName, lastName);
		_userName = userName;
		_city = city;
		_ownedBooks = new ArrayList<Book>();
	}

	public User(String userName, String firstName, String lastName, String city) {
		super(firstName, lastName);
		_userName = userName;
		_city = city;
		_ownedBooks = new ArrayList<Book>();
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
}
