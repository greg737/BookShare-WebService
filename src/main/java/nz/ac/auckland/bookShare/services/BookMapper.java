package nz.ac.auckland.bookShare.services;

import nz.ac.auckland.bookShare.domain.Author;
import nz.ac.auckland.bookShare.domain.Book;

public class BookMapper {
	static Book toDomainModel(nz.ac.auckland.bookShare.dto.Book dtoBook) {
		Book fullBook = new Book(dtoBook.getId(), dtoBook.getName(), dtoBook.get_genre(), 
				dtoBook.getLanguage(), dtoBook.getType(),
				new Author(dtoBook.getAuthor()));
		return fullBook;
	}
	
	static nz.ac.auckland.bookShare.dto.Book toDto(Book book) {
		nz.ac.auckland.bookShare.dto.Book dtoBook = new nz.ac.auckland.bookShare.dto.Book(
				book.getId(), book.getName(), book.get_genre(), book.getLanguage(), book.getType(),
				book.getAuthor().getId());
		return dtoBook;
		
	}
}
