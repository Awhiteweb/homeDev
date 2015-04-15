package local.database.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class MysqlConnect
{
	private static final String USERNAME = "root",
								PASSWORD = "",
							 CONN_STRING = "jdbc:mysql://localhost/movie_db";
	
	private static Connection conn;
	
	private static MysqlConnect __me = null;
	
	private MysqlConnect()
	{
		try
		{
			conn = DriverManager.getConnection( CONN_STRING, USERNAME, PASSWORD );

			System.out.println( "connected" );	
		}
		catch (SQLException e)
		{
			System.err.println( "could not connect : " + e.getSQLState() + ", " + e.getErrorCode() + ", " + e.getMessage() );
		}

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
