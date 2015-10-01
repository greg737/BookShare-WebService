package nz.ac.auckland.bookShare.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * DTO class to represent a Author.
 * 
 * @author Greggory Tan
 *
 */
@XmlRootElement
@XmlType(name="author")
@XmlAccessorType(XmlAccessType.FIELD)
public class Author extends Person {
	
	@XmlElementWrapper(name = "written-books")
	@XmlElement(name = "bookID")
	private List<Long> _writtenBooks = new ArrayList<Long>();
	
	public Author(){
		this(0, null, null);
	}
	
	/**
	 * Constructs a DTO Author instance. This method is intended to be called
	 * by Web service clients when creating new Author. 
	 */
	public Author(String firstName, String lastName) {
		this(0 , firstName, lastName);
	}
	
	/**
	 * Constructs a DTO Author instance. This method should NOT be called by 
	 * Web Service clients. It is intended to be used by the Web Service 
	 * implementation when creating a DTO Author from a domain-model Author 
	 * object.
	 */
	public Author(long id, String firstName, String lastName){
		super(id, firstName, lastName);
	}

	public Collection<Long> getWrittenBooks() {
		return _writtenBooks;
	}

	public void addNewWritten(long bookID) {
		_writtenBooks.add(bookID);
	}
}
