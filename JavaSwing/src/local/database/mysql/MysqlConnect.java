package local.database.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MysqlConnect
{
	private static final String USERNAME = "root",
								PASSWORD = "",
							 CONN_STRING = "jdbc:mysql://localhost/movie_db";
	
	private static Connection conn;
	private static PreparedStatement preparedStatement = null;
	private static ResultSet resultSet = null;
	
	private static MysqlConnect __me = null;
	
	public MysqlConnect()
	{
		try
		{
			conn = DriverManager.getConnection( CONN_STRING, USERNAME, PASSWORD );

//			System.out.println( "connected" );	
		}
		catch (SQLException e)
		{
			System.err.println( "could not connect : " + e.getSQLState() + ", " + e.getErrorCode() + ", " + e.getMessage() );
		}
	}

	public ResultSet query( String query ) throws SQLException
	{
		
//		String stmt = "SELECT `m`.`id` AS `ID`, "
//				+ "`m`.`title` AS `Title`, "
//				+ "`m`.`path` AS `Path`, "
//				+ "`ge`.`genre` AS `Genre`, "
//				+ "`gr`.`group` AS `Group`, "
//				+ "`m`.`series_num` AS `Series Number`, "
//				+ "`t`.`genre` AS `Type` "
//				+ "FROM `main` AS `m` "
//				+ "LEFT JOIN `genres` AS `ge` "
//				+ "ON `m`.`genre` = `ge`.`id` "
//				+ "LEFT JOIN `groups` AS `gr` "
//				+ "ON `m`.`group` = `gr`.`id` "
//				+ "LEFT JOIN `type` AS `t` "
//				+ "ON `m`.`type` = `t`.`id` "
//				+ "WHERE `m`.`type` = 1"
//				+ "LIMIT 20;";
		
		preparedStatement = conn.prepareStatement( query );
		resultSet = preparedStatement.executeQuery();
		
		
		return resultSet;
	}
	
	public int update( String statement ) throws SQLException
	{
		preparedStatement = conn.prepareStatement( statement );
		return preparedStatement.executeUpdate();
	}
	
	/**
	 * creates a single connection to mysql database
	 */
	public static Connection connect()
	{
		
		if ( __me == null )
		{
			__me = new MysqlConnect();
		}
		
		return conn;
	}
	
	/**
	 * closes the mysql connection
	 * @throws SQLException
	 */
	public static void close() throws SQLException
	{

		if( conn != null )
		{
			conn.close();
		}
		
	}

}
