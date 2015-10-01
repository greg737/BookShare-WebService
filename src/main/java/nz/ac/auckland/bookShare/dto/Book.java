package nz.ac.auckland.bookShare.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import nz.ac.auckland.bookShare.domain.Genre;
import nz.ac.auckland.bookShare.domain.Language;
import nz.ac.auckland.bookShare.domain.Type;

/**
 * DTO class to represent a Book.
 * 
 * @author Greggory Tan
 *
 */
@XmlRootElement
@XmlType(name="book")
@XmlAccessorType(XmlAccessType.FIELD)
public class Book {
	@XmlElement(name="id")
	private long _id;

	@XmlElement(name="name")
	private String _name;
	
	@XmlElement(name="genre")
	private Genre _genre;
	
	@XmlElement(name="author")
	private Author _author; 
	
	@XmlElement(name="language")
	private Language _language;
	
	@XmlElement(name="type")
	private Type _type;

	public Book() {
		this(null, null, null, null, null);
	}
	
	/**
	 * Constructs a DTO Book instance. This method is intended to be called
	 * by Web service clients when creating new Book. 
	 */
	public Book(String name, Genre genre, Language language, Type type, Author author) {
		this(0, name, genre, language, type, author);
	}
	
	/**
	 * Constructs a DTO Book instance. This method should NOT be called by 
	 * Web Service clients. It is intended to be used by the Web Service 
	 * implementation when creating a DTO Book from a domain-model Book 
	 * object.
	 */
	public Book(long id, String name, Genre genre, Language language, Type type, Author author) {
		_id = id;
		_name = name;
		_genre = genre;
		_language = language;
		_type = type;
		_author = author;
	}
		
	public Genre getGenre() {
		return _genre;
	}

	public Author getAuthor() {
		return _author;
	}

	public Language getLanguage() {
		return _language;
	}

	public Type getType() {
		return _type;
	}

	public long getId() {
		return _id;
	}

	public String getName() {
		return _name;
	}
}