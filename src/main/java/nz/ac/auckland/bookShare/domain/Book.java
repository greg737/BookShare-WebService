package nz.ac.auckland.bookShare.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import nz.ac.auckland.bookShare.domain.Author;

/**
 * Bean class to represent a Parolee.
 * 
 * For this first Web service, a Parolee is simply represented by a unique id, a
 * name, gender and date of birth.
 * 
 * @author Ian Warren
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
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Book))
            return false;
        if (obj == this)
            return true;

        Book rhs = (Book) obj;
        return new EqualsBuilder().
            append(_id, rhs._id).
            append(_name, rhs._name).
            append(_genre, rhs._genre).
            append(_language, rhs._language).
            append(_type, rhs._type).
            append(_author, rhs._author).
            isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31). 
				append(_id).
	            append(_name).
	            append(_genre).
	            append(_language).
	            append(_type).
	            append(_author).
	            toHashCode();
	}
}