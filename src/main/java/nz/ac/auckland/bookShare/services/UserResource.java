package nz.ac.auckland.bookShare.services;

import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
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
		List<nz.ac.auckland.bookShare.dto.User> users = new ArrayList<nz.ac.auckland.bookShare.dto.User>();
		_em.getTransaction().begin();
		List<User> results = _em.createQuery("select u from User").getResultList();
		for (User user : results) {
			users.add(UserMapper.toDto(user));
		}
		return users;
	}
}
