package nz.ac.auckland.bookShare.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import nz.ac.auckland.bookShare.domain.Genre;
import nz.ac.auckland.bookShare.domain.Language;
import nz.ac.auckland.bookShare.domain.Type;

/**
 * Bean class to represent a Parolee.
 * 
 * For this first Web service, a Parolee is simply represented by a unique id, a
 * name, gender and date of birth.
 * 
 * @author Ian Warren
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
	
	public Book(String name, Genre genre, Language language, Type type, Author author) {
		this(0, name, genre, language, type, author);
	}
	
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