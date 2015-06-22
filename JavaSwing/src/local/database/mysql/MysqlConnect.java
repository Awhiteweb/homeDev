package local.database.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import local.models.top.CoreData;

/*
 * creates a single connection to a mysql database
 */
public class MysqlConnect
{
	private static Connection conn;
	private static PreparedStatement preparedStatement = null;
	private static ResultSet resultSet = null;
	private static MysqlConnect __me = null;
	
	public MysqlConnect()
	{
		try
		{
			conn = DriverManager.getConnection( CoreData.CONN_STRING, CoreData.USERNAME, CoreData.PASSWORD );
//			System.out.println( "connected" );	
		}
		catch (SQLException e)
		{
			System.err.println( "could not connect : " + e.getSQLState() + ", " + e.getErrorCode() + ", " + e.getMessage() );
		}
	}

	/**
	 * NOT for XML
	 * 
	 * sends a mysql database query and gets a ResultSet back
	 * 
	 * @param query
	 * @return ResultSet
	 * @throws SQLException
	 */
	public ResultSet query( String query ) throws SQLException
	{
		preparedStatement = conn.prepareStatement( query );
		resultSet = preparedStatement.executeQuery();
		return resultSet;
	}
	
	/**
	 * NOT for XML
	 * 
	 * sends string statement to mysql database to update.
	 * 
	 * @param statement
	 * @throws SQLException
	 */
	public void update( String statement ) throws SQLException
	{
		preparedStatement = conn.prepareStatement( statement );
		if ( preparedStatement.executeUpdate() < 1 )
		{
			System.err.println( "nothing updated from statement:\n\t" + statement );
		}
		
	}
	
	/**
	 * creates a single connection to mysql database
	 * if a connection already exists that connection is returned
	 * @return Connection
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
