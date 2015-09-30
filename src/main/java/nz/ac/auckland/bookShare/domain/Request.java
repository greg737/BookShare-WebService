package nz.ac.auckland.bookShare.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Request {
	@Id
	@GeneratedValue(generator="ID_GENERATOR")
	@Column(name="REQUEST_ID")
	private long _id;
	
	@OneToOne(optional = false)
	private User _requestor;
	
	@OneToOne(optional = false)
	private Book _book;
	
	@Embedded
	private Location _location;
	
	private String _msg;
	
	public Request(){
		this(null, null);
	}
	
	public Request(long id, User user, Book book){
		this(user, book);
		_id = id;
	}
	
	public Request(User user, Book book){
		_requestor = user;
		_book = book;
	}
	
	public long getId(){
		return _id;
	}
	
	public User getRequestor() {
		return _requestor;
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
