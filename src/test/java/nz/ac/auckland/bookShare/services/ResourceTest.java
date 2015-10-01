package nz.ac.auckland.bookShare.services;

import static org.junit.Assert.*;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import nz.ac.auckland.bookShare.domain.Genre;
import nz.ac.auckland.bookShare.domain.Language;
import nz.ac.auckland.bookShare.domain.Type;
import nz.ac.auckland.bookShare.dto.Author;
import nz.ac.auckland.bookShare.dto.Book;
import nz.ac.auckland.bookShare.dto.Request;
import nz.ac.auckland.bookShare.dto.User;

public class ResourceTest {
	private static final String WEB_SERVICE_URI = "http://localhost:8080/services/users";

	private static Client _client;
	public static boolean clearData = true;

	/**
	 * One-time setup method that creates a Web service client.
	 */
	@Before
	public void setUpClient() {
		_client = ClientBuilder.newClient();
	}

	/**
	 * Finalization method that destroys the Web service client.
	 */
	@After
	public void destroyClient() {
		_client.close();
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
		}
	}

	@BeforeClass
	public static void deleteAllData() {
		if (clearData) {
			_client = ClientBuilder.newClient();
			Response delete = _client.target(WEB_SERVICE_URI + "/all/delete").request().delete();
			delete.close();
			_client.close();
		}
	}

	/**
	 * Tests that the Web service can create a new user and retrieve the new
	 * user.
	 */
	@Test
	public void addAndRetrieveUser() {
		User zoran = new User("lecturer1", "Zoran", "Salcic", "Auckland");
		Response zoranResponse = _client.target(WEB_SERVICE_URI).request().post(Entity.xml(zoran));

		if (zoranResponse.getStatus() != 201) {
			fail("Failed to create new User");
		}

		String location = zoranResponse.getLocation().toString();
		zoranResponse.close();

		// Query the Web service for the new Parolee.
		User zoranFromService = _client.target(location).request().accept("application/xml").get(User.class);

		// The original local Parolee object (zoran) should have a value equal
		// to that of the Parolee object representing Zoran that is later
		// queried from the Web service. The only exception is the value
		// returned by getId(), because the Web service assigns this when it
		// creates a Parolee.
		assertEquals(zoran.getUserName(), zoranFromService.getUserName());
		assertEquals(zoran.getLastName(), zoranFromService.getLastName());
		assertEquals(zoran.getFirstName(), zoranFromService.getFirstName());
		assertEquals(zoran.getGender(), zoranFromService.getGender());
		assertEquals(zoran.getCity(), zoranFromService.getCity());
	}

	/**
	 * Tests that the Web service can return all users.
	 */
	@Test
	public void updateUserBookLibrary() {
		User user1 = new User("programmer1", "Nasser", "Giacaman", "Auckland");
		Response response0 = _client.target(WEB_SERVICE_URI).request().post(Entity.xml(user1));
		String location = response0.getLocation().toString();
		response0.close();

		Author author = new Author("Mark", "Wilson");
		Book book = new Book("How To Program", Genre.ACADEMIC, Language.ENGLISH, Type.PAPERBACK, author);
		Response response1 = _client.target(location + "/books").request().put(Entity.xml(book));

		if (response1.getStatus() != 201) {
			fail("Failed to add new Book");
		}
		String location1 = response1.getLocation().toString();
		String idStr = location1.substring(location1.lastIndexOf('/') + 1);
		response1.close();
		Book bookFromService = _client.target("http://localhost:8080/services/books/" + idStr).request()
				.accept("application/xml").get(Book.class);

		assertEquals(book.getName(), bookFromService.getName());
		assertEquals(book.getType(), bookFromService.getType());
		assertEquals(book.getLanguage(), bookFromService.getLanguage());
		assertEquals(book.getGenre(), bookFromService.getGenre());
		assertEquals(book.getAuthor().getFirstName(), bookFromService.getAuthor().getFirstName());
		assertEquals(book.getAuthor().getLastName(), bookFromService.getAuthor().getLastName());
	}

	/**
	 * Tests that the Web service can return all users.
	 */
	@Test
	public void updateUserRequest() {
		User user1 = new User("programmer2", "Catherine", "Watson", "Auckland");
		Response requestResponse0 = _client.target(WEB_SERVICE_URI).request().post(Entity.xml(user1));
		String location1 = requestResponse0.getLocation().toString();
		requestResponse0.close();
		Author author = new Author("Mark", "Wilson");
		Book book = new Book("How To DDOS", Genre.ACADEMIC, Language.ENGLISH, Type.PAPERBACK, author);
		Response requestResponse1 = _client.target(location1 + "/books").request().put(Entity.xml(book));
		requestResponse1.close();

		User user2 = new User("lecturer101", "Pete", "Bier", "Auckland");
		Response requestResponse2 = _client.target(WEB_SERVICE_URI).request().post(Entity.xml(user2));
		requestResponse2.close();

		Request request = new Request(user2, book);
		Response requestResponse3 = _client.target(location1 + "/requests").request().put(Entity.xml(request));

		if (requestResponse3.getStatus() != 201) {
			fail("Failed to add new Request");
		}
		String location3 = requestResponse3.getLocation().toString();
		String idStr = location3.substring(location3.lastIndexOf('/') + 1);
		requestResponse3.close();
		Request requestFromService = _client.target("http://localhost:8080/services/request/" + idStr).request()
				.accept("application/xml").get(Request.class);

		assertEquals(request.getBook().getName(), requestFromService.getBook().getName());
		assertEquals(request.getBook().getType(), requestFromService.getBook().getType());
		assertEquals(request.getBook().getLanguage(), requestFromService.getBook().getLanguage());
		assertEquals(request.getBook().getGenre(), requestFromService.getBook().getGenre());
	}

	/**
	 * Tests that the Web service can return all users.
	 */
	@Test
	public void userRequestQueryParam() {
		User user1 = new User("user1", "Cath", "Wat", "Auckland");
		Response userResponse0 = _client.target(WEB_SERVICE_URI).request().post(Entity.xml(user1));
		String location1 = userResponse0.getLocation().toString();
		userResponse0.close();

		Author author = new Author("Mark", "Wilson");
		Book book = new Book("How To WebService", Genre.ACADEMIC, Language.ENGLISH, Type.PAPERBACK, author);
		Response authorResponse1 = _client.target(location1 + "/books").request().put(Entity.xml(book));
		authorResponse1.close();

		Book book1 = new Book("How To Algorithm", Genre.ACADEMIC, Language.ENGLISH, Type.PAPERBACK, author);
		Response bookResponse2 = _client.target(location1 + "/books").request().put(Entity.xml(book1));
		bookResponse2.close();

		Book book2 = new Book("How Not To DDOS", Genre.ACADEMIC, Language.ENGLISH, Type.PAPERBACK, author);
		Response bookResponse3 = _client.target(location1 + "/books").request().put(Entity.xml(book2));
		bookResponse3.close();

		User user2 = new User("lecturer010", "Peter", "Peter", "Auckland");
		Response userResponse4 = _client.target(WEB_SERVICE_URI).request().post(Entity.xml(user2));
		userResponse4.close();

		Request request1 = new Request(user2, book);
		Response requestResponse5 = _client.target(location1 + "/requests").request().put(Entity.xml(request1));
		requestResponse5.close();

		Request request2 = new Request(user2, book1);
		Response requestResponse6 = _client.target(location1 + "/requests").request().put(Entity.xml(request2));
		requestResponse6.close();

		Request request3 = new Request(user2, book2);
		Response requestResponse7 = _client.target(location1 + "/requests").request().put(Entity.xml(request3));
		requestResponse7.close();

		List<Request> result = _client.target(location1 + "/requests?size=2").request().accept("application/xml")
				.get(new GenericType<List<Request>>() {
				});

		assertEquals(2, result.size());
	}
	
	/**
	 * Tests that the Web service can return all users.
	 */
	@Test
	public void userBookQueryParam() {
		User user1 = new User("user23", "Cat", "Watsons", "Auckland");
		Response userResponse0 = _client.target(WEB_SERVICE_URI).request().post(Entity.xml(user1));
		String location1 = userResponse0.getLocation().toString();
		userResponse0.close();

		Author author = new Author("Gabe", "Newell");
		Book book = new Book("PC MASTER RACE", Genre.COMEDY, Language.ENGLISH, Type.PAPERBACK, author);
		Response authorResponse1 = _client.target(location1 + "/books").request().put(Entity.xml(book));
		authorResponse1.close();

		Book book1 = new Book("How To Make Easy Money", Genre.ACADEMIC, Language.ENGLISH, Type.PAPERBACK, author);
		Response bookResponse2 = _client.target(location1 + "/books").request().put(Entity.xml(book1));
		bookResponse2.close();

		Book book2 = new Book("Console Not MasterRace", Genre.COMEDY, Language.ENGLISH, Type.PAPERBACK, author);
		Response bookResponse3 = _client.target(location1 + "/books").request().put(Entity.xml(book2));
		bookResponse3.close();

		List<Book> result = _client.target(location1 + "/books?size=2").request().accept("application/xml")
				.get(new GenericType<List<Book>>() {
				});

		assertEquals(2, result.size());
	}
}
