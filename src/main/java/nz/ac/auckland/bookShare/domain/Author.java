package nz.ac.auckland.bookShare.domain;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import static javax.persistence.FetchType.EAGER;

import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "AUTHOR", uniqueConstraints = 
{@UniqueConstraint(columnNames = { "FIRSTNAME", "LASTNAME" }) })
public class Author extends Person {
	@OneToMany(fetch = EAGER)
	private Collection<Book> _writtenBooks;

	public Author() {
		this(null, null);
	}

	public Author(long id) {
		this(id, null, null);
	}

	public Author(long id, String firstName, String lastName) {
		super(id, firstName, lastName);
		_writtenBooks = new ArrayList<Book>();
	}

	public Author(String firstName, String lastName) {
		super(firstName, lastName);
		_writtenBooks = new ArrayList<Book>();
	}

	public Collection<Book> getWrittenBooks() {
		return _writtenBooks;
	}

	public void addNewWritten(Book newBook) {
		_writtenBooks.add(newBook);
	}
}
