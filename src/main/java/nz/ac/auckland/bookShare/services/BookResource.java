package nz.ac.auckland.bookShare.services;

import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nz.ac.auckland.bookShare.domain.Book;

public class BookResource {
	private static final Logger _logger = LoggerFactory.getLogger(UserResource.class);
	private EntityManager _em = null;

	public BookResource() {
		_em = EntityManagerFactorySingleton.generateEntityManager();
	}
	
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
}
