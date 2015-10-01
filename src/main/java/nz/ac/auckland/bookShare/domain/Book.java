package nz.ac.auckland.bookShare.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import nz.ac.auckland.bookShare.domain.Author;

/**
 * Bean class to represent a Book.
 * 
 * A book must have unique name, a genre, a language, a book type and
 * a author 
 * 
 * @author Greggory Tan
 *
 */
@Entity
public class Book {
	@Id
	@GeneratedValue(generator="ID_GENERATOR")
	@Column(name="BOOK_ID")
	private long _id;

	@Column(name="NAME", nullable = false, unique = true)
	private String _name;
	
	@Column(name="GENRE")
	private Genre _genre;
	
	@ManyToOne(optional = false, cascade=CascadeType.PERSIST)
	private Author _author; 
	
	@Column(name="LANGUAGE")
	private Language _language;
	
	@Column(name="TYPE")
	private Type _type;
	
	//Default constructor method for JAXB
	public Book() {
		this(null, null, null, null, null);
	}
	
	public Book(long id) {
		this(id, null, null, null, null, null);
	}
	
	public Book(long id, String name, Genre genre, Language language, Type type, Author author) {
		this(name, genre, language, type, author);
		_id = id;
	}

	public Book(String name, Genre genre, Language language, Type type, Author author) {
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