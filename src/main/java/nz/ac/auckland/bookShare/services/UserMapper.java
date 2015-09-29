package nz.ac.auckland.bookShare.services;

import nz.ac.auckland.bookShare.domain.User;

/**
 * Helper class to convert between domain-model and DTO objects representing
 * Parolees.
 * 
 * @author Ian Warren
 *
 */
public class UserMapper {

	static User toDomainModel(nz.ac.auckland.bookShare.dto.User dtoUser) {
		User fullUser = new User(dtoUser.getUserName(), dtoUser.getFirstName(),
				dtoUser.getLastName(), dtoUser.getCity());
		return fullUser;
	}
	
	static nz.ac.auckland.bookShare.dto.User toDto(User user) {
		nz.ac.auckland.bookShare.dto.User dtoUser = new nz.ac.auckland.bookShare.dto.User(
				user.getId(), user.getUserName(), user.getFirstName(), user.getLastName(), user.getCity());
		return dtoUser;	
	}
}
