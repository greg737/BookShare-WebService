package nz.ac.auckland.bookShare.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.EqualsBuilder;

@XmlRootElement
@XmlType(name="request")
@XmlAccessorType(XmlAccessType.FIELD)
public class Request {
	@XmlElement(name="id")
	private long _id;
	
	@XmlElement(name="requestor")
	private User _requestor;
	
	@XmlElement(name="book")
	private Book _book;
	
	@XmlElement(name="location")
	private Location _location;
	
	public Request(){
		this(0, null, null);
	}
	
	public Request(User user, Book book){
		this(0, user, book);
	}
	
	public Request(long id, User user, Book book){
		_id = id;
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
	
	public void setLocation(Location location){
		_location = location;
	}
	
	public Location getLocation(){
		return _location;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Request))
            return false;
        if (obj == this)
            return true;

        Request rhs = (Request) obj;
        return new EqualsBuilder().
            append(_id, rhs._id).
            append(_book, rhs._book).
            append(_requestor, rhs._requestor).
            append(_location, rhs._location).
            isEquals();
	}
}
