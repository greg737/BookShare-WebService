package nz.ac.auckland.bookShare.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31). 
				append(_id).
				append(_book).
	            append(_requestor).
	            append(_location).
	            toHashCode();
	} 	 
}
