package database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

//import com.mysql.jdbc.Connection;
//import com.mysql.jdbc.Statement;

public class DataTransfer {

	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	public void main(String[] args) {

		File path = new File("/Volumes/Public/Shared Videos/Movie Archive/");
		
		File[] files = path.listFiles();
		
		for( File file : files ) {
			System.out.println( file.getName() );
		}
		
	}

	public void writeMovie() throws Exception {
		
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			connect = DriverManager
					.getConnection("jdbc:mysql://localhost/movie_db?"
							+ "user=root&password=");

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();

			// PreparedStatements can use variables and are more efficient
			preparedStatement = connect
					.prepareStatement("INSERT INTO movie_db.main VALUES (default, ?, ?, ?, ?, ?)");
			// "myuser, webpage, datum, summary, COMMENTS from feedback.comments");
			// Parameters start with 1
			preparedStatement.setString(1, "title");
			preparedStatement.setString(2, "path");
			preparedStatement.setString(3, "genre");
			preparedStatement.setString(4, "group");
			preparedStatement.setString(5, "series number");
			preparedStatement.executeUpdate();

		} catch (Exception e) {
			throw e;
		}

	}

	public void writeGenre( String genre ) throws Exception {
		
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			connect = DriverManager
					.getConnection("jdbc:mysql://localhost/movie_db?"
							+ "user=root&password=");

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();

			// PreparedStatements can use variables and are more efficient
			preparedStatement = connect
					.prepareStatement("INSERT INTO movie_db.genre VALUES (default, ?)");
			// Parameters start with 1
			preparedStatement.setString( 1, genre );
			preparedStatement.executeUpdate();

		} catch (Exception e) {
			throw e;
		}

	}

}
