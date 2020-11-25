package com.revature.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseManager {

	final String baseUrl = "jdbc:postgresql://localhost/";
	//default implementation

	/**
	 * This connects to a default database called testDb
	 * The method will fail if you don't have testDb setup
	 * @return
	 */
	public Connection getConnection() {
		Connection c = null;
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost/testDb",
					System.getenv("DB_Username"), System.getenv("DB_Password"));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Could not create object.");
		}
		return c;
	}
	/**
	 * Gets the local database connection
	 * @param dbName
	 * @param dbUser
	 * @param dbPassword
	 * @return null or Connection
	 */
	public Connection getLocalConnection(String dbName, String dbUser, String dbPassword) {
		Connection c = null;
		String realUrl = baseUrl +dbName;
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection(realUrl,
					dbUser, dbPassword);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Could not create object.");
		}
		return c;
	}
	/**
	 * Gets a connection via the cloud
	 * Need to set up a cloud account with AWS or something
	 * @param cDbName
	 * @param dbUser
	 * @param dbPassword
	 * @return null or Connection
	 */
	public Connection getCloudConnection(String cDbName, String dbUser, String dbPassword) {
		Connection c = null;
		String realUrl = cDbName;
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection(realUrl,
					dbUser, dbPassword);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Could not create object.");
		}
		return c;
	}
}
