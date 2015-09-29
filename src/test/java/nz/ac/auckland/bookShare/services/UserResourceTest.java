package nz.ac.auckland.bookShare.services;

import static org.junit.Assert.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import nz.ac.auckland.bookShare.dto.User;

public class UserResourceTest {
	private static final String WEB_SERVICE_URI = "http://localhost:8080/services/users";

	private static Client _client;

	/**
	 * One-time setup method that creates a Web service client.
	 */
	@Before
	public void setUpClient() {
		_client = ClientBuilder.newClient();
	}

	/**
	 * One-time finalisation method that destroys the Web service client.
	 */
	@AfterClass
	public static void destroyClient() {
		_client.close();
	}
	
	/**
	 * Tests that the Web service can create a new Parolee.
	 */
	@Test
	public void addUser() {
		User zoran = new User("lecturer101", "Zoran", "Salcic", "Auckland");

		Response response = _client
				.target(WEB_SERVICE_URI).request()
				.post(Entity.xml(zoran));
		
		if (response.getStatus() != 201) {
			fail("Failed to create new Parolee");
		}

		String location = response.getLocation().toString();
		response.close();

		// Query the Web service for the new Parolee.
		User zoranFromService = _client.target(location).request()
				.accept("application/xml").get(User.class);

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
}
