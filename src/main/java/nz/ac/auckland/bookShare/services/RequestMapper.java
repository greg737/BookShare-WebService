package nz.ac.auckland.bookShare.services;

import nz.ac.auckland.bookShare.domain.Request;

public class RequestMapper {
	static Request toDomainModel(nz.ac.auckland.bookShare.dto.Request dtoRequest) {
		Request fullRequest = new Request(dtoRequest.getId(),UserMapper.toDomainModel(dtoRequest.getRequestor()),
				BookMapper.toDomainModel(dtoRequest.getBook()));
		return fullRequest;
	}
	
	static nz.ac.auckland.bookShare.dto.Request toDto(Request request) {
		nz.ac.auckland.bookShare.dto.Request dtoRequest = new nz.ac.auckland.bookShare.dto.Request(
				request.getId(), UserMapper.toDto(request.getRequestor()), BookMapper.toDto(request.getBook()));
		return dtoRequest;	
	}
}
