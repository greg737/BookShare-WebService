package nz.ac.auckland.bookShare.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nz.ac.auckland.bookShare.domain.Book;
import nz.ac.auckland.bookShare.domain.User;

@Path("/books")
public class BookResource {
	private static final Logger _logger = LoggerFactory.getLogger(UserResource.class);
	private EntityManager _em = null;

	public BookResource() {
		_em = EntityManagerFactorySingleton.generateEntityManager();
	}

	/**
	 * Returns a view of the Book database, represented as a List of
	 * nz.ac.auckland.bookShare.dto.Book objects.
	 * A QueryParam of size can be included to limit the number of books returned.
	 * 
	 * @param size
	 * @return List<nz.ac.auckland.bookShare.dto.Book>
	 */
	@GET
	@Produces("application/xml")
	public List<nz.ac.auckland.bookShare.dto.Book> getBooks(@DefaultValue(value = "-1") @QueryParam("size") int size) {
		List<nz.ac.auckland.bookShare.dto.Book> books = new ArrayList<nz.ac.auckland.bookShare.dto.Book>();
		_logger.debug("Retrieving list of books");

		_em.getTransaction().begin();
		List<Book> results = _em.createQuery("select u from Book u").getResultList();
		if (size == -1) {
			for (Book book : results) {
				books.add(BookMapper.toDto(book));
			}
		} else {
			for (Book book : results) {
				if (size > 0) {
					books.add(BookMapper.toDto(book));
					size--;
				}
			}
		}

		_logger.debug("Returning list of books");
		_em.close();

		return books;
	}

	/**
	 * Returns nz.ac.auckland.bookShare.dto.Book with matching id parsed as XML
	 * 
	 * @param id
	 * @return nz.ac.auckland.bookShare.dto.Book
	 */
	@GET
	@Path("{id}")
	@Produces("application/xml")
	public nz.ac.auckland.bookShare.dto.Book getBookDto(@PathParam("id") long id) {
		_em.getTransaction().begin();
		Book book = _em.find(Book.class, id);
		_logger.debug("Retrived Book with ID: " + book.getId());
		nz.ac.auckland.bookShare.dto.Book bookDto = BookMapper.toDto(book);
		_em.getTransaction().commit();
		_em.close();

		return bookDto;
	}

	/**
	 * Returns the owners of a book, represented as a List of
	 * nz.ac.auckland.bookShare.dto.Book objects.
	 * A QueryParam of size can be included to limit the number of books returned.
	 * 
	 * @param id
	 * @param size
	 * @return List<nz.ac.auckland.bookShare.dto.User>
	 */
	@GET
	@Path("{id}/owners")
	@Produces("application/xml")
	public List<nz.ac.auckland.bookShare.dto.User> getOwners(@PathParam("id") long id, @DefaultValue(value = "-1") @QueryParam("size") int size) {
		List<nz.ac.auckland.bookShare.dto.User> users = new ArrayList<nz.ac.auckland.bookShare.dto.User>();
		_logger.debug("Retrieving list of owners");

		_em.getTransaction().begin();
		Book book = _em.find(Book.class, id);
		List<User> results = _em.createQuery("select u from User u where :book member of u._ownedBooks")
				.setParameter("book", book).getResultList();
		if (size == -1) {
			for (User user : results) {
				users.add(UserMapper.toDto(user));
			}
		} else {
			for (User user : results) {
				if (size > 0) {
					users.add(UserMapper.toDto(user));
					size--;
				}
			}
		}

		_logger.debug("Returning list of owners");
		_em.close();

		return users;
	}
}
