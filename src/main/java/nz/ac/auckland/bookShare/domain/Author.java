package nz.ac.auckland.bookShare.domain;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Author))
			return false;
		if (obj == this)
			return true;

		Author rhs = (Author) obj;
		return new EqualsBuilder().append(this.getId(), rhs.getId()).append(this.getFirstName(), rhs.getFirstName())
				.append(this.getLastName(), rhs.getLastName()).append(this.getWrittenBooks(), rhs.getWrittenBooks())
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31).append(this.getId()).append(this.getFirstName()).append(this.getLastName())
				.append(this.getWrittenBooks()).toHashCode();
	}
}
