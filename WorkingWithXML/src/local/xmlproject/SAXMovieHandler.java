package local.xmlproject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import local.mysql.MysqlDatabase;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXMovieHandler extends DefaultHandler
{

	private List<Movie> data;
	private Movie movie;
	private MysqlDatabase mysql;
	private String currentElement = "";
	private String previousElement = "";
	private StringBuilder currentText;
	
	public /*List<Movie>*/ void readDataFromXML(String filename) throws Exception
	{
		
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		
		parser.parse( new File( filename ), this );
		
//		return data;	
	}

	@Override
	public void startDocument() throws SAXException
	{
		System.out.println("Start Document");
		data = new ArrayList<>();
		
	}
	
	@Override
	public void endDocument() throws SAXException
	{
		previousElement = "";
		System.out.println("End Document");
	}

	@Override
	public void startElement( String uri, String localName, String qName,
			Attributes attributes ) throws SAXException
	{
		currentElement = qName;
		
		switch ( currentElement )
		{
		case "plist":
//			System.out.println("Start Element " + qName);
			break;
		
		case "dict":
//			System.out.println("Start Element " + qName);
			movie = new Movie();
			mysql = new MysqlDatabase();
//			data.add( movie );
			break;
		
		default:
//			System.out.println("\t Start Element " + qName);
			currentText = new StringBuilder();
			break;
		}
		
	}
	
	@Override
	public void endElement( String uri, String localName, String qName )
			throws SAXException
	{
//		System.out.println("End Element " + qName);
//		if ( currentElement.equals( "plist" ) || currentElement.equals( "dict" ) )
//		{
//			System.out.println("End Element " + qName);
//			return;
//		} else {
//			System.out.println("\t End Element " + qName);
//		}

		switch ( qName )
		{
		case "plist":
//			System.out.println("End Element " + qName);
			return;

		case "dict":
//			System.out.println("End Element " + qName);
//			data.add( movie );
			mysql.writeVideo();
			return;
			
		default:
//			System.out.println("\t End Element " + qName);
			break;
		}
		
		
		String content = currentText.toString();
		
		switch ( previousElement )
		{
		case MysqlDatabase.NAME:
			mysql.setName( content );
			break;
			
		case MysqlDatabase.GENRE:
			mysql.setGenre( content );
			break;

		case MysqlDatabase.GROUP:
			mysql.setGroup( content );
			break;

		case MysqlDatabase.LOCATION:
			mysql.setLocation( content );
			break;
			
		case MysqlDatabase.KIND:
			mysql.setKind( content );
			break;
			
		case MysqlDatabase.NUMBER:
			mysql.setNumber( content );
			break;

		default:
			break;
		}
		
		previousElement = content;
		currentElement = "";
	}
	
	@Override
	public void characters( char[] ch, int start, int length )
			throws SAXException
	{
		if( currentText != null )
		{
			currentText.append( ch, start, length );
		}
	}
	
	
	
	
}
