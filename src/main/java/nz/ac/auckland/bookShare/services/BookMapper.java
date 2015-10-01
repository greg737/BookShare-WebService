package nz.ac.auckland.bookShare.services;

import nz.ac.auckland.bookShare.domain.Book;

/**
 * Helper class to convert between domain-model and DTO objects representing
 * Book.
 * 
 * @author Greggory Tan
 *
 */
public class BookMapper {
	static Book toDomainModel(nz.ac.auckland.bookShare.dto.Book dtoBook) {
		Book fullBook = new Book(dtoBook.getId(), dtoBook.getName(), dtoBook.getGenre(), 
				dtoBook.getLanguage(), dtoBook.getType(),
				AuthorMapper.toDomainModel(dtoBook.getAuthor()));
		return fullBook;
	}
	
	static nz.ac.auckland.bookShare.dto.Book toDto(Book book) {
		nz.ac.auckland.bookShare.dto.Book dtoBook = new nz.ac.auckland.bookShare.dto.Book(
				book.getId(), book.getName(), book.getGenre(), book.getLanguage(), book.getType(),
				AuthorMapper.toDto(book.getAuthor()));
		return dtoBook;
		
	}
}
