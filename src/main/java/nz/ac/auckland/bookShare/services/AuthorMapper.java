package nz.ac.auckland.bookShare.services;

import nz.ac.auckland.bookShare.domain.Author;
import nz.ac.auckland.bookShare.domain.Book;

public class AuthorMapper {
	static Author toDomainModel(nz.ac.auckland.bookShare.dto.Author dtoAuthor) {
		Author fullAuthor = new Author(dtoAuthor.getId(), dtoAuthor.getFirstName(), dtoAuthor.getLastName());
		if (dtoAuthor.getWrittenBooks().size() > 0){
			for (nz.ac.auckland.bookShare.dto.Book book: dtoAuthor.getWrittenBooks()){
				fullAuthor.addNewWritten(BookMapper.toDomainModel(book));
			}
		}		
		return fullAuthor;
	}
	
	static nz.ac.auckland.bookShare.dto.Author toDto(Author author) {
		nz.ac.auckland.bookShare.dto.Author dtoAuthor = new nz.ac.auckland.bookShare.dto.Author(
				author.getId(), author.getFirstName(), author.getLastName());
		if (author.getWrittenBooks().size() > 0){
			for (Book book: author.getWrittenBooks()){
				dtoAuthor.addNewWritten(BookMapper.toDto(book));
			}
		}
		return dtoAuthor;
	}
}
