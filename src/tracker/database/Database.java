package tracker.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import tracker.database.Database;

public class Database {
	private static Connection conn;
	private static Database instance;

	public Database getInstance() {
		if (instance == null) {
			instance = new Database();
		}

		return instance;
	}

	public static Connection getConnection() {
		return conn;
	}

	public static void init() throws ClassNotFoundException, SQLException {
		Class.forName("org.postgresql.Driver");
		conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/issuetracker", "binay", "binay");
	}
}
