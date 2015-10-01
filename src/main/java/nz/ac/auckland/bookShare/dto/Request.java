package nz.ac.auckland.bookShare.dto;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(name="request")
@XmlAccessorType(XmlAccessType.FIELD)
public class Request {
	@XmlElement(name="id")
	private long _id;
	
	@XmlElementWrapper(name = "requestors")
	@XmlElement(name="requestor")
	private Set<User> _requestor;
	
	@XmlElement(name="requestor")
	private User _bookOwner;
	
	@XmlElement(name="book")
	private Book _book;
	
	@XmlElement(name="location")
	private Location _location;
	
	public Request(){
		this(null, null, null);
	}
	
	public Request(User user, User owner, Book book){
		this(0, user, owner, book);
	}
	
	public Request(User user, Book book){
		this(0, user, null, book);
	}
	
	public Request(long id, Set<User> users, User owner, Book book){
		_requestor = users;
		_book = book;
		_id = id;
		_bookOwner = owner;
	}
	
	public Request(long id, User user, User owner, Book book){
		_id = id;
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
	
	public void setLocation(Location location){
		_location = location;
	}
	
	public Location getLocation(){
		return _location;
	}
}
