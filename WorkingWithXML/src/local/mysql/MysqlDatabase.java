package local.mysql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//import com.mysql.jdbc.Connection;
//import com.mysql.jdbc.Statement;

public class MysqlDatabase {

	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	private String kind;
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


	public MysqlDatabase()
	{
	}
	
	
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
			
			genreInt = writeResultSet( resultSet );
			
			return genreInt;

		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}

	}

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
			
			groupInt = writeResultSet( resultSet );
			
			return groupInt;

		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}


	}

	private int writeResultSet(ResultSet resultSet) throws SQLException {
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

	
	
	public String getName()
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public String getGenre()
	{
		return genre;
	}

	public void setGenre(String genre)
	{
		this.genre = genre;
	}
	
	public String getGroup()
	{
		return group;
	}

	public void setGroup(String group)
	{
		this.group = group;
	}
	
	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}
	
	public String getKind()
	{
		return kind;
	}

	public void setKind(String kind)
	{
		this.kind = kind;
	}
	
	public int getNumber()
	{
		return number;
	}
	
	public void setNumber(String number)
	{
		this.number = Integer.parseInt( number );
	}

	public int getSeason()
	{
		return season;
	}
	
	public void setSeason(String season)
	{
		this.season = Integer.parseInt( season );
	}

}
