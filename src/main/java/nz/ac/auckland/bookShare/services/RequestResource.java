package nz.ac.auckland.bookShare.services;

import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nz.ac.auckland.bookShare.domain.Request;

@Path("/request")
public class RequestResource {
	private static final Logger _logger = LoggerFactory.getLogger(UserResource.class);
	private EntityManager _em = null;

	public RequestResource() {
		_em = EntityManagerFactorySingleton.generateEntityManager();
	}
	
	/**
	 * Returns nz.ac.auckland.bookShare.dto.Request with matching id parsed as XML
	 * 
	 * @param id
	 * @return nz.ac.auckland.bookShare.dto.Request
	 */
	@GET
	@Path("{id}")
	@Produces("application/xml")
	public nz.ac.auckland.bookShare.dto.Request getBookDto(@PathParam("id") long id) {
		_em.getTransaction().begin();
		Request request = _em.find(Request.class, id);
		_logger.debug("Retrived Request with ID: " + request.getId());
		nz.ac.auckland.bookShare.dto.Request requestDto = RequestMapper.toDto(request);
		_em.getTransaction().commit();
		_em.close();

		return requestDto;
	}
}
