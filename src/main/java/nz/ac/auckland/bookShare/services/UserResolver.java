package nz.ac.auckland.bookShare.services;

import javax.ws.rs.ext.ContextResolver;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import nz.ac.auckland.bookShare.dto.User;

/**
 * ContextResolver implementation to return a customised JAXBContext
 */
public class UserResolver implements ContextResolver<JAXBContext> {
	private JAXBContext _context;

	public UserResolver() {
		try {
			// The JAXB Context should be able to marshal and unmarshal the
			// specified classes.
			_context = JAXBContext.newInstance(User.class);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	@Override
	public JAXBContext getContext(Class<?> type) {
		if (type.equals(User.class)) {
			return _context;
		} else {
			return null;
		}
	}
}