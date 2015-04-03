<%@ page import="java.sql.*" %><%@ page import="java.io.*" %><%@ page import="com.mysql.*" %><?xml version="1.0" encoding="UTF-8"?>
<movies>
<%
	Connection connection = null;
	Statement statement = null;
	ResultSet results = null;
	
	try 
	{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/movie_db", "root", "");
		out.println("connect to database");
		
	} 
	catch (SQLException e)
	{
		out.println(e.getMessage());
	}


%>
</movies>