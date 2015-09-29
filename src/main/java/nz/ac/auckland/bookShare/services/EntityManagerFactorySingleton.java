package nz.ac.auckland.bookShare.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityManagerFactorySingleton {
	private static Logger _logger = LoggerFactory.getLogger(EntityManagerFactorySingleton.class);

	// JDBC connection to the database.
	private static Connection _jdbcConnection = null;

	// JPA EntityManagerFactory, used to create an EntityManager.
	private static EntityManagerFactory _factory = null;

	public EntityManagerFactorySingleton(){
//		try {
//			Class.forName("org.h2.Driver");
//			_jdbcConnection = DriverManager.getConnection(
//					"jdbc:h2:~/test;mv_store=false", "sa", "sa");
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		_factory = Persistence.createEntityManagerFactory("bookSharingPU");
	}


	public static void releaseEntityManager() throws SQLException {
		_jdbcConnection.close();
	}
	
	public static EntityManager generateEntityManager(){
		return _factory.createEntityManager();
	}

	protected static void clearDatabase(boolean dropTables) throws SQLException {
		Statement s = _jdbcConnection.createStatement();
		s.execute("SET REFERENTIAL_INTEGRITY FALSE");

		Set<String> tables = new HashSet<String>();
		ResultSet rs = s.executeQuery("select table_name "
				+ "from INFORMATION_SCHEMA.tables "
				+ "where table_type='TABLE' and table_schema='PUBLIC'");

		while (rs.next()) {
			tables.add(rs.getString(1));
		}
		rs.close();
		for (String table : tables) {
			_logger.debug("Deleting content from " + table);
			s.executeUpdate("DELETE FROM " + table);
			if (dropTables) {
				s.executeUpdate("DROP TABLE " + table);
			}
		}

		s.execute("SET REFERENTIAL_INTEGRITY TRUE");
		s.close();
	}

}
