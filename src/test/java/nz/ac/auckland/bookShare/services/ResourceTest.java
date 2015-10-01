package nz.ac.auckland.bookShare.services;

import static org.junit.Assert.*;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Cookie;
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

/**
 * Tests all the resourse implementaion for this webservice
 * 
 * @author Greggory Tan
 *
 */
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

	/**
	 * One-time setup method that deletes the the rows in the database if the 
	 * condition cleardata is true.
	 */
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

		// Query the Web service for the new user.
		User zoranFromService = _client.target(location).request().accept("application/xml").get(User.class);

		assertEquals(zoran.getUserName(), zoranFromService.getUserName());
		assertEquals(zoran.getLastName(), zoranFromService.getLastName());
		assertEquals(zoran.getFirstName(), zoranFromService.getFirstName());
		assertEquals(zoran.getGender(), zoranFromService.getGender());
		assertEquals(zoran.getCity(), zoranFromService.getCity());
	}

	/**
	 * Tests that the Web service can add a new book to a user's library
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
	 * Tests that the Web service can add a new request to a user
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
	 * Tests that the Web service can return a number of request.
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
	 * Tests that the Web service can return a number of books owned by the user.
	 */
	@Test
	public void userResourceBookQueryParam() {
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

	/**
	 * Tests that the Web service can return a number of books in the database.
	 */
	@Test
	public void bookResourceQueryParam() {
		User user1 = new User("unknownUser", "Un", "Knowned", "Auckland");
		Response userResponse0 = _client.target(WEB_SERVICE_URI).request().post(Entity.xml(user1));
		String location1 = userResponse0.getLocation().toString();
		userResponse0.close();

		Author author = new Author("Gabe", "Newell");
		Book book = new Book("PC NOT MASTER RACE", Genre.COMEDY, Language.ENGLISH, Type.PAPERBACK, author);
		Response authorResponse1 = _client.target(location1 + "/books").request().put(Entity.xml(book));
		authorResponse1.close();

		Book book1 = new Book("How To Not Make Easy Money", Genre.ACADEMIC, Language.ENGLISH, Type.PAPERBACK, author);
		Response bookResponse2 = _client.target(location1 + "/books").request().put(Entity.xml(book1));
		bookResponse2.close();

		Book book2 = new Book("Console is MasterRace", Genre.COMEDY, Language.ENGLISH, Type.PAPERBACK, author);
		Response bookResponse3 = _client.target(location1 + "/books").request().put(Entity.xml(book2));
		bookResponse3.close();

		Book book3 = new Book("Console is Not MasterRace", Genre.COMEDY, Language.ENGLISH, Type.PAPERBACK, author);
		Response bookResponse4 = _client.target(location1 + "/books").request().put(Entity.xml(book3));
		bookResponse4.close();

		List<Book> result = _client.target(location1 + "/books?size=2").request().accept("application/xml")
				.get(new GenericType<List<Book>>() {
				});

		assertEquals(2, result.size());
	}

	/**
	 * Tests that a book can be owned by different users.
	 */
	@Test
	public void bookOwnedByMultipleUsers() {
		User user1 = new User("users145", "Caths", "Watting", "Auckland");
		Response userResponse0 = _client.target(WEB_SERVICE_URI).request().post(Entity.xml(user1));
		String location1 = userResponse0.getLocation().toString();
		userResponse0.close();

		User user2 = new User("users134", "Random", "User", "Auckland");
		Response userResponse1 = _client.target(WEB_SERVICE_URI).request().post(Entity.xml(user2));
		String location2 = userResponse1.getLocation().toString();
		userResponse1.close();

		Author author = new Author("Mark", "Wilson");
		Book book = new Book("Insert Name", Genre.ACADEMIC, Language.ENGLISH, Type.PAPERBACK, author);
		Response authorResponse1 = _client.target(location1 + "/books").request().put(Entity.xml(book));
		authorResponse1.close();

		Book book1 = new Book("Do not read", Genre.ACADEMIC, Language.ENGLISH, Type.PAPERBACK, author);
		Response bookResponse2 = _client.target(location1 + "/books").request().put(Entity.xml(book1));
		bookResponse2.close();

		Book book2 = new Book("Testing 123", Genre.ACADEMIC, Language.ENGLISH, Type.PAPERBACK, author);
		Response bookResponse3 = _client.target(location1 + "/books").request().put(Entity.xml(book2));
		String location3 = bookResponse3.getLocation().toString();
		String idStr = location3.substring(location3.lastIndexOf('/') + 1);
		bookResponse3.close();

		Response bookResponse4 = _client.target(location2 + "/books").request().put(Entity.xml(book2));
		String location4 = bookResponse4.getLocation().toString();
		String idStr1 = location4.substring(location4.lastIndexOf('/') + 1);
		bookResponse4.close();

		List<User> users = _client.target("http://localhost:8080/services/books/" + idStr + "/owners").request()
				.accept("application/xml").get(new GenericType<List<User>>() {
				});
		assertEquals(2, users.size());
	}
	
	/**
	 * Tests that the Web service can return a number of request.
	 */
	@Test
	public void userQueryParam() {
		User user0 = new User("hacker1", "Best", "Hacker", "Auckland");
		Response response0 = _client.target(WEB_SERVICE_URI).request().post(Entity.xml(user0));
		response0.close();
		User user1 = new User("hacker2", "What", "Hacker", "Auckland");
		Response response1 = _client.target(WEB_SERVICE_URI).request().post(Entity.xml(user1));
		response1.close();
		User user2 = new User("hacker3", "Worst", "Hacker", "Auckland");
		Response response2 = _client.target(WEB_SERVICE_URI).request().post(Entity.xml(user2));
		response2.close();
		
		List<User> users = _client.target(WEB_SERVICE_URI + "?size=2").request()
				.accept("application/xml").get(new GenericType<List<User>>() {
				});
		assertEquals(2, users.size());

	}

	/**
	 * Testing if the web service can create a cookie that holds the id of the user
	 * and then retrieve that cookie This can be used to find other users in the same city
	 */
	@Test
	public void testUserLogin() {
		User user0 = new User("lecturer234", "Best", "Lecturer", "Wellington");
		Response response0 = _client.target(WEB_SERVICE_URI).request().post(Entity.xml(user0));
		String location = response0.getLocation().toString();
		response0.close();
		User user1 = new User("lecturer002", "Not", "Lecturer", "Wellington");
		Response response1 = _client.target(WEB_SERVICE_URI).request().post(Entity.xml(user1));
		response1.close();
		User user2 = new User("lecturer012", "Maybe", "Lecturer", "Wellington");
		Response response2 = _client.target(WEB_SERVICE_URI).request().post(Entity.xml(user2));
		response2.close();
		
		

		// Create a cookie for the user
		Response response3  = _client.target(location + "/login").request().get();
		Cookie cookie = response3.getCookies().get("user").toCookie();
		response3.close();

		// Now retrieve users in same city with cookie
		List<User> users = _client.target(WEB_SERVICE_URI ).request().cookie(cookie)
				.get(new GenericType<List<User>>() {
				});
		assertEquals(3, users.size());
		assertEquals(user0.getCity(), users.get(0).getCity());
		assertEquals(user0.getCity(), users.get(1).getCity());
		assertEquals(user0.getCity(), users.get(2).getCity());
	}
}
