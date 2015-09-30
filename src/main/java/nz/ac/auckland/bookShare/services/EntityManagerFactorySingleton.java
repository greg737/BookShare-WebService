package nz.ac.auckland.bookShare.services;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactorySingleton {
	// JPA EntityManagerFactory, used to create an EntityManager.
	private static EntityManagerFactory _factory = null;

	public EntityManagerFactorySingleton(){
		_factory = Persistence.createEntityManagerFactory("bookSharingPU");
	}

	public static EntityManager generateEntityManager() {
		return _factory.createEntityManager();
	}
}
