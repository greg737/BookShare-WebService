package nz.ac.auckland.bookShare.services;

import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nz.ac.auckland.bookShare.domain.Author;
import nz.ac.auckland.bookShare.domain.Book;
import nz.ac.auckland.bookShare.domain.Request;
import nz.ac.auckland.bookShare.domain.User;

/**
 * Service interface for the Users application. This interface allows Users to
 * be created, queried (by id) and updated.
 * 
 * @author Ian Warren
 *
 */
@Path("/users")
public class UserResource {
	private static final Logger _logger = LoggerFactory.getLogger(UserResource.class);
	private EntityManager _em = null;

	public UserResource() {
		_em = EntityManagerFactorySingleton.generateEntityManager();
	}

	/**
	 * Adds a new User to the system. The state of the new User is described by
	 * a nz.ac.auckland.User.dto.User object.
	 * 
	 * @param dtoUser
	 *            the User data included in the HTTP request body.
	 */
	@POST
	@Consumes("application/xml")
	public Response createUser(nz.ac.auckland.bookShare.dto.User dtoUser) {
		_logger.debug("Read User: " + dtoUser);
		User user = UserMapper.toDomainModel(dtoUser);

		_em.getTransaction().begin();
		_em.persist(user);
		_em.getTransaction().commit();
		_em.close();
		_logger.debug("Created User: " + user);
		return Response.created(URI.create("/users/" + user.getId())).build();
	}

	/**
	 * Returns a view of the User database, represented as a List of
	 * nz.ac.auckland.parolee.dto.Parolee objects.
	 * 
	 */
	@GET
	@Produces("application/xml")
	public List<nz.ac.auckland.bookShare.dto.User> getParolees() {
		_logger.debug("Creating list of users");
		List<nz.ac.auckland.bookShare.dto.User> users = new ArrayList<nz.ac.auckland.bookShare.dto.User>();
		_em.getTransaction().begin();
		List<User> results = _em.createQuery("select u from User u").getResultList();
		for (User user : results) {
			users.add(UserMapper.toDto(user));
		}
		_logger.debug("Returning list of users");
		_em.close();
		return users;
	}

	/**
	 * Retrieves candidateDTO parsed as XML
	 * 
	 * @param id
	 * @return CandidateDTO
	 */
	@GET
	@Path("{id}")
	@Produces("application/xml")
	public nz.ac.auckland.bookShare.dto.User getCandidateDtoXML(@PathParam("id") long id) {
		_em.getTransaction().begin();
		User user = _em.find(User.class, id);
		_logger.debug("Retrived User with ID: " + user.getId());
		nz.ac.auckland.bookShare.dto.User userdto = UserMapper.toDto(user);
		_em.getTransaction().commit();
		_em.close();

		return userdto;
	}

	/**
	 * Updates an existing User. The parts of a User that can be updated are
	 * those represented by a nz.ac.auckland.parolee.dto.Parolee instance.
	 * 
	 * @param dtoParolee
	 * 
	 */
	@PUT
	@Path("{id}/books")
	@Consumes("application/xml")
	public Response addBookForUser(@PathParam("id") long id, nz.ac.auckland.bookShare.dto.Book dtoBook) {
		_em.getTransaction().begin();
		Book book = BookMapper.toDomainModel(dtoBook);

		User user = _em.find(User.class, id);
		_logger.debug("Adding book to User with ID: " + user.getId());
		List<Author> result = _em
				.createQuery("select a from Author a where a._firstName = :first " + "and a._lastName = :last")
				.setParameter("first", book.getAuthor().getFirstName())
				.setParameter("last", book.getAuthor().getLastName()).getResultList();
		if (result.size() == 0) {
			_em.persist(book);
			user.addNewOwned(book);
			_em.persist(user);
			_logger.debug("Added new Book to User with ID = " + user.getId());
			_em.getTransaction().commit();
		} else {
			Book newbook = new Book(book.getId(), book.getName(), book.getGenre(), book.getLanguage(), book.getType(),
					result.get(0));
			_em.persist(newbook);
			user.addNewOwned(newbook);
			_em.persist(user);
			_logger.debug("Added new Book to User with ID = " + user.getId());
			_em.getTransaction().commit();
		}
		_em.close();
		return Response.created(URI.create("/users/" + id + "/books" + book.getId())).build();
	}

	@GET
	@Path("{id}/books")
	@Produces("application/xml")
	public List<nz.ac.auckland.bookShare.dto.Book> getBooks(@PathParam("id") long id) {
		List<nz.ac.auckland.bookShare.dto.Book> books = new ArrayList<nz.ac.auckland.bookShare.dto.Book>();
		_logger.debug("Retrieving list of books");

		_em.getTransaction().begin();
		User result = _em.find(User.class, id);
		for (Book book : result.getOwnedBooks()) {
			books.add(BookMapper.toDto(book));
		}
		_logger.debug("Returning list of books");
		_em.close();

		return books;
	}

	/**
	 * Updates an existing User. The parts of a User that can be updated are
	 * those represented by a nz.ac.auckland.parolee.dto.Parolee instance.
	 * 
	 * @param dtoParolee
	 * 
	 */
	@PUT
	@Path("{id}/requests")
	@Consumes("application/xml")
	public Response addRequestForUser(@PathParam("id") long id, nz.ac.auckland.bookShare.dto.Request dtoRequest) {
		_em.getTransaction().begin();
		Request request = RequestMapper.toDomainModel(dtoRequest);

		User user = _em.find(User.class, id);
		_logger.debug("Adding request to User with ID: " + user.getId());
		List<User> resultUser = _em
				.createQuery("select u from User u where u._firstName = :first " + "and u._lastName = :last")
				.setParameter("first", request.getRequestor().getFirstName())
				.setParameter("last", request.getRequestor().getLastName()).getResultList();
		List<Book> resultBook = _em
				.createQuery("select b from Book b where b._name = :name ")
				.setParameter("name", request.getBook().getName())
				.getResultList();
		if (resultUser.size() == 0) {
			if (resultBook.size() == 0) {
				_em.persist(request);
				user.addRequest(request);
				_em.persist(user);
				_logger.debug("Added new Request to User with ID = " + user.getId());
				_em.getTransaction().commit();
			} else {
				Request newRequest = new Request(request.getId(), request.getRequestor(), resultBook.get(0));
				_em.persist(newRequest);
				user.addRequest(newRequest);
				_em.persist(user);
				_logger.debug("Added new Request to User with ID = " + user.getId());
				_em.getTransaction().commit();
			}
		} else {
			if (resultBook.size() == 0) {
				Request newRequest = new Request(request.getId(), resultUser.get(0), request.getBook());
				_em.persist(newRequest);
				user.addRequest(newRequest);
				_em.persist(user);
				_logger.debug("Added new Request to User with ID = " + user.getId());
				_em.getTransaction().commit();
			} else {
				Request newRequest = new Request(request.getId(), resultUser.get(0), resultBook.get(0));
				_em.persist(newRequest);
				user.addRequest(newRequest);
				_em.persist(user);
				_logger.debug("Added new Request to User with ID = " + user.getId());
				_em.getTransaction().commit();
			}
		}
		_em.close();
		return Response.created(URI.create("/users/" + id + "/books" + request.getId())).build();
	}

	@GET
	@Path("{id}/requests")
	@Produces("application/xml")
	public List<nz.ac.auckland.bookShare.dto.Request> getRequests(@PathParam("id") long id) {
		List<nz.ac.auckland.bookShare.dto.Request> requests = new ArrayList<nz.ac.auckland.bookShare.dto.Request>();
		_logger.debug("Retrieving list of request");

		_em.getTransaction().begin();
		User result = _em.find(User.class, id);
		for (Request request : result.getRequests()) {
			requests.add(RequestMapper.toDto(request));
		}
		_logger.debug("Returning list of request");
		_em.close();

		return requests;
	}

	/**
	 * Wipes the database of all info (used for testing purposes only)
	 */
	@DELETE
	@Path("all/delete")
	public void wipeAll() {
		_logger.debug("WIPING DATA");
		_em.getTransaction().begin();
		List<Request> requests = _em.createQuery("select r from Request r").getResultList();
		for (Request request : requests) {
			_logger.debug("Removing Request: " + request.getId());
			_em.remove(request);
		}
		
		List<User> users = _em.createQuery("select u from User u").getResultList();
		for (User user : users) {
			_logger.debug("Removing User: " + user.getId());
			_em.remove(user);
		}

		List<Book> books = _em.createQuery("select b from Book b").getResultList();
		for (Book book : books) {
			_logger.debug("Removing Book: " + book.getId());
			_em.remove(book);
		}

		List<Author> authors = _em.createQuery("select a from Author a").getResultList();
		for (Author author : authors) {
			_logger.debug("Removing Author: " + author.getId());
			_em.remove(author);
		}
		
		_em.getTransaction().commit();
		_em.close();
	}
}
