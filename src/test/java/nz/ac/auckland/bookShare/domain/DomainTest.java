package nz.ac.auckland.bookShare.domain;

import org.junit.Test;

import nz.ac.auckland.bookShare.domain.Author;
import nz.ac.auckland.bookShare.domain.Book;
import nz.ac.auckland.bookShare.domain.Genre;
import nz.ac.auckland.bookShare.domain.Type;
import nz.ac.auckland.bookShare.domain.User;

public class DomainTest extends JpaTest {
	//private static Logger _logger = LoggerFactory.getLogger(DomainTest.class);

	@Test
	public void persistItem() {
		
		_entityManager.getTransaction().begin();
		
		User user1 = new User("bookLover1", "Jess", "A", "Auckland");
	
		User user2 = new User("bookLover2", "James", "B", "AUckland");
		
		Author author1 = new Author("James", "James");
		
		Book book1 = new Book("How to program", Genre.COMEDY, "English", Type.PAPERBACK, author1);
		
		Book book2 = new Book("How to be a pro", Genre.CLASSIC, "English", Type.PAPERBACK, author1);

		Book book3 = new Book("How to game", Genre.CLASSIC, "English", Type.PAPERBACK, author1);
		
		Request request1 = new Request(user2, book1);
		
		request1.setLocation(new Location(5, 5));
		
		Request request2 = new Request(user1, book2);
		
		request2.setLocation(new Location(5, 5));
		
		user1.addNewOwned(book1);
		user2.addNewOwned(book2);
		user1.addNewOwned(book3);
		user1.addRequest(request1);
		user2.addRequest(request2);
		
		_entityManager.persist(author1);

		_entityManager.persist(user1);

		_entityManager.persist(user2);
		
		_entityManager.persist(book1);
		
		_entityManager.persist(book2);
		
		_entityManager.persist(book3);
				
		_entityManager.persist(request1);
		
		_entityManager.persist(request2);
		
		_entityManager.getTransaction().commit();
	}

}
