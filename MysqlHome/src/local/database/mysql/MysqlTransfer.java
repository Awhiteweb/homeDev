package local.database.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import local.dto.Video;

//import com.mysql.jdbc.Connection;
//import com.mysql.jdbc.Statement;

public class MysqlTransfer {

	private static Connection connect;
	private static Statement statement = null;
	private static PreparedStatement preparedStatement = null;
	private static ResultSet resultSet = null;
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
				NAME = "Name",
			   GENRE = "Genre",
			   GROUP = "Series",
			LOCATION = "Location",
				KIND = "Kind",
			  NUMBER = "Episode Order",
			  SEASON = "Season";
	
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
			connect = MysqlConnect.connect();

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
			connect = MysqlConnect.connect();

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
		connect = MysqlConnect.connect();
		
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
			connect = MysqlConnect.connect();

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
			connect = MysqlConnect.connect();

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
			connect = MysqlConnect.connect();

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
			connect = MysqlConnect.connect();

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
	public static List<Video> returnAll() throws Exception
	{
		List<Video> result;
		
		String stmt = "SELECT `m`.`id` AS `ID`, "
				+ "`m`.`title` AS `Title`, "
				+ "`m`.`path` AS `Path`, "
				+ "`ge`.`genre` AS `Genre`, "
				+ "`gr`.`group` AS `Group`, "
				+ "`m`.`series_num` AS `Series Number`, "
				+ "`t`.`genre` AS `Type` "
				+ "FROM `main` AS `m` "
				+ "LEFT JOIN `genres` AS `ge` "
				+ "ON `m`.`genre` = `ge`.`id` "
				+ "LEFT JOIN `groups` AS `gr` "
				+ "ON `m`.`group` = `gr`.`id` "
				+ "LEFT JOIN `type` AS `t` "
				+ "ON `m`.`type` = `t`.`id` "
				+ "WHERE `m`.`type` = 1;";
		
		try {
			connect = MysqlConnect.connect();
			
			statement = connect.createStatement();
			
			preparedStatement = connect
					.prepareStatement( stmt );
			resultSet = preparedStatement.executeQuery();
			
			result = readResults( resultSet );

		}
		catch (SQLException e)
		{
			result = null;
			throw e;
		}
		finally
		{
			close();
		}
		
		return result;
	}
	
	private static List<Video> readResults( ResultSet resultSet ) throws SQLException
	{
		List<Video> result = new ArrayList<Video>();
		

		while ( resultSet.next() )
		{
//			switch ( resultSet.getString( "type" ) )
//			{
//			case Movie:
				Video movResults = new Video();
				movResults.setID( Integer.parseInt( resultSet.getString( "ID" ) ) );
				movResults.setTitle( resultSet.getString( "Title" ) );
				movResults.setLocation( resultSet.getString( "Path" ) );
				movResults.setGenre( resultSet.getString( "Genre" ) );
				movResults.setGroup( resultSet.getString( "Group" ) );
				movResults.setSeriesN( Integer.parseInt( resultSet.getString( "Series Number" ) ) );
				result.add( movResults );
//				break;
				
//			case TV:
//				TV tvResults = new TV();
//				tvResults.setID( Integer.parseInt( resultSet.getString( "id" ) ) );
//				tvResults.setTitle( resultSet.getString( "title" ) );
//				tvResults.setLocation( resultSet.getString( "path" ) );
//				tvResults.setGenre( resultSet.getString( "genre" ) );
//				tvResults.setGroup( resultSet.getString( "group" ) );
//				tvResults.setSeriesN( Integer.parseInt( resultSet.getString( "series_num" ) ) );
//				tvResults.setSeasonN( Integer.parseInt( resultSet.getString( "tv_season" ) ) );
//				break;
//
//			default:
//				break;
//			}
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


	
	private static void close() {
		try {
			
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

		} catch (Exception e) {

		}
	}


}
