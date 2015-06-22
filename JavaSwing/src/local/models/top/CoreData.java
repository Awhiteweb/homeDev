package local.models.top;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Class to store the database connection values and location for the xml file
 * 
 * @author Alex.White
 *
 */
public final class CoreData extends DefaultHandler {
	
	/*
	 *  XML file location
	 */
	public static String XMLFILE;
	
	/*
	 * database connection details
	 */
	public static String USERNAME,
						 PASSWORD,
						 CONN_STRING;
	
	private StringBuilder currentText;
	
	
	public void loadUser()
	{
		try
		{
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			parser.parse( "files/UserData.xml", this );
		}
		catch ( SAXException | IOException | ParserConfigurationException e )
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void startDocument() throws SAXException
	{
	}
	
	@Override
	public void endDocument() throws SAXException
	{
	}
	
	@Override
	public void startElement( String uri, String localName, String qName,
			Attributes attributes ) throws SAXException
	{
		switch ( qName )
		{
			case "file":
				currentText = new StringBuilder();
				break;
				
			case "username":
				currentText = new StringBuilder();
				break;

			case "password":
				currentText = new StringBuilder();
				break;
				
			case "database":
				currentText = new StringBuilder();
				break;
			
			default:
				break;	
		}

	}
	
	@Override
	public void endElement( String uri, String localName, String qName )
			throws SAXException
	{
		
		switch ( qName )
		{
			case "file":
				XMLFILE = currentText.toString();
				break;
				
			case "username":
				USERNAME = currentText.toString();
				break;

			case "password":
				PASSWORD = currentText.toString();
				break;
				
			case "database":
				CONN_STRING = currentText.toString();
				break;
			
			default:
				break;	
		}
		
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
