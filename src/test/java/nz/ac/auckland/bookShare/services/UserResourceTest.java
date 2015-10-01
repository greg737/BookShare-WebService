package nz.ac.auckland.bookShare.services;

import static org.junit.Assert.*;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nz.ac.auckland.bookShare.domain.Genre;
import nz.ac.auckland.bookShare.domain.Language;
import nz.ac.auckland.bookShare.domain.Type;
import nz.ac.auckland.bookShare.dto.Author;
import nz.ac.auckland.bookShare.dto.Book;
import nz.ac.auckland.bookShare.dto.Request;
import nz.ac.auckland.bookShare.dto.User;

public class UserResourceTest {
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
			Response response = _client.target(WEB_SERVICE_URI + "/all/delete").request().delete();
			response.close();
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
		Response response = _client.target(WEB_SERVICE_URI).request().post(Entity.xml(zoran));

		if (response.getStatus() != 201) {
			fail("Failed to create new User");
		}

		String location = response.getLocation().toString();
		response.close();

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
		response1.close();
		List<Book> books = _client.target(location + "/books").request().accept("application/xml")
				.get(new GenericType<List<Book>>() {
				});
		Book bookFromService = books.get(0);
		
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
		Response response0 = _client.target(WEB_SERVICE_URI).request().post(Entity.xml(user1));
		String location1 = response0.getLocation().toString();
		response0.close();
		Author author = new Author("Mark", "Wilson");
		Book book = new Book("How To DDOS", Genre.ACADEMIC, Language.ENGLISH, Type.PAPERBACK, author);
		Response response1 = _client.target(location1 + "/books").request().put(Entity.xml(book));
		response1.close();
		
		User user2 = new User("lecturer101", "Pete", "Bier", "Auckland");
		Response response2 = _client.target(WEB_SERVICE_URI).request().post(Entity.xml(user2));
		String location2 = response2.getLocation().toString();
		response2.close();
		
		Request request = new Request(user1, book);
		Response response3 = _client.target(location2 + "/requests").request().put(Entity.xml(request));

		if (response3.getStatus() != 201) {
			fail("Failed to add new Request");
		}
		response3.close();
		
		List<Request> requests = _client.target(location2 + "/requests").request().accept("application/xml")
				.get(new GenericType<List<Request>>() {
				});
		Request requestFromService = requests.get(0);
		
		assertEquals(request.getBook().getName(), requestFromService.getBook().getName());
		assertEquals(request.getBook().getType(), requestFromService.getBook().getType());
		assertEquals(request.getBook().getLanguage(), requestFromService.getBook().getLanguage());
		assertEquals(request.getBook().getGenre(), requestFromService.getBook().getGenre());
		assertEquals(request.getRequestor().getFirstName(), requestFromService.getRequestor().getFirstName());
		assertEquals(request.getRequestor().getLastName(), requestFromService.getRequestor().getLastName());
	}
}
