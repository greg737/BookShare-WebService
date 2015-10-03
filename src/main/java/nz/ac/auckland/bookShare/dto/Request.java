package nz.ac.auckland.bookShare.dto;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import nz.ac.auckland.bookShare.domain.Location;

/**
 * DTO class to represent a Request.
 * 
 * @author Greggory Tan
 *
 */
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
	
	@XmlElement(name="message")
	private String _msg;
	
	public Request(){
		this(null, null, null);
	}
	
	/**
	 * Constructs a DTO Request instance. This method is intended to be called
	 * by Web service clients when creating new Request and has a owner field to be added. 
	 */
	public Request(User user, User owner, Book book){
		this(0, user, owner, book);
	}
	
	/**
	 * Constructs a DTO Request instance. This method is intended to be called
	 * by Web service clients when creating new Request sent by a user. The Web Service
	 * will fetch the owner of the book and add it to the request.
	 */
	public Request(User user, Book book){
		this(0, user, null, book);
	}
	
	/**
	 * Constructs a DTO Request instance. This method should NOT be called by 
	 * Web Service clients. It is intended to be used by the Web Service 
	 * implementation when creating a DTO Request from a domain-model Request 
	 * object.
	 */
	public Request(long id, Set<User> users, User owner, Book book){
		_requestor = users;
		_book = book;
		_id = id;
		_bookOwner = owner;
	}
	
	/**
	 * Constructs a DTO Parolee instance. This method should NOT be called by 
	 * Web Service clients. It is intended to be used by the Web Service 
	 * implementation when creating a DTO Parolee from a domain-model Parolee 
	 * object.
	 */
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
	
	public void setMessage(String msg){
		_msg = msg;
	}
	
	public String getMessage(){
		return _msg;
	}
}
