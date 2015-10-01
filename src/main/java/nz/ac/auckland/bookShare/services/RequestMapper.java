package nz.ac.auckland.bookShare.services;

import java.util.HashSet;
import java.util.Set;

import nz.ac.auckland.bookShare.domain.Request;
import nz.ac.auckland.bookShare.domain.User;

public class RequestMapper {
	static Request toDomainModel(nz.ac.auckland.bookShare.dto.Request dtoRequest) {
		Set<User> requestors = new HashSet<User>();
		for (nz.ac.auckland.bookShare.dto.User requestor : dtoRequest.getRequestors()) {
			requestors.add(UserMapper.toDomainModel(requestor));
		}
		Request fullRequest = new Request(dtoRequest.getId(), requestors, UserMapper.toDomainModel(dtoRequest.getOwner()),
				BookMapper.toDomainModel(dtoRequest.getBook()));
		return fullRequest;
	}

	static nz.ac.auckland.bookShare.dto.Request toDto(Request request) {
		Set<nz.ac.auckland.bookShare.dto.User> requestors = new HashSet<nz.ac.auckland.bookShare.dto.User>();
		for (User requestor : request.getRequestors()) {
			requestors.add(UserMapper.toDto(requestor));
		}
		nz.ac.auckland.bookShare.dto.Request dtoRequest = new nz.ac.auckland.bookShare.dto.Request(request.getId(),
				requestors, UserMapper.toDto(request.getOwner()), BookMapper.toDto(request.getBook()));
		return dtoRequest;
	}
}
