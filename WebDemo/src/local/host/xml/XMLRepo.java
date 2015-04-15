package local.host.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javanet.staxutils.*;





public class XMLRepo extends DefaultHandler
{

	private List<String> data;
	private String currentElement = "";
	private String previousElement = "";
	private StringBuilder currentText;
	
	public void createDocument (List<String> data, String filename) throws XMLStreamException, IOException
	{
		
		XMLOutputFactory factory = XMLOutputFactory.newInstance();
		FileWriter fw = new FileWriter( new File(filename) );
		XMLStreamWriter w = factory.createXMLStreamWriter( fw );
		IndentingXMLStreamWriter writer = new IndentingXMLStreamWriter( w );
		
		
		writer.writeStartDocument();
		
		createElement(writer, data);
		
		writer.writeEndDocument();
		
		
		writer.flush();
		writer.close();
		
		System.out.println( "All Done!" );

	}

	private void createElement(XMLStreamWriter writer, List<String> data) throws XMLStreamException 
	{
		for (String d : data) 
		{
			writer.writeStartElement( "item" );
			writer.writeAttribute( "id" , "0" );
			
				writer.writeStartElement( "element_name" );
				writer.writeCharacters( "fill data" );
				writer.writeEndElement();
				
				writer.writeStartElement( "" );
				writer.writeStartElement( "element_name" );
				writer.writeCharacters( "fill data" );
				writer.writeEndElement();

			
			writer.writeEndElement();
	
		}
	}
	
	public void readDocument( String filename ) throws SAXException, IOException, ParserConfigurationException
	{
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		
		parser.parse( new File( filename ), this );
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
		case "title":
			break;
			
		case "date":
			break;
		}
	}
	
	@Override
	public void endElement( String uri, String localName, String qName )
			throws SAXException
	{
		
	}
	
	@Override
	public void characters( char[] ch, int start, int length )
			throws SAXException
	{
		
	}
}
