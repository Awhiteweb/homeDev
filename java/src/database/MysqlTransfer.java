package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

//import com.mysql.jdbc.Connection;
//import com.mysql.jdbc.Statement;

public class MysqlTransfer {

	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	private String location;
	private String group;
	private String genre;
	private String name;
	private int number;
	private int genreInt;
	private int groupInt;
	private int season;
	private int type;
	
	public static final String
			NAME="Name",
			GENRE="Genre",
			GROUP="Series",
			LOCATION="Location",
			KIND="Kind",
			NUMBER="Episode Order",
			SEASON="Season";
	
	public MysqlTransfer()
	{
	}

	/**
	 * send new movies and tv shows to database
	 */
	public void writeVideo() {
		
		try {
			
			if ( location != null )
			{
			
				if ( location.contains( "iTunes/Movies" ) ) 
				{
					writeMovie();
				}
				
				if ( location.contains( "iTunes/TV" ) )
				{
					writeTV();
				}
			} 
			
		} catch (Exception e) {
			System.err.println( e );
		}
		
	}

	private void writeMovie() throws Exception
	{
		if ( genre != null )
		{
			writeGenre(genre);
			genreInt = searchGenre(genre);
		}
		
		if ( group != null )
		{
			writeGroup(group);
			groupInt = searchGroup(group);
		}
		
		type = 1;
		
		try {
			connectMysql();

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();

			// PreparedStatements can use variables and are more efficient
			preparedStatement = connect
					.prepareStatement(
						"INSERT IGNORE INTO `movie_db`.`main` "
						+ "( `title`, `path`, `genre`, `group`, `type`, `series_num` ) "
						+ "VALUES ( ?, ?, ?, ?, ?, ? ) ");
			// Parameters start with 1
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, location);
			preparedStatement.setInt(3, genreInt);
			preparedStatement.setInt(4, groupInt);
			preparedStatement.setInt(5, type);
			preparedStatement.setInt(6, number);
			preparedStatement.executeUpdate();

		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}
	}
	
	private void writeTV() throws Exception
	{
		
		if ( genre != null )
		{
			writeGenre(genre);
			genreInt = searchGenre(genre);
		}
		
		if ( group != null )
		{
			writeGroup(group);
			groupInt = searchGroup(group);
		}
		
		try {
			connectMysql();

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();

			// PreparedStatements can use variables and are more efficient
			preparedStatement = connect
					.prepareStatement(
						"INSERT IGNORE INTO `movie_db`.`main` "
						+ "( `title`, `path`, `genre`, `group`, `type`, `series_num`, `tv_season` ) "
						+ "VALUES ( ?, ?, ?, ?, ?, ?, ? ) ");
			// Parameters start with 1
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, location);
			preparedStatement.setInt(3, genreInt);
			preparedStatement.setInt(4, groupInt);
			preparedStatement.setInt(5, 2);
			preparedStatement.setInt(6, number);
			preparedStatement.setInt(7, season);
			preparedStatement.executeUpdate();

		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}

	}

	/**
	 * updates movies in the main table 1 column at a time
	 * @param column of type String, the column you want to update
	 * @param update of type String, the update value
	 * @param id of type int, the movie id you want to update
	 * @throws Exception
	 */
	public void updateMovie( String column, String update, int id ) throws Exception
	{
		connectMysql();
		
		statement = connect.createStatement();
		
		preparedStatement = connect.prepareStatement
				(
						"UPDATE `movie_db`.`main` "
								+ "SET ? = ? "
								+ "WHERE `id` LIKE ?"
				);
		preparedStatement.setString(1, column);
		preparedStatement.setString(2, update);
		preparedStatement.setInt(3, id);
		preparedStatement.executeUpdate();

				
	}

	/**
	 * inserts new genres into the genre table
	 * @param genre of type String
	 * @throws Exception
	 */
	public void writeGenre( String genre ) throws Exception {
		
		genre = genre.toLowerCase();
		
		try {
			connectMysql();

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();

			// PreparedStatements can use variables and are more efficient
			preparedStatement = connect
					.prepareStatement("INSERT IGNORE INTO `movie_db`.`genres` VALUES (NULL, ?)");
			// Parameters start with 1
			preparedStatement.setString( 1, genre );
			preparedStatement.executeUpdate();

		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}

	}

	private int searchGenre( String genre ) throws Exception {
		
		genre = genre.toLowerCase();
		
		try {
			connectMysql();

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();

			// PreparedStatements can use variables and are more efficient
			preparedStatement = connect
					.prepareStatement("SELECT * FROM `movie_db`.`genres` WHERE `genre` LIKE ?");
			// Parameters start with 1
			preparedStatement.setString( 1, genre );
			resultSet = preparedStatement.executeQuery();
			
			genreInt = readAResult( resultSet );
			
			return genreInt;

		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}

	}

	/**
	 * inserts new groups into the groups table
	 * @param group of type String
	 * @throws Exception
	 */
	public void writeGroup( String group ) throws Exception {
		
		group = group.toLowerCase();
		
		try {
			connectMysql();

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();

			// PreparedStatements can use variables and are more efficient
			preparedStatement = connect
					.prepareStatement("INSERT IGNORE INTO `movie_db`.`groups` VALUES (NULL, ?)");
			// Parameters start with 1
			preparedStatement.setString( 1, group );
			preparedStatement.executeUpdate();

		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}

	}

	private int searchGroup( String group ) throws Exception {
		
		group = group.toLowerCase();
		
		try {
			connectMysql();

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();

			// PreparedStatements can use variables and are more efficient
			preparedStatement = connect
					.prepareStatement("SELECT * FROM `movie_db`.`groups` WHERE `group` LIKE ?");
			// Parameters start with 1
			preparedStatement.setString( 1, group );
			resultSet = preparedStatement.executeQuery();
			
			groupInt = readAResult( resultSet );
			
			return groupInt;

		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}


	}

	/**
	 * queries database and returns all database entries
	 * @return a List<String[]>
	 * @throws Exception
	 */
	private List<String[]> returnAll() throws Exception
	{
		
		
		connectMysql();
		
		statement = connect.createStatement();
		
		preparedStatement = connect
				.prepareStatement("SELECT * FROM `movie_db`.`main`");
		resultSet = preparedStatement.executeQuery();
		
		List<String[]> result = readResults( resultSet );

		return result;

	}
	
	private List<String[]> readResults( ResultSet resultSet2 ) throws SQLException
	{
		List<String[]> result = new ArrayList<String[]>();
		

		while ( resultSet.next() )
		{
			String[] results = new String [8];
			results[0] = resultSet.getString( "id" );
			results[1] = resultSet.getString( "title" );
			results[2] = resultSet.getString( "path" );
			results[3] = resultSet.getString( "genre" );
			results[4] = resultSet.getString( "group" );
			results[5] = resultSet.getString( "type" );
			results[6] = resultSet.getString( "series_num" );
			results[7] = resultSet.getString( "tv_season" );
			result.add( results );
		}
		
		return result;
	}

	private int readAResult(ResultSet resultSet) throws SQLException 
	{
		int result = 0;
		// ResultSet is initially before the first data set
		while (resultSet.next()) {
			// It is possible to get the columns via name
			// also possible to get the columns via the column number
			// which starts at 1
			// e.g. resultSet.getSTring(2);
			result = Integer.parseInt( resultSet.getString("id") );
		}
		return result;
	}

	private void connectMysql() throws ClassNotFoundException, SQLException
	{
		// This will load the MySQL driver, each DB has its own driver
		Class.forName("com.mysql.jdbc.Driver");
		// Setup the connection with the DB
		connect = DriverManager
				.getConnection("jdbc:mysql://localhost/movie_db?"
						+ "user=root&password=");
	}
	
	private void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {

		}
	}


}
