package nz.ac.auckland.bookShare.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Request {
	@Id
	@GeneratedValue(generator="ID_GENERATOR")
	@Column(name="REQUEST_ID")
	private long _id;
	
	@ManyToMany
	private Set<User> _requestor;
	
	@ManyToOne
	private User _bookOwner;
	
	@ManyToOne(optional = false)
	private Book _book;
	
	@Embedded
	private Location _location;
	
	private String _msg;
	
	public Request(){
		this(null, null, null);
	}
	
	public Request(long id, User user, User owner, Book book){
		this(user, owner, book);
		_id = id;
	}
	
	public Request(long id, Set<User> users, User owner, Book book){
		_requestor = users;
		_book = book;
		_id = id;
		_bookOwner = owner;
	}
	
	public Request(User user, User owner, Book book){
		_requestor = new HashSet<User>();
		_requestor.add(user);
		_book = book;
		_bookOwner = owner;
	}
	
	public long getId(){
		return _id;
	}
	
	public User getOwner(){
		return _bookOwner;
	}
	
	public Set<User> getRequestors() {
		return _requestor;
	}
	
	public void addRequestor(User user){
		_requestor.add(user);
	}
	
	public Book getBook() {
		return _book;
	}
	
	public void setMessage(String msg){
		_msg = msg;
	}
	
	public String getMessage(){
		return _msg;
	}
	
	public void setLocation(Location location){
		_location = location;
	}
	
	public Location getLocation(){
		return _location;
	}
}
